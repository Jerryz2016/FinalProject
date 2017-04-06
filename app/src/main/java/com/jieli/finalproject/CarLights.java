package com.jieli.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class CarLights extends AppCompatActivity {
    private ToggleButton headlight;
    private SeekBar dimlight;
    private ProgressBar progress;
    private Button lightOK;

    private Bundle lightset;
    private Boolean headlightSet;
    private int dimlightSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_lights);
        // definition of lights views
        headlight = (ToggleButton) findViewById(R.id.headlight_set);
        dimlight = (SeekBar) findViewById(R.id.dimmlight_set);
        progress = (ProgressBar) findViewById(R.id.light_progress);
        lightOK = (Button) findViewById(R.id.light_ok);
        // get lights settings from user sharedPreference
        lightset = this.getIntent().getExtras();
        headlightSet = (lightset.getString("headlights").equals("1"));
        String dim = lightset.getString("dimmable");
        dimlightSet = Integer.parseInt(dim);
        // set lights as user prefered from sharedPreference
        headlight.setChecked(headlightSet);
        dimlight.setProgress(dimlightSet);

        headlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headlight.isChecked()) {
                    Snackbar.make(v, "HeadLights set HIGH", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(v, "HeadLights set NORMAL", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        dimlight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Snackbar.make(findViewById(R.id.car_lights_layout), "Dimmable Light: " + String.valueOf(progress), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lightOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headlightSet = headlight.isChecked();
                dimlightSet = dimlight.getProgress();

                Intent data = new Intent();
                if (headlightSet)
                    data.putExtra("headlights", "1");
                else data.putExtra("headlights", "0");
                data.putExtra("dimmable", Integer.toString(dimlightSet));
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}
