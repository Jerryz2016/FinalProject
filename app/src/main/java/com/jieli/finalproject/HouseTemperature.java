package com.jieli.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//  create empty temperature fragment
public class HouseTemperature extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_temperature);   //set empty temperature frame layout with id temp_frame_layout

        Toast toast0 = Toast.makeText(this, "Setting Temperature fragement", Toast.LENGTH_SHORT);
        toast0.show();

        //fragement3, create fragment onCreation, pass data from Intent Extras to FragmentTransction
        HouseSettingTemperatureFragment tempfrag = new HouseSettingTemperatureFragment();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            tempfrag.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.housetemperature_framelayout, tempfrag).commit();
        }
    }
}
