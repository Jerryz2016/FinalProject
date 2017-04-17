/**
 * File name: 	FragmentFridge.java
 * Author:  	Group3 Chao Gu
 * Course: 		CST2335 – Graphical Interface Programming
 * Project: 	Final
 * Date: 		April 14, 2017
 * Professor: 	ERIC TORUNSKI
 * Purpose: 	To create a fridge control panel.
 */
package com.jieli.finalproject;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * The FragmentFridge Class is to create a control panel of fridge.
 * A number picker is to allow user to set the temperature.
 * Click on setting button, the temperature will be set as the same as the number of number picker
 *
 * @author Group3 Chao Gu
 * @version v1.0.
 */
public class FragmentFridge extends Fragment {
    /* ATTRIBUTES	-----------------------------------------------------	*/
    TextView typeText, nameText, temperatureText;
    NumberPicker temperaturePicker;
    Button setting, buttonReturn;
    int temperature, position;
    Bundle bundle;
    final String DEGREE = "\u00b0";
    View root;
    KitchenStartActivity start = null;
    /* CONSTRUCTORS	-----------------------------------------------------	*/
    public FragmentFridge(){

    }

    public FragmentFridge(KitchenStartActivity k){
        start = k;
    }
    /**
     * To create a visible view for FragmentFridge, initialize the attributes and create some methods
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_fridge, null);

        typeText = (TextView) root.findViewById(R.id.type_text);
        nameText = (TextView) root.findViewById(R.id.name_text);
        temperatureText = (TextView) root.findViewById(R.id.temperature_text);
        temperaturePicker = (NumberPicker) root.findViewById(R.id.temperaturePicker);
        setting = (Button) root.findViewById(R.id.setting);
        buttonReturn = (Button) root.findViewById(R.id.button_return);


        String[] s = {"-15", "-14", "-13", "-12", "-11", "-10", "-9", "-8", "-7", "-6",  //---set the picker with negative values, max value and min value---
                "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5"};
        temperaturePicker.setMaxValue(20);
        temperaturePicker.setMinValue(0);
        temperaturePicker.setDisplayedValues(s);
        temperaturePicker.setValue(15);


        bundle = getArguments();
        position = bundle.getInt("position");
        typeText.setText(bundle.getString("type"));
        nameText.setText(bundle.getString("name"));
        temperatureText.setText(bundle.getString("setting") + DEGREE);


        temperaturePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {      //---read the value from picker---
                temperature = newVal - 15;
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {        //---when setting button is clicked, set the value and show a piece of information to user---
                temperatureText.setText(String.valueOf(temperature) + DEGREE);
                Snackbar.make(v, "Set temperature to " + String.valueOf(temperature) + DEGREE, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                start.updateDB(nameText.getText().toString(), String.valueOf(temperature));
            }
        });

        return root;
    }
}
