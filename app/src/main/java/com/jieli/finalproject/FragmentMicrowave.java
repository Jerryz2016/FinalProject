/**
 * File name: 	FragmentMicrowave.java
 * Author:  	Group3 Chao Gu
 * Course: 		CST2335 – Graphical Interface Programming
 * Project: 	Final
 * Date: 		April 14, 2017
 * Professor: 	ERIC TORUNSKI
 * Purpose: 	To create a microwave control panel.
 */
package com.jieli.finalproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class FragmentMicrowave extends Fragment {
    /* ATTRIBUTES	-----------------------------------------------------	*/
    TextView textTimer;
    NumberPicker minutePicker, secondPicker;
    Button reset, start, stop;
    Long milliseconds;
    Long minute, second;
    String text;
    CountDownTimer timer;
    View root;

    /**
     * To create a visible view for FragmentMicrowave, initialize the attributes and create some methods
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_microwave, null);

        textTimer = (TextView) root.findViewById(R.id.textTimer);
        minutePicker = (NumberPicker) root.findViewById(R.id.minutePicker);
        secondPicker = (NumberPicker) root.findViewById(R.id.secondPicker);
        reset = (Button) root.findViewById(R.id.button_reset);
        start = (Button) root.findViewById(R.id.button_start);
        stop = (Button) root.findViewById(R.id.button_stop);

        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);
        minutePicker.setValue(0);
        secondPicker.setValue(0);
        milliseconds = 0L;
        minute = 0L;
        second = 0L;
        text = String.format(Locale.getDefault(), "%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
        textTimer.setText(text);
        reset.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
        stop.setVisibility(View.INVISIBLE);


        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) { //---read the value from minute picker and set it into the minute---
                minute = Long.valueOf(newVal);
                milliseconds = minute * 60 * 1000 + second * 1000;
                text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                textTimer.setText(text);
            }
        });


        secondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) { //---read the value from second picker and set it into the second---
                second = Long.valueOf(newVal);
                milliseconds = minute * 60 * 1000 + second * 1000;
                text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                textTimer.setText(text);
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //---when start button is clicked, disable all other button, make the clock start to count down---
                timer = new CountDownTimer(milliseconds, 1000) {
                    public void onTick(long millisUntilFinished) {
                        String text = String.format(Locale.getDefault(), "%02d : %02d",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                        textTimer.setText(text);
                    }

                    public void onFinish() {
                        milliseconds = 0L;
                        text = String.format(Locale.getDefault(), "%02d : %02d",
                                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                        textTimer.setText(text);
                    }
                }.start();
                reset.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //---when reset button is clicked, disable all other button except start button, reset the clock---
                timer.cancel();
                milliseconds = 0L;
                minute = 0L;
                second = 0L;
                minutePicker.setValue(0);
                secondPicker.setValue(0);
                text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                textTimer.setText(text);
                reset.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.INVISIBLE);
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //---when stop button is clicked, disable all other button except start button, reset the clock---
                timer.cancel();
                milliseconds = 0L;
                minute = 0L;
                second = 0L;
                minutePicker.setValue(0);
                secondPicker.setValue(0);
                text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                textTimer.setText(text);
                reset.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                stop.setVisibility(View.INVISIBLE);
            }
        });
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

