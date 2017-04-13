package com.jieli.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


public class FragmentKitchenLight extends Fragment {
    TextView typeText, nameText, statusText;
    SeekBar seekBar;
    Button setting, buttonReturn, on, off;
    int dimmable, position;
    Bundle bundle;
    final String PERCENT = "\u0025";
    ImageView image;
    View root;

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

        //---negative value means the light is turned off, so the negative value should be modified---
        if (Integer.valueOf(bundle.getString("setting")) < 0) {
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

        //---when user slides the seekbar, read the value and set it into TextView, also use it to set the transparent value of the image---
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        //---when on button is clicked, make on button invisible, and the off button visible---
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                on.setVisibility(View.INVISIBLE);
                off.setVisibility(View.VISIBLE);
            }
        });

        //---when off button is clicked, make off button invisible, and the on button visible---
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                off.setVisibility(View.INVISIBLE);
                on.setVisibility(View.VISIBLE);
            }
        });

        //---when setting button is clicked, set the value and show a piece of information to user---
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Set dimmable to " + String.valueOf(dimmable) + PERCENT, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //---when return button is clicked, transfer the relative values back to the calling activity and update the DB---
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (on.getVisibility() == View.VISIBLE) {
                    Intent backIntent = new Intent();
                    backIntent.putExtra("position", position);
                    backIntent.putExtra("setting", String.valueOf(dimmable));
                    backIntent.putExtra("name", nameText.getText().toString());
                    getActivity().setResult(RESULT_OK, backIntent);
                    getActivity().finish();
                }else{
                    Intent backIntent = new Intent();
                    backIntent.putExtra("position", position);
                    backIntent.putExtra("setting", String.valueOf(-dimmable));
                    backIntent.putExtra("name", nameText.getText().toString());
                    getActivity().setResult(RESULT_OK, backIntent);
                    getActivity().finish();
                }

            }
        });
        return root;
    }
}
