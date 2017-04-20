/**
 * File name: 	FragmentKitchenLight.java
 * Author:  	Group3 Chao Gu
 * Course: 		CST2335 – Graphical Interface Programming
 * Project: 	Final
 * Date: 		April 14, 2017
 * Professor: 	ERIC TORUNSKI
 * Purpose: 	To create a kitchen light control panel.
 */
package com.jieli.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * The FragmentKitchenLight Class is to create a control panel of kitchen light.
 * A number picker is to allow user to set the dimmable.
 * Click on setting button, the dimmable will be set as the same as the number of number picker
 *
 * @author Group3 Chao Gu
 * @version v1.0.
 */

public class FragmentKitchenLight extends Fragment {
    /* ATTRIBUTES	-----------------------------------------------------	*/
    TextView typeText, nameText, statusText;
    SeekBar seekBar;
    Button setting, buttonReturn, on, off;
    int dimmable, position;
    Bundle bundle;
    final String PERCENT = "\u0025";
    ImageView image;
    View root;
    KitchenStartActivity start = null;
    /* CONSTRUCTORS	-----------------------------------------------------	*/
    public FragmentKitchenLight(){

    }

    public FragmentKitchenLight(KitchenStartActivity k){
        start = k;
    }

    /**
     * To create a visible view for FragmentKitchenLight, initialize the attributes and create some methods
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_kitchenlight, null);

        typeText = (TextView) root.findViewById(R.id.type_text);
        nameText = (TextView) root.findViewById(R.id.name_text);
        statusText = (TextView) root.findViewById(R.id.status_text);
        seekBar = (SeekBar) root.findViewById(R.id.seekBar);
        on = (Button) root.findViewById(R.id.on);
        off = (Button) root.findViewById(R.id.off);
        setting = (Button) root.findViewById(R.id.setting);
        buttonReturn = (Button) root.findViewById(R.id.button_return);
        image = (ImageView) root.findViewById(R.id.image);

        seekBar.setMax(100);
        seekBar.setProgress(0);

        bundle = getArguments();
        position = bundle.getInt("position", 1);
        typeText.setText(bundle.getString("type"));
        nameText.setText(bundle.getString("name"));


        if (Integer.valueOf(bundle.getString("setting")) < 0) {   //---negative value means the light is turned off, so the negative value should be modified
            statusText.setText(bundle.getString("setting").replace("-", "") + PERCENT);
            seekBar.setProgress(-Integer.valueOf(bundle.getString("setting")));
            image.setImageAlpha(-Integer.valueOf(bundle.getString("setting"))*2);
            on.setVisibility(View.INVISIBLE);
            off.setVisibility(View.VISIBLE);

        } else {
            statusText.setText(bundle.getString("setting") + PERCENT);
            seekBar.setProgress(Integer.valueOf(bundle.getString("setting")));
            image.setImageAlpha(Integer.valueOf(bundle.getString("setting"))*2);
            on.setVisibility(View.VISIBLE);
            off.setVisibility(View.INVISIBLE);
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//--- when user slides the seekbar, read the value and set it into TextView,
                                                                                  //--- also use it to set the transparent value of the image---
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                dimmable = progress;
                statusText.setText(String.valueOf(dimmable) + PERCENT);
                image.setImageAlpha(progress*2);
            }
        });


        on.setOnClickListener(new View.OnClickListener() {  //---when on button is clicked, make on button invisible, and the off button visible---
            @Override
            public void onClick(View v) {
                on.setVisibility(View.INVISIBLE);
                off.setVisibility(View.VISIBLE);
            }
        });


        off.setOnClickListener(new View.OnClickListener() {  //---when off button is clicked, make off button invisible, and the on button visible---
            @Override
            public void onClick(View v) {
                off.setVisibility(View.INVISIBLE);
                on.setVisibility(View.VISIBLE);
            }
        });


        setting.setOnClickListener(new View.OnClickListener() { //---when setting button is clicked, set the value and show a piece of information to user---
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Set dimmable to " + String.valueOf(dimmable) + PERCENT, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                if (on.getVisibility() == View.VISIBLE) {
                    start.updateDB(nameText.getText().toString(), String.valueOf(dimmable));
                }else{
                    start.updateDB(nameText.getText().toString(), String.valueOf(-dimmable));
                }
            }
        });


        return root;
    }
}
