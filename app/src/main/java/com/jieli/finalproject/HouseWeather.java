
/* File Name: HouseWeather.java
* Author: HaiXia Feng
* Course: CST2335 Graphical Interface Programming
* Assignment: Final Project
* Date: April-7-2017
* Professor: Eric Torunski
* Purpose:
* This class extends AppCompatActivity representing a HouseWeather view holder for the inflated fragment
* view group.
*/
package com.jieli.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * File name: HouseWeather.java
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
public class HouseWeather extends AppCompatActivity {

    Context cont;

    /**
     * This auto-called method creating the layout holder for the HouseWeatherFragment view.
     * @author Haixia Feng
     * @param savedInstanceState Type Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_weather);   //set empty temperature frame layout with id temp_frame_layout

      //  HouseGarageFragment frag = new HouseGarageFragment();
        HouseWeatherFragment frag =new HouseWeatherFragment(null);
        Bundle bun = getIntent().getExtras();
        frag.setArguments(bun);
        getSupportFragmentManager().beginTransaction().add(R.id.house_fragholder, frag).commit();



    }//end of the class


















}
