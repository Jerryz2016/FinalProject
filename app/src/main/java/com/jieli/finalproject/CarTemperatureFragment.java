package com.jieli.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Jerry on 27-Mar-17.
 */

public class CarTemperatureFragment extends Fragment {
    Context parent;
    int temp;
    String message;
    boolean isTablet;


    //no matter how you got here, the data is in the getArguments
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Bundle bundle = getArguments();
        temp = bundle.getInt("Temperature");
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
        View gui = inflater.inflate(R.layout.temp_frag_layout, null);

        TextView textViewTemperature = (TextView) gui.findViewById(R.id.temp_dis_textview);
        textViewTemperature.setText("Temperature: " + temp);


        Button okButton = (Button) gui.findViewById(R.id.temp_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTablet) {
                    CarTemperatureFragment mf = (CarTemperatureFragment) getFragmentManager().findFragmentById(R.id.car_framelayout); //remove the sw600dp frage
                    getFragmentManager().beginTransaction().remove(mf).commit();

                } else {
                    Intent data = new Intent();
                    data.putExtra("Temperature", 24);
                    getActivity().setResult(RESULT_OK, data);
                    getActivity().finish();
                }
            }
        });

        return gui;
    }

    public CarTemperatureFragment() {
        super();
    }
}
