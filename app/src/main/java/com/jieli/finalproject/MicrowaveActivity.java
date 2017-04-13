package com.jieli.finalproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MicrowaveActivity extends AppCompatActivity {
    TextView textTimer;
    NumberPicker minutePicker, secondPicker;
    Button reset, start, stop;
    Long milliseconds;
    Long minute, second;
    String text;
    CountDownTimer timer;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_microwave);

        textTimer = (TextView) findViewById(R.id.textTimer);
        minutePicker = (NumberPicker) findViewById(R.id.minutePicker);
        secondPicker = (NumberPicker) findViewById(R.id.secondPicker);
        reset = (Button) findViewById(R.id.button_reset);
        start = (Button) findViewById(R.id.button_start);
        stop = (Button) findViewById(R.id.button_stop);

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


        //---read the value from minute picker and set it into the minute---
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange (NumberPicker picker, int oldVal, int newVal){
                minute = Long.valueOf(newVal);
                milliseconds = minute*60*1000 + second*1000;
                text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                textTimer.setText(text);
            }
        });

        //---read the value from second picker and set it into the second---
        secondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange (NumberPicker picker, int oldVal, int newVal){
                second = Long.valueOf(newVal);
                milliseconds = minute*60*1000 + second*1000;
                text = String.format(Locale.getDefault(), "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60);
                textTimer.setText(text);
            }
        });

        //---when start button is clicked, disable all other button, make the clock start to count down---
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //---when reset button is clicked, disable all other button except start button, reset the clock---
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //---when stop button is clicked, disable all other button except start button, reset the clock---
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        returnButton = (Button) findViewById(R.id.button1);
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}