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
 * The Class TVFragment extends Fragment. It is used to
 * set up TV fragment for tablet
 * @author   Jie Wang
 * @version v1.3.  Date: Apr 12, 2017
 */
public class TVFragment extends Fragment {
    Context parent;
    LivingRoomActivity living = null;

    String tVChannelID;
    SharedPreferences prefs  ;
    SharedPreferences.Editor myEditor ;
    TextView textTVChnnel;
    Switch tvSwitch;
    Button delete;
    int clickedID;
    private Bundle clickedItem;

    private String tvOnOff;

    // default constructor
    public TVFragment(){super();}

    // constructor with argument of c: LivingRoomActivity
    public TVFragment(LivingRoomActivity c){living = c;}
    /**
     * Fragment callback function by Android.
     * *@param b: Bundl
     */
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        Bundle bundle = getArguments();

        tVChannelID = bundle.getString("TVChannelID");
        clickedID = bundle.getInt("id");
       // clickedItem = getActivity().getIntent().getExtras();
       // clickedID = clickedItem.getInt("id");
    }
    /**
     * Fragment callback function by Android.
     * *@param inflater: LayoutInflater
     * *@param container: ViewGroup
     * *@param savedInstanceState Bundle
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.tv_frag_layout, null);
        textTVChnnel = (TextView) gui.findViewById(R.id.tv_editText);
        tvSwitch = (Switch) gui.findViewById(R.id.tv_sw);
        delete = (Button) gui.findViewById(R.id.tv_del);

        prefs = this.getActivity().getSharedPreferences("livingRoomItems", Context.MODE_PRIVATE);
        myEditor = prefs.edit();
        textTVChnnel.setText(prefs.getString("DefaultTVChannel"+Integer.toString(clickedID), "CBC"));
        tvSwitch.setChecked(prefs.getBoolean("DefaultTVStatus"+Integer.toString(clickedID), false));

        Button btnEnter = (Button)gui.findViewById(R.id.tv_Enter);
        btnEnter.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TVFragment", "clicked btnEnter");
                tVChannelID = textTVChnnel.getText().toString();

                myEditor.putString("DefaultTVChannel"+Integer.toString(clickedID),textTVChnnel.getText().toString());
                myEditor.putBoolean("DefaultTVStatus"+Integer.toString(clickedID),tvSwitch.isChecked());

                myEditor.commit();

                if (living == null){

                    Intent data = new Intent();
                    data.putExtra("TVChannelID", tVChannelID);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();

                }else{
                        if (tvSwitch.isChecked()==true)
                            tvOnOff = "  On";
                        else
                            tvOnOff ="  Off";
                        living.setTVChannel(tVChannelID +tvOnOff );
                        living.removeFragment();
                }
            }
        });
           //delete button to delete the item of a tv
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (living == null) {
                    Intent intent = new Intent();
                    intent.putExtra("delete", true);
                    intent.putExtra("id", clickedID);
                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                }else{
                    living.delTV(clickedID);
                    living.removeFragment();
                }
            }
        });
        return gui;
    }

}
