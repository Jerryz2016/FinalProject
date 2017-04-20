
/* File Name: HouseSettingTemperatureFragment.java
* Author: HaiXia Feng
* Course: CST2335 Graphical Interface Programming
* Assignment: Final Project
* Date: April-7-2017
* Professor: Eric Torunski
* Purpose:
 * This class extends fragment creating and controlling a HouseSettingTemperatureFragment view
*/
package com.jieli.finalproject;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Date;

/**
 * File name: HouseSettingTemperatureFragment.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 * This class extends fragment creating and controlling a HouseSettingTemperatureFragment view
 * Last Revision: April. 6, 2017
 */

public class HouseSettingTemperatureFragment extends Fragment {
    Context parent;
    String houseTimeSchedule;
    String houseTemp;
    Date date;
    boolean isTablet;
    TextView textTime;
    TextView textTemperature;
    HouseSettingStartActivity houseSettingStartActivity;
    View gui;
    long id;
    String time;
    String temp;
    //HousesettingDetail housesettingDetail;

    TextView inputTime;
    TextView inputTemperature;

    String timeNewItem;
    String tempuratureNewItem;

    Dialog dialog;
    ListView listview;
    HouseDatabaseHelper houseDatabaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    Context ctx;

    /**
     * non-constructor
     */
    public HouseSettingTemperatureFragment() {
        super();
    }

    /**
     * Constructor
     * @param house Type HouseSettingStartActivity
     */
    public HouseSettingTemperatureFragment(HouseSettingStartActivity house) {
        houseSettingStartActivity = house;
    }

    //no matter how you got here, the data is in the getArguments

    /**
     * This auto-called method initializing fields
     * @author Haixia Feng
     * @param b Type Bundle
     */
    @Override
    public void onCreate(Bundle b) {
        ctx = getActivity();
        super.onCreate(b);
        Bundle bundle = getArguments();
        houseTemp = bundle.getString("Temperature");
        isTablet = bundle.getBoolean("isTablet");
        id = bundle.getLong("_id");
        time = bundle.getString("Time");
        temp = bundle.getString("Temp");
    }

    /**
     * This method sets the super context
     * @author Haixia Feng
     * @param context Type Context
     *
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }
    //The Cursor of that get the database information, return Cursor

    /**
     * This mehtod returns a Cursor object for mapping the database table
     * @author Haixia Feng
     * @return cursor1 Type Cursor
     *
     */
    public  Cursor  getCursor(){
        Cursor cursor1 =db.query(false, HouseDatabaseHelper.TABLE_NAME,
                new String[]{

                        HouseDatabaseHelper.KEY_Time,
                        HouseDatabaseHelper.KEY_ID,
                        HouseDatabaseHelper.Key_Temp,
                },
                null, null, null, null, HouseDatabaseHelper.KEY_Time + " DESC", null);
        return cursor1;
    }



    /**
     * This method overrides supper class method updating the HouseSettingTemperatureFragment object
     * @author Haixia Feng
     * @param inflater Type LayoutInflater
     * @param container Type ViewGroup
     * @param savedInstanceState Type Bundle
     * @return gui Type View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //set the layout inflater
        gui = inflater.inflate(R.layout.activity_house_temp, null);

        //instantiate a new HouseDatabaseHelper and make the writable database
        houseDatabaseHelper =new HouseDatabaseHelper(ctx);
        db = houseDatabaseHelper.getWritableDatabase();
        //db = houseDatabaseHelper.getReadableDatabase();
        //db.execSQL(HouseDatabaseHelper.DROP_TABLE_MESSAGE);
        //db.execSQL(HouseDatabaseHelper.CREATE_TABLE_MESSAGE);

        listview= (ListView)gui.findViewById(R.id.house_temp_listview);
        cursor = getCursor();

        // simpleCursor that make the two column of the database to be one
        adapter = new SimpleCursorAdapter(ctx,
                R.layout.activity_house_displayschedule,
                cursor,
                new String[] { "Time","Temp" },
                new int[] { R.id.house_listviewholder1 ,R.id.house_listviewholder2});

        //adapter changeCursor
        adapter.changeCursor(getCursor());
        //set adapter
        listview.setAdapter(adapter);




        //  textview that for display the time and temp
        inputTime =(TextView)gui.findViewById(R.id.house_time_setting);
        inputTemperature =(TextView)gui.findViewById(R.id.house_temp_setting);

        //listview onclick listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,  final long id) {

                //when click, show the dialog
                dialog=new Dialog(getActivity());
                dialog.setTitle(R.string.house_dialog_title);  //set title
                dialog.setContentView(R.layout.activity_house_add_dialog); //set content view
                TextView txtMessage=(TextView)dialog.findViewById(R.id.house_temp_hold);
                txtMessage.setText(R.string.house_dialog_text);  //set text
                txtMessage.setTextColor(Color.parseColor("#ff2222")); //set color
                final EditText editText=(EditText)dialog.findViewById(R.id.house_temp);

                editText.setText("ID"+ id);

                Button save=(Button)dialog.findViewById(R.id.house_temp_save);
                Button delete =(Button)dialog.findViewById(R.id.house_temp_delete) ;

                //save button click listener
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tempuratureNewItem =editText.getText().toString();


                        if (!isTablet){ //if phone

                            Intent intent = new Intent();
                            intent.putExtra("_id", id);
                            intent.putExtra("Temp", tempuratureNewItem);
                            getActivity().setResult(3, intent); //set the edit temp result code be 3
                            getActivity().finish();
                        } else
                        {     //if tablet edit temp
                            houseSettingStartActivity.EditTemp(id, tempuratureNewItem);
                            Log.i("temp test",tempuratureNewItem);
//                            HouseSettingTemperatureFragment mf = (HouseSettingTemperatureFragment) getFragmentManager().findFragmentById(R.id.activity_house_temp);
//                            //houseSettingStartActivity.removetempFragment();
//                             //remove the sw600dp frage
//                            getFragmentManager().beginTransaction().remove(mf).commit();


                        }


                        adapter.notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });

                dialog.show();
                //delete button click listener
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //if phone
                        if (!isTablet){
                            Intent intent = new Intent();
                            intent.putExtra("_id", id);
                            //set the selet result code be 2
                            getActivity().setResult(2, intent);
                            getActivity().finish();
                        } else
                        {
                            //if tablet
                            houseSettingStartActivity.deleteschedule(id);
                           // houseSettingStartActivity.removetempFragment();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });



        //add button, create a new item to the schedule
        Button add =(Button)gui.findViewById(R.id.house_temp_add);


//add button click listener
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the updated time and temp
                timeNewItem = inputTime.getText().toString();
                tempuratureNewItem = inputTemperature.getText().toString();


//                if((timeNewItem!=""&&timeNewItem!="null")&&(tempuratureNewItem!=""&&tempuratureNewItem!="null")){

                    //if phone
                    if (!isTablet){
                        Intent intent = new Intent();
                        intent.putExtra("Time", timeNewItem);
                        intent.putExtra("Temp", tempuratureNewItem);
                        //set the update time and temp result code be 1
                        getActivity().setResult(1, intent);
                        getActivity().finish();
                    } else  //if tablet
                    {




                        houseSettingStartActivity.updateTemp(id, timeNewItem, tempuratureNewItem);
                        //houseSettingStartActivity.removetempFragment();
                        inputTime.setText(null);
                        inputTemperature.setText(null);

                    }



//                }

//                else{
//
//                    getActivity().finish();
//
//                }







            }
        });

        return gui;
    }
   


}//end of the class
