
/* File Name: HouseGarageFragment.java
* Author: HaiXia Feng
* Course: CST2335 Graphical Interface Programming
* Assignment: Final Project
* Date: April-7-2017
* Professor: Eric Torunski
* Purpose:
 * This class extends fragment creating a HouseGarageFragment place holder for listView
*/
package com.jieli.finalproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;



/**
 * File name: HouseGarageFragment.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 * This class extends fragment creating a HouseGarageFragment place holder for listView of data
 * obtained from a website data providor
 * Last Revision: April. 6, 2017
 */
public class HouseGarageFragment extends Fragment {
    Long id;//reference to the primary key
    Boolean DoorSwitch;//hold the door switch state: on or off
    Boolean LightSwitch;//hold the light switch state: on or off
    //HouseDatabaseActivity housesettingDetail = null;
    boolean garagedoor=false;
    boolean garagelight=false;
    Switch door;
    Switch light;
    boolean isTablet;

    HouseSettingStartActivity houseSettingStartActivity;

    /**
     * non-arg constructor
     */
    public HouseGarageFragment() {
    }

    /**
     * Constructor
     * @author Haixia Feng
     * @param c Type HouseSettingStartActivity
     */
    public HouseGarageFragment (HouseSettingStartActivity c){
        houseSettingStartActivity= c;
    }

    /**
     * @author Haixia Feng
     * @param b Type Bundle
     *          data transport
     */
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        Bundle bun = getArguments();
        DoorSwitch=bun.getBoolean("DoorSwitch");
        LightSwitch=bun.getBoolean("LightSwitch");
        isTablet = bun.getBoolean("isTablet");
        id = bun.getLong("_id");

    }




    /**
     * This method overrides supper class method updating the HouseGarageFragment object
     * @author Haixia Feng
     * @param inflater Type LayoutInflater
     * @param container Type ViewGroup
     * @param savedInstanceState Type Bundle
     * @return gui Type View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View gui = inflater.inflate(R.layout.house_garage_fragment, null);

        door = (Switch) gui.findViewById(R.id.house_garage_switch);
        light =(Switch) gui.findViewById(R.id.house_garage_switch2);


        door.setChecked(DoorSwitch);
        light.setChecked(LightSwitch);
        Button save = (Button)gui.findViewById(R.id.house_garage_save);


        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (door.isChecked()) {
                    Toast.makeText(view.getContext(), "Opening Garage", Toast.LENGTH_LONG).show();
                    garagedoor = true;
                    garagelight=true;
                } else {
                    Toast.makeText(view.getContext(), "Closing Garage", Toast.LENGTH_LONG).show();
                    garagedoor = false;
                    garagelight=false;
                }
                light.setChecked(garagedoor);

            }
        });


        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (light.isChecked()) {
                    Toast.makeText(view.getContext(), "Opening Garage", Toast.LENGTH_LONG).show();
                    garagelight = true;
                } else {
                    Toast.makeText(view.getContext(), "Closing Garage", Toast.LENGTH_LONG).show();
                    garagelight = false;
                }


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoorSwitch=   door.isChecked();
                LightSwitch=  light.isChecked();
                if (isTablet) {
                    //todo call store method in HouseSettings class to save temp in db
                    //HouseGarageFragment mf = (HouseGarageFragment) getFragmentManager().findFragmentById(R.id.activity_house_garage_fragment); //remove the sw600dp frage
                    //getFragmentManager().beginTransaction().remove(mf).commit();
                    houseSettingStartActivity.updateGarage(id,DoorSwitch,LightSwitch);
                    //houseSettingStartActivity.removeFragment();

                    Toast.makeText(view.getContext(), R.string.house_datasave, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("DoorSwitch", garagedoor);
                    intent.putExtra("LightSwitch", garagelight);
                    intent.putExtra("_id", id);

                    getActivity().setResult(0, intent);
                    getActivity().finish();
                }

            }
        });
        Toast.makeText(gui.getContext(), " Data saved", Toast.LENGTH_LONG).show();

        return gui;
    }

}
