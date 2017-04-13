package com.jieli.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


public class FragmentFridge extends Fragment {
    TextView typeText, nameText, temperatureText;
    NumberPicker temperaturePicker;
    Button setting, buttonReturn;
    int temperature, position;
    Bundle bundle;
    final String DEGREE = "\u00b0";
    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_fridge, null);

        typeText = (TextView) root.findViewById(R.id.type_text);
        nameText = (TextView) root.findViewById(R.id.name_text);
        temperatureText = (TextView) root.findViewById(R.id.temperature_text);
        temperaturePicker = (NumberPicker) root.findViewById(R.id.temperaturePicker);
        setting = (Button) root.findViewById(R.id.setting);
        buttonReturn = (Button) root.findViewById(R.id.button_return);

        //---set the picker with negative values, max value and min value---
        String[] s = {"-15", "-14", "-13", "-12", "-11", "-10", "-9", "-8", "-7", "-6",
                "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5"};
        temperaturePicker.setMaxValue(20);
        temperaturePicker.setMinValue(0);
        temperaturePicker.setDisplayedValues(s);
        temperaturePicker.setValue(0);


        bundle = getArguments();
        position = bundle.getInt("position");
        typeText.setText(bundle.getString("type"));
        nameText.setText(bundle.getString("name"));
        temperatureText.setText(bundle.getString("setting") + DEGREE);

        //---read the value from picker---
        temperaturePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temperature = newVal - 15;
            }
        });

        //---when setting button is clicked, set the value and show a piece of information to user---
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperatureText.setText(String.valueOf(temperature) + DEGREE);
                Snackbar.make(v, "Set temperature to " + String.valueOf(temperature) + DEGREE, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //---when return button is clicked, transfer the relative values back to the calling activity and update the DB---
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent();
                backIntent.putExtra("position", position);
                backIntent.putExtra("setting", String.valueOf(temperature));
                backIntent.putExtra("name", nameText.getText().toString());
                getActivity().setResult(RESULT_OK, backIntent);
                getActivity().finish();
            }
        });
        return root;
    }
}
