package com.jieli.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jie on 29/03/17.
 */

public class TVFragment extends Fragment {
    Context parent;
    LivingRoomActivity living = null;

    String tVChannelID;
    SharedPreferences prefs  ;
    SharedPreferences.Editor myEditor ;
    TextView textTVChnnel;
    Switch tvSwitch;


    public TVFragment(){super();}

    public TVFragment(LivingRoomActivity c){living = c;}

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Bundle bundle = getArguments();
        tVChannelID = bundle.getString("TVChannelID");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.tv_frag_layout, null);
        textTVChnnel = (TextView) gui.findViewById(R.id.tv_editText);
        tvSwitch = (Switch) gui.findViewById(R.id.tv_sw);


        prefs = this.getActivity().getSharedPreferences("livingRoomItems", Context.MODE_PRIVATE);
        myEditor = prefs.edit();
        textTVChnnel.setText(prefs.getString("DefaultTVChannel", "CBC"));
        tvSwitch.setChecked(prefs.getBoolean("DefaultTVStatus", false));

        Button btnEnter = (Button)gui.findViewById(R.id.tv_Enter);
        btnEnter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TVFragment", "clicked btnEnter");
                tVChannelID = textTVChnnel.getText().toString();

                myEditor.putString("DefaultTVChannel",textTVChnnel.getText().toString());
                myEditor.putBoolean("DefaultTVStatus",tvSwitch.isChecked());

                myEditor.commit();

                if (living == null){

                    Intent data = new Intent();
                    data.putExtra("TVChannelID", tVChannelID);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();

                }else{
                        living.setTVChannel(tVChannelID);
                        living.removeFragment();
                }
            }
        });

        return gui;
    }
}
