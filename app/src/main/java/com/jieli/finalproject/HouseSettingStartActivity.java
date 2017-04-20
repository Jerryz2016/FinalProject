/* File Name: HouseSettingStartActivity.java
 * Author: HaiXia Feng
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Date: April-7-2017
 * Professor: Eric Torunski, David Lareau
 * Purpose:
 * This class extends AppCompatActivity representing a house object which is the main entrance of the
 * sub-activity of four for this final project.
 */
package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;




/**
 * File name: HouseSettingStartActivity.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 * This class extends AppCompatActivity representing a house object which is the main entrance of the
 * sub-activity of four for this final project.
 * Last Revision: April. 6, 2017
 */

public class HouseSettingStartActivity extends AppCompatActivity {

    /**
     * The following are the fields of the class:
     * ctx: Context instance, provides context for Intent class
     * listView: reference to ListView ViewGroup
     * houseSetting:
     * String array references to three sub-activity names: Garage, House Temperature, Outside Weather
     * <p>
     * isTablet:Boolean for choosing proper devices
     * dbHelper:Type HouseDatabaseHelper
     * db: Type SQLiteDatabase
     * results: Type Cursor
     * A Cursor object for retrieving data from the database
     * Retrieve data from database
     * <p>
     * bun: Type Bundle
     * A collection of data for the database-stores house temperature schedule: time and temperature
     * TAG: Type String
     * A string for debugging output
     */

    private Context ctx;
    private String timeSchedule, temp;//holding input time and temperature
    private ListView listView;
    private String[] houseSetting = {"Garage", "House Temperature", "Outside Weather"};
    private Boolean isTablet;  // for to check if a phone or tablet
    protected HouseDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor results;
    private Bundle bun;
    private static final String TAG = HouseSettingStartActivity.class.getSimpleName();


    /**
     * @param savedInstanceState Type Bundle
     * @author Haixia Feng
     * This method is called automaticall when the activity started.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_housesetting_start);

        retrieveData();
        updateHouseSetting();
        exitSetting();
        Log.d(TAG, " End of OnCreate");
    }//end of oncreate()



    //delete the time and temp for the house setting

    /**
     * @author Haixia Feng
     * This method deletes the stored settings from the database.
     * @param id Type Long
     * reference to the primary key of the table Houseitems within the database, HouseDatabase
     */
    public void deleteschedule(long id) {
//use id to delete the row of the database
//        db.delete(HouseDatabaseHelper.TABLE_NAME, "_id=" + id , null);
//        //refresh database
        refreshDB();

        db.delete(HouseDatabaseHelper.TABLE_NAME, "_id=?", new String[]{Long.toString(id)});
        results = db.query(false, HouseDatabaseHelper.TABLE_NAME,
                new String[]{HouseDatabaseHelper.KEY_ID,
                        HouseDatabaseHelper.KEY_LightSwitch,
                        HouseDatabaseHelper.KEY_DoorSwitch,
                        HouseDatabaseHelper.KEY_Time,
                        HouseDatabaseHelper.Key_Temp
                },
                null, null, null, null, null, null);
        ;
//        adapter.changeCursor(cursor);
        refreshDB();
    }

    // use to update the garage setting

    /**
     * @author Haixia Feng
     * This method records of the garage setting within the database
     * @param id Type long
     *           refercence to the primary key of the HouseItems table
     * @param door Type boolean
     *             reference to the stored state of the garage door
     * @param light Type boolean
     *              reference to the stored state of the garage light
     */
    public void updateGarage(long id, boolean door, boolean light) {
//same id , to update the doorswitch and lightswitch
        ContentValues values = new ContentValues();
        values.put(HouseDatabaseHelper.KEY_DoorSwitch, door ? 1 : 0);
        values.put(HouseDatabaseHelper.KEY_LightSwitch, light ? 1 : 0);
        db.update(HouseDatabaseHelper.TABLE_NAME, values, HouseDatabaseHelper.KEY_ID + "=" + id, null);
        refreshDB();
        if (results.getCount() <= 0) {
            db.insert(HouseDatabaseHelper.TABLE_NAME, null, values);
            refreshDB();
        }
    }

    //update new time and temperature

    /**
     * @author Haixia Feng
     * This method updates house time-temperature schedules and inserts into the HouseItems table
     * @param id Type long
     *           reference to the primary key of the HouseItems table
     * @param Time Type String
     *             reference to the setting time
     * @param Temp Type String
     *             reference to the setting, temperature
     */
    public void updateTemp(long id, String Time, String Temp) {

        ContentValues values = new ContentValues();
        values.put(HouseDatabaseHelper.KEY_Time, Time);
        values.put(HouseDatabaseHelper.Key_Temp, Temp);

        db.insert(HouseDatabaseHelper.TABLE_NAME, null, values);
        refreshDB();
    }

    // update temp only with the same time
    public void EditTemp(long id, String Temp) {

        ContentValues values = new ContentValues();
        values.put(HouseDatabaseHelper.Key_Temp, Temp);
        db.update(HouseDatabaseHelper.TABLE_NAME, values, HouseDatabaseHelper.KEY_ID + "=" + id, null);
        refreshDB();

    }

    //refresh the database
    private void refreshDB() {
        //dbHelper = new HouseDatabaseHelper(this);
        //db = dbHelper.getWritableDatabase();

        results = db.query(false, HouseDatabaseHelper.TABLE_NAME,
                new String[]{HouseDatabaseHelper.KEY_ID,
                        HouseDatabaseHelper.KEY_LightSwitch,
                        HouseDatabaseHelper.KEY_DoorSwitch,
                        HouseDatabaseHelper.KEY_Time,
                        HouseDatabaseHelper.Key_Temp
                },
                null, null, null, null, null, null);

        int rows = results.getCount(); //number of rows returned
        results.moveToFirst(); //move to first result
    }



    /**
     *@author Haixia Feng
     * @param requestCode Type int
     * @param resultCode  Type int
     * @param data  Type Intent
     */
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bun;
        if (data != null) {
            //results  code 0 from garage setting
            if (requestCode == 10 && resultCode == 0) {
                bun = data.getExtras();
                Long id = bun.getLong("_id");
                boolean DoorSwitch = bun.getBoolean("DoorSwitch");
                boolean LightSwitch = bun.getBoolean("LightSwitch");
                updateGarage(id, DoorSwitch, LightSwitch);
            }
            //ressult code i from house Temp setting for update time and temp
            if (requestCode == 10 && resultCode == 1) {
                bun = data.getExtras();
                Long id = bun.getLong("_id");
                String times = bun.getString("Time");
                String temps = bun.getString("Temp");
                updateTemp(id, times, temps);
            }
            //result code 2 from house temp setting for delete time and temp
            if (requestCode == 10&& resultCode == 2) {
                bun = data.getExtras();
                Long id = bun.getLong("_id");
                deleteschedule(id);
            }
            //result code 3 from house temp setting for update only temp with the same time
            if (requestCode == 10&& resultCode == 3) {
                bun = data.getExtras();
                Long id = bun.getLong("_id");
                String temps = bun.getString("Temp");
                EditTemp(id, temps);
            }
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case 1:                // temperature
                        Bundle extras = data.getExtras();
                        temp = (String) extras.get("Temperature");
                        //  store in db
                }
            }
        }
    }

    //activity life cycle methods
    public void storeSettings() {

    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume");

    }

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart");
    }

    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    public void onDestory() {
        super.onDestroy();

        Log.e(TAG, "onDestroy");
//              if (cursor != null) cursor.close();
//              if (dbHelper != null)  dbHelper.close();
    }

    /**
     * This method retrieves data from the database and displays on the screen
     * @author Haixia Feng
     */
    private void retrieveData() {

        listView = (ListView) findViewById(R.id.houseSettingButton_List);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bun = new Bundle();
                if (results.getCount() > 0) {
                    id = results.getLong(results.getColumnIndex(HouseDatabaseHelper.KEY_ID));
                    int LightSwitch = results.getInt(results.getColumnIndex(HouseDatabaseHelper.KEY_LightSwitch));
                    int DoorSwitch = results.getInt(results.getColumnIndex(HouseDatabaseHelper.KEY_DoorSwitch));
                    String Time = results.getString(results.getColumnIndex(HouseDatabaseHelper.KEY_Time));
                    String Temp = results.getString(results.getColumnIndex(HouseDatabaseHelper.Key_Temp));


                    //id is the database _id
                    bun.putLong("_id", id);
                    // boolean DoorSwitch
                    bun.putBoolean("DoorSwitch", DoorSwitch != 0);
                    // Boolean LightSwitch
                    bun.putBoolean("LightSwitch", LightSwitch != 0);
                    //String Time
                    bun.putString("Time", Time);
                    // String temp
                    bun.putString("Temp", Temp);
                }
                bun.putBoolean("isTablet", isTablet);

                switch (position) {

                    case 0: //setting Garage
                        Log.d("listview", "Garage");
                        Toast toast0 = Toast.makeText(ctx, "Setting Garage", Toast.LENGTH_SHORT);
                        toast0.show();
                        if (isTablet) {
                            HouseGarageFragment frag = new HouseGarageFragment(HouseSettingStartActivity.this);
                            frag.setArguments(bun);
                            getSupportFragmentManager().beginTransaction().replace(R.id.house_fragmentholder, frag).commit();
                        } else {
                            Bundle bundle0 = new Bundle();
                            Intent temIntent = new Intent(ctx, HouseGarage.class);
                            temIntent.putExtras(bun);
                            startActivityForResult(temIntent, 10);
                        }
                        break;

                    case 1: //setting temperature
                        Log.d("listview", "House Temperature");
                        Toast toast1 = Toast.makeText(ctx, "Setting Time and Temperature", Toast.LENGTH_SHORT);
                        toast1.show();
                        if (isTablet) {
                            HouseSettingTemperatureFragment frag;
                            frag = new HouseSettingTemperatureFragment(HouseSettingStartActivity.this);
                            //bun =new Bundle();
                            frag.setArguments(bun);
                            getSupportFragmentManager().beginTransaction().replace(R.id.house_fragmentholder, frag).commit();
                        }
                        //step 3 if a phone, transition to empty Activity that has FrameLayout
                        else //isPhone
                        {
                            //
                            Bundle bundle = new Bundle();
                            bundle.putString("Time", timeSchedule);
                            bundle.putString("Temperature", temp);
                            Intent temIntent = new Intent(ctx, HouseTemperature.class);
                            temIntent.putExtras(bundle);
                            startActivityForResult(temIntent, 10);
                        }

                        break;

                    case 2: //setting Outside Weather
                        Log.d("listview", "Outside Weather");
                        Toast toast2 = Toast.makeText(ctx, "Start Ottawa Weather", Toast.LENGTH_SHORT);
                        toast2.show();

                        if (isTablet) {
                            HouseWeatherFragment frag;
                            frag = new HouseWeatherFragment(HouseSettingStartActivity.this);
                            //bun =new Bundle();
                            frag.setArguments(bun);
                            getSupportFragmentManager().beginTransaction().replace(R.id.house_fragmentholder, frag).commit();
                        }
                        //step 3 if a phone, transition to empty Activity that has FrameLayout
                        else //isPhone
                        {
                            Intent intnt = new Intent(HouseSettingStartActivity.this, HouseWeather.class);
                            intnt.putExtra("ID", id);
                            startActivity(intnt); //go to view fragment details
                        }



                        break;

                }
            }
        });


    }


    /**
     * @author Haixia Feng
     * This method updates the settings.
          */
    private void updateHouseSetting() {
        isTablet = (findViewById(R.id.house_fragmentholder) != null);

        dbHelper = new HouseDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        //db.execSQL(HouseDatabaseHelper.DROP_TABLE_MESSAGE);
        //db.execSQL(HouseDatabaseHelper.CREATE_TABLE_MESSAGE);

        // get the Cursor with query
        results = db.query(false, HouseDatabaseHelper.TABLE_NAME,
                new String[]{HouseDatabaseHelper.KEY_ID,
                        HouseDatabaseHelper.KEY_LightSwitch,
                        HouseDatabaseHelper.KEY_DoorSwitch,
                        HouseDatabaseHelper.KEY_Time,
                        HouseDatabaseHelper.Key_Temp
                },
                null, null, null, null, null, null);

        results.moveToLast();


        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, houseSetting));
    }

    //exit setting

    /**
     * @author Haixia Feng
     * This method exits the house setting activity
     */
    private void exitSetting()
    {
    Button houseExitButton = (Button) findViewById(R.id.houseSettingButton1);
        houseExitButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        finish();
    }
    });
}
}
