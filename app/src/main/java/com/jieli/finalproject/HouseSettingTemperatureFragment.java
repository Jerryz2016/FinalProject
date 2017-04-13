package com.jieli.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;
import static android.app.Activity.RESULT_OK;

/**
 * Author Haixia Feng
 * Date   March 30, 2017
 */

public class HouseSettingTemperatureFragment extends Fragment {
    Context parent;
    String houseTimeSchedule;
    String houseTemp;
    Date date;
    boolean isTablet;
    TextView textTime;
    TextView textTemperature;


    //no matter how you got here, the data is in the getArguments
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Bundle bundle = getArguments();
        houseTemp = bundle.getString("Temperature");
        isTablet = bundle.getBoolean("isTablet");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.house_setting_temp_frag, null);


        textTime = (TextView) gui.findViewById(R.id.time_set);
        textTime.setText(houseTimeSchedule);

        textTemperature = (TextView) gui.findViewById(R.id.housetemp_set);

        textTemperature.setText(houseTemp);


        Button okButton = (Button) gui.findViewById(R.id.housetemp_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houseTemp = textTemperature.getText().toString();
                houseTimeSchedule = textTime.getText().toString();
                if (isTablet) {
                    //todo call store method in HouseSettings class to save temp in db
                    HouseSettingTemperatureFragment mf = (HouseSettingTemperatureFragment) getFragmentManager().findFragmentById(R.id.housetemperature_framelayout); //remove the sw600dp frage
                    getFragmentManager().beginTransaction().remove(mf).commit();

                } else {
                    Intent data = new Intent();
                    data.putExtra("Time", houseTimeSchedule);
                    data.putExtra("Temperature", houseTemp);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();
                }
            }
        });


        return gui;
    }
   

    public HouseSettingTemperatureFragment() {
        super();
    }
}
