package com.jieli.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

//  create empty temperature fragment
public class CarTemperature extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_temperature);   //set empty temperature frame layout with id temp_frame_layout

        Toast toast0 = Toast.makeText(this, "Setting CarTemperature fragement", Toast.LENGTH_SHORT);
        toast0.show();

        //fragement3, create fragment onCreation, pass data from Intent Extras to FragmentTransction
        CarTemperatureFragment tempfrag = new CarTemperatureFragment();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            tempfrag.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.temp_frame_layout, tempfrag).commit();
        }
    }

    protected void onStart() {
        super.onStart();
        Log.i("CarTemperature activity", "In onStart()");

    }
}
