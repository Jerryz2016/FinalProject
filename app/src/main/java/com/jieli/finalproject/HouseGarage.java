/* File Name: HouseGarage.java
 * Author: HaiXia Feng
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Date: April-7-2017
 * Professor: Eric Torunski, David Lareau
 * Purpose:
 * This class extends AppCompatActivity representing a garage object which controls a HouseGarageFragment
 * to set the state of the garage.
 */
package com.jieli.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;



/**
 * File name: HouseGarage.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 * This class extends AppCompatActivity representing a garage object which controls a HouseGarageFragment
 * to set the state of the garage.
 * Last Revision: April. 6, 2017
 */


    public class HouseGarage extends AppCompatActivity {

    private  static final String TAG=HouseGarage.class.getSimpleName();//Debugging information


    /**
     * @author Haixia Feng
     * This method inflates the HouseGarageFragment view for users setting.
     * @param savedInstanceState Type Bundle
     */
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_house_garage);

            HouseGarageFragment frag = new HouseGarageFragment(null);
            Bundle bun = getIntent().getExtras();
            frag.setArguments(bun);
            getSupportFragmentManager().beginTransaction().add(R.id.house_fragholder, frag).commit();


        Log.v(TAG, " End of HouseGarage OnCreate");
        }



    }//End of the class






