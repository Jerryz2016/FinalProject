
/* File Name: HouseTemperature.java
* Author: HaiXia Feng
* Course: CST2335 Graphical Interface Programming
* Assignment: Final Project
* Date: April-7-2017
* Professor: Eric Torunski
* Purpose:
* This class extends AppCompatActivity representing a HouseTemperature object which controls the house
* time-temperature setting scheduls
*/
package com.jieli.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

//  create empty temperature fragment

/**
 * File name: HouseTemperature.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 * This class extends AppCompatActivity representing a HouseTemperature object which holds the house
 * time-temperature setting scheduls view
 * Last Revision: April. 6, 2017
 */

public class HouseTemperature extends AppCompatActivity {

    /**
     * This auto-called method creating the layout holder for the houseTemperature fragment.
     * @author Haixia Feng
     * @param savedInstanceState Type Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_temperature);   //set empty temperature frame layout with id temp_frame_layout

        Toast toast0 = Toast.makeText(this, "Setting Temperature fragement", Toast.LENGTH_SHORT);
        toast0.show();

        //fragement3, create fragment onCreation, pass data from Intent Extras to FragmentTransction
        HouseSettingTemperatureFragment frag = new HouseSettingTemperatureFragment();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            frag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.housetemperature_framelayout, frag).commit();

        }
    }
}//end of the class
