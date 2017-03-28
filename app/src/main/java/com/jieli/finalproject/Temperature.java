package com.jieli.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Temperature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Toast toast0 = Toast.makeText(this, "Setting Temperature fragement", Toast.LENGTH_SHORT);
        toast0.show();

        //fragement3, create fragment onCreation, pass data from Intent Extras to FragmentTransction
        CarTemperatureFragment tempfrag = new CarTemperatureFragment();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            tempfrag.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.temp_frame_layout, tempfrag).commit();
        }
    }
}
