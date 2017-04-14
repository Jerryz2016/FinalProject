/**
 * File name: 	FridgeActivity.java
 * Author:  	Group3 Chao Gu
 * Course: 		CST2335 – Graphical Interface Programming
 * Project: 	Final
 * Date: 		April 14, 2017
 * Professor: 	ERIC TORUNSKI
 * Purpose: 	To create a fridge control panel.
 */
package com.jieli.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * The FridgeActivity Class is to create a control panel of fridge.
 * A number picker is to allow user to set the temperature.
 * Click on setting button, the temperature will be set as the same as the number of number picker
 *
 * @author Group3 Chao Gu
 * @version v1.0.
 */
public class FridgeActivity extends Activity {
    /* ATTRIBUTES	-----------------------------------------------------	*/
    TextView typeText, nameText, temperatureText;
    NumberPicker temperaturePicker;
    Button setting, buttonReturn;
    int temperature, position;
    Intent intent;
    final String DEGREE = "\u00b0";


    /**
     * To create a visible view for FridgeActivity, initialize the attributes and create some methods
     * @param savedInstanceState
     */
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

        String[] s = {"-15", "-14", "-13", "-12", "-11", "-10", "-9", "-8", "-7", "-6",  //---_set the picker with negative values, max value and min value
                "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5"};
        temperaturePicker.setMaxValue(20);
        temperaturePicker.setMinValue(0);
        temperaturePicker.setDisplayedValues(s);
        temperaturePicker.setValue(15);

        intent = getIntent();
        position = intent.getIntExtra("position", 1);
        typeText.setText(intent.getStringExtra("type"));
        nameText.setText(intent.getStringExtra("name"));
        temperatureText.setText(intent.getStringExtra("setting")+DEGREE);


        temperaturePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {  //----read the value from picker
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temperature = newVal - 15;
            }
        });


        setting.setOnClickListener(new View.OnClickListener() { //----when setting button is clicked, set the value and show a piece of information to user
            @Override
            public void onClick(View v) {
                temperatureText.setText(String.valueOf(temperature) + DEGREE);
                Snackbar.make(v, "Set temperature to "+ String.valueOf(temperature) + DEGREE, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        buttonReturn.setOnClickListener(new View.OnClickListener() {   //--- when return button is clicked, transfer the relative values back to the calling
                                                                       //--- activity and update the DB
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                backIntent.putExtra("position", position);
                backIntent.putExtra("setting", String.valueOf(temperature));
                backIntent.putExtra("name", nameText.getText().toString());
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });

    }
}
