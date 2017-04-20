
/* File Name: HouseDatabaseActivity.java
* Author: HaiXia Feng
* Course: CST2335 Graphical Interface Programming
* Assignment: Final Project
* Date: April-7-2017
* Professor: Eric Torunski
* Purpose:
* This class extends AppCompatActivity creating database object and storing input data
*/

package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * File name: HouseDatabaseActivity.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 * This class extends AppCompatActivity creating database object and storing input data
 * Last Revision: April. 6, 2017
 */

public class HouseDatabaseActivity extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter ;
    private boolean isTablet;
    HouseGarageFragment houseGarageFragment;

    SQLiteDatabase db;
    String ACTIVITY_NAME = "HouseDatabaseActivity";

    Cursor results;
    protected HouseDatabaseHelper dbHelper;

    /**
     *
     * @param savedInstanceState Type Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housesetting_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isTablet = (findViewById(R.id.house_fragholder) != null);

        dbHelper=new HouseDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        results = db.query(false, HouseDatabaseHelper.DATABASE_NAME,
                new String[]{HouseDatabaseHelper.KEY_ID,
                        HouseDatabaseHelper.KEY_LightSwitch,
                        HouseDatabaseHelper.KEY_DoorSwitch,
                        HouseDatabaseHelper.KEY_Time,
                        HouseDatabaseHelper.Key_Temp},
                null, null, null, null, null, null);


        results.moveToFirst();

        ListView HouseView =(ListView)findViewById(R.id.house_listview);

        String [] item = {"House Garage", "House Temperature", "House Weather"};
        arrayList= new ArrayList<>(Arrays.asList(item));
        adapter =new ArrayAdapter<>(this,R.layout.activity_housesetting_start,R.id.houseSettingButton_List,arrayList);
        HouseView.setAdapter(adapter);

        HouseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("HouseView ", "onItemClick: " + i + " " + l);
                long id = results.getLong(results.getColumnIndex(HouseDatabaseHelper.KEY_ID));
                int LightSwitch =results.getInt(results.getColumnIndex(HouseDatabaseHelper.KEY_LightSwitch));
                int DoorSwitch =results.getInt(results.getColumnIndex(HouseDatabaseHelper.KEY_DoorSwitch));
                String Time =results.getString(results.getColumnIndex(HouseDatabaseHelper.KEY_Time));
                String Temp =results.getString(results.getColumnIndex(HouseDatabaseHelper.KEY_Time));

                Bundle bun = new Bundle();
                bun.putLong("_id", id);//l is the database ID of selected item
                bun.putBoolean("DoorSwitch",DoorSwitch!=0);
                bun.putBoolean("LightSwitch",LightSwitch!=0);
                bun.putString("Time",Time);
                bun.putString("Temp",Temp);

                if (adapterView.getItemAtPosition(i).equals("House Weather")) {

                    Intent HouseWeatherSetting = new Intent(HouseDatabaseActivity.this, HouseWeather.class);
                    startActivityForResult(HouseWeatherSetting, 5);

                }else if (adapterView.getItemAtPosition(i).equals("House Temperature")) {

                    Intent HouseTempSetting = new Intent(HouseDatabaseActivity.this, HouseTemperature.class);
                    //        HouseTempSetting.putExtra("Time",time);
                    //       HouseTempSetting.putExtra("Temp",temp);
                    //          HouseTempSetting.putExtras(bun);
                    startActivityForResult(HouseTempSetting, 5);

                }else  if (adapterView.getItemAtPosition(i).equals("House Garage")) {

                    if (isTablet) {

                        //houseGarageFragment = new HouseGarageFragment(HouseSettingStartActivity.this);
                        houseGarageFragment = new HouseGarageFragment(null);
                        houseGarageFragment.setArguments(bun);
                        getSupportFragmentManager().beginTransaction().replace(R.id.house_fragholder, houseGarageFragment).commit();

                    }
                    //step 3 if a phone, transition to empty Activity that has FrameLayout
                    else //isPhone
                    {
                        Intent intnt = new Intent(HouseDatabaseActivity.this, HouseGarage.class);
                        intnt.putExtra("_id", id);
                        intnt.putExtra("DoorSwitch",DoorSwitch);
                        intnt.putExtra("LightSwitch",LightSwitch);

                        startActivityForResult(intnt,5);
                    }
                }
            }
        });
    }

    /**
     * This method sets the garage door and switch states
     * @param id Type long
     *           reference to the primary key of the HourseItems table
     * @param door Type boolean
     *             holds the state of the door: open or close
     * @param light Type boolean
     *              holds the state of the light: on or off
     */
    public void updateGarage(long id, boolean door, boolean light) {

        ContentValues values = new ContentValues();
        values.put(HouseDatabaseHelper.KEY_DoorSwitch, door ? 1:0);
        values.put(HouseDatabaseHelper.KEY_LightSwitch,light ? 1:0);
        db.update(HouseDatabaseHelper.DATABASE_NAME, values, HouseDatabaseHelper.KEY_ID + "=" + id, null);
        refreshDB();
    }

    /**
     * This method toast the data from the database and display the results on the screen
     * @param requestCode Type int
     * @param resultCode  Type int
     * @param data        Type Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Bundle bun = data.getExtras();
        Long id = bun.getLong("_id");
        boolean DoorSwitch = bun.getBoolean("DoorSwitch");
        boolean LightSwitch = bun.getBoolean("LightSwitch");
        String toastText = bun.getString("Response");

        if (requestCode==5 && resultCode == 0) {
            updateGarage(id, DoorSwitch,LightSwitch);

            Toast toast = Toast.makeText(HouseDatabaseActivity.this, toastText, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * This method updates the database
     * @author Haixia Feng
     *
     */
    private void refreshDB() {
        dbHelper = new HouseDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        results = db.query(false, HouseDatabaseHelper.DATABASE_NAME,
                new String[] {  HouseDatabaseHelper.KEY_ID,
                        HouseDatabaseHelper.KEY_LightSwitch,
                        HouseDatabaseHelper.KEY_DoorSwitch,
                        HouseDatabaseHelper.KEY_Time,
                        HouseDatabaseHelper.Key_Temp  },
                null, null, null, null, null, null);

        int rows = results.getCount(); //number of rows returned
        results.moveToFirst(); //move to first result
    }


    /**
     * This mehtod remove the old fragment after the data saved
     * @author Haixia Feng
     */
    public void removeFragment() {
        getSupportFragmentManager().beginTransaction().remove(houseGarageFragment).commit();
    }

    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        db.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.house_menu, menu);
//        return true;
//
//    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_housesetting_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

}//end of the class
