package com.jieli.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * CST2335 Final Project-Automobile activity
 * <p>
 * The Class CarTemperature, using an empty framelayout for a phone to add a fragment to
 * display temperature.
 * <p>
 * Group     3
 *
 * @author Jieli Zhang
 * @version v1.0
 *          Date      2017.04.12
 */
//  create empty temperature fragment
public class CarTemperature extends AppCompatActivity {

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
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

    /**
     * On start.
     */
    protected void onStart() {
        super.onStart();
        Log.i("CarTemperature activity", "In onStart()");

	}
}
