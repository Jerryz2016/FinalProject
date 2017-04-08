package com.jieli.finalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


    /**
     * Created by Haixia Feng  2017-04-03.
     */

    public class HouseGarage extends AppCompatActivity {

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_house_garage);

            HouseGarageFragment frag = new HouseGarageFragment();
            Bundle bun = getIntent().getExtras();
            frag.setArguments(bun);
            getSupportFragmentManager().beginTransaction().add(R.id.house_fragholder, frag).commit();
        }
    }






