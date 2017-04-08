package com.jieli.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fhx20 on 4/6/2017.
 */

public class OutsideWeather extends AppCompatActivity {

    Context cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside_weather);   //set empty temperature frame layout with id temp_frame_layout

      //  HouseGarageFragment frag = new HouseGarageFragment();
        OutsideWeatherFragment frag =new OutsideWeatherFragment(null);
        Bundle bun = getIntent().getExtras();
        frag.setArguments(bun);
        getSupportFragmentManager().beginTransaction().add(R.id.house_fragholder, frag).commit();



    }


















}
