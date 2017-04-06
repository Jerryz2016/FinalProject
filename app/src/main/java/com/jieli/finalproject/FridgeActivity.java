package com.jieli.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class FridgeActivity extends AppCompatActivity {
    TextView typeText, nameText, temperatureText;
    NumberPicker temperaturePicker;
    Button setting, buttonReturn;
    int temperature, position;
    Intent intent;
    final String DEGREE = "\u00b0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        typeText = (TextView) findViewById(R.id.type_text);
        nameText = (TextView) findViewById(R.id.name_text);
        temperatureText = (TextView) findViewById(R.id.temperature_text);
        temperaturePicker = (NumberPicker) findViewById(R.id.temperaturePicker);
        setting = (Button) findViewById(R.id.setting);
        buttonReturn = (Button) findViewById(R.id.button_return);

        temperaturePicker.setMaxValue(10);
        temperaturePicker.setMinValue(0);
        temperaturePicker.setValue(0);

        intent = getIntent();
        position = intent.getIntExtra("position", 0);
        typeText.setText(intent.getStringExtra("type"));
        nameText.setText(intent.getStringExtra("name"));
        temperatureText.setText(intent.getStringExtra("setting"));

        temperaturePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temperature = newVal;
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperatureText.setText(String.valueOf(temperature) + DEGREE);
                Snackbar.make(v, "Set temperature to "+ String.valueOf(temperature) + DEGREE, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                backIntent.putExtra("position", position);
                backIntent.putExtra("setting", String.valueOf(temperature) + DEGREE);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });

    }
}
