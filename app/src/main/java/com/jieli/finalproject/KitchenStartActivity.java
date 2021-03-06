/**
 * File name: 	KitchenStartActivity.java
 * Author:  	Group3 Chao Gu
 * Course: 		CST2335 – Graphical Interface Programming
 * Project: 	Final
 * Date: 		April 14, 2017
 * Professor: 	ERIC TORUNSKI
 * Purpose: 	To create a Smart-Kitchen environment remote control starting interface.
 */
package com.jieli.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The KitchenStartActivity Class is to create a starting entry for user.
 * A panel with list shows the appliances of the kitchen, below there are two buttons which could be used to add or delete an appliance.
 * Click on one item of the panel will bring user a window with detailed information and some setting buttons.
 *
 * @author Group3 Chao Gu
 * @version v1.0.
 */
public class KitchenStartActivity extends AppCompatActivity {
/* ATTRIBUTES	-----------------------------------------------------	*/

    ArrayAdapter<String> adapter1, adapter2;     //----- Adapter1 is to inflate the list of added items into the list view and the prompt alert dialog box for deleting
                                                 //----- Adapter2 is to inflate the list of optional items into the prompt alert dialog box for adding
    ListView button_List;
    Boolean isTablet;
    Button returnButton, addButton, deleteButton;
    FragmentMicrowave fragmentMicrowave;
    FragmentFridge fragmentFridge;
    FragmentKitchenLight fragmentKitchenLight;
    DBAdapter dbAdapter;
    int fridge_Request_Code = 1;
    int light_Request_Code = 2;
    Context context = this;
    final String DEGREE = "\u00b0";
    final String PERCENT = "\u0025";
    AlertDialog.Builder alertDialogToAdd, alertDialogToDel;

    List<String> list1 = new ArrayList<>();     //----- list1 is to record the appliances that have been added into the control panel list
    List<String> list2 = new ArrayList<>();     //----- list2 is to record the appliances that act as options for user

    SharedPreferences pref1, pref2;             //----- pref1 is to receive the data from list1
    //----- pref2 is to receive the data from list2
    SharedPreferences.Editor editor1, editor2;
    Set<String> set1, set2;                     //----- set1 is a helper to insert list1 into prefs1
                                                //----- set2 is a helper to insert list2 into prefs2


    /**
     * To create a visible view for KitchenStartActivity, initialize the attributes and create some methods
     * @param savedInstanceState, Type: Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_start);

        isTablet = (findViewById(R.id.detail) != null);
        dbAdapter = new DBAdapter(context);
        new createDB().execute("");

        button_List = (ListView) findViewById(R.id.list_item);
        addButton = (Button) findViewById(R.id.buttonAdd);
        deleteButton = (Button) findViewById(R.id.buttonDelete);
        returnButton = (Button) findViewById(R.id.button1);

        pref1 = getSharedPreferences("list1", Context.MODE_PRIVATE);
        pref2 = getSharedPreferences("list2", Context.MODE_PRIVATE);
        editor1 = pref1.edit();
        editor2 = pref2.edit();

        set1 = pref1.getStringSet("list1", new HashSet<String>());
        Log.i("set1 start value", "" + set1);
        set2 = pref2.getStringSet("list2", new HashSet<String>());
        Log.i("set2 start value", "" + set2);

        if (set1.isEmpty() && set2.isEmpty()) {  //--- the first time when activity come to on-create, the two lists should be null, so initial the two adapters
            set1.clear();
            set1.add("Microwave");
            set1.add("Fridge");
            set1.add("Main Light");
            list1.addAll(set1);
            adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
            Log.i("set1 value", "" + set1);

            set2.clear();
            set2.add("Fridge (Samsung)");
            set2.add("Freezer (Samsung)");
            set2.add("Main Ceiling Light");
            list2.addAll(set2);
            adapter2 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, list2);
            Log.i("set2 value", "" + set2);
        } else {                                  //--- if it is not the first time, read data from set and initialize adapter
            list1.addAll(set1);
            adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);

            list2.addAll(set2);
            adapter2 = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, list2);
        }


        alertDialogToAdd = new AlertDialog.Builder(this);                   //----- This dialog is to show the appliances that has been added into the control panel list
        alertDialogToAdd.setTitle("Select One appliance to add: ");

        alertDialogToAdd.setAdapter(adapter2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {     //----- when a item is clicked, add it into list1 and delete it from list2 at the meantime,
                adapter1.add(adapter2.getItem(position));                   //----- record them into share preferences files
                adapter1.notifyDataSetChanged();                            //----- refresh the two adapters
                set1.add(adapter2.getItem(position));
                Log.i("set1 value", "" + set1);

                String s = adapter2.getItem(position);
                adapter2.remove(s);
                set2.remove(s);
                Log.i("set2 value", "" + set2);
            }
        });


        alertDialogToDel = new AlertDialog.Builder(this);                  //----- This dialog is to show the appliances that could be deleted,
        alertDialogToDel.setTitle("Select One appliance to delete: ");     //----- they are the same ones in the control panel list

        alertDialogToDel.setAdapter(adapter1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {    //----- when a item is clicked, add it into list2 and delete it from list1 at the meantime,
                adapter2.add(adapter1.getItem(position));                  //----- record them into share preferences files
                set2.add(adapter1.getItem(position));                      //----- refresh the two adapters
                adapter2.notifyDataSetChanged();
                Log.i("set2 value", "" + set2);

                String s = adapter1.getItem(position);
                adapter1.remove(s);
                set1.remove(s);
                adapter1.notifyDataSetChanged();
                Log.i("set1 value", "" + set1);
            }
        });


        button_List.setAdapter(adapter1);                                    //---- add the adapter1 which holds the list of appliances that have been added into the ListView
        button_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //---- when a item is clicked, go to the specific activity or fragment

                if (button_List.getItemAtPosition(position).toString().equals("Microwave")) {
                    if (isTablet) {
                        fragmentMicrowave = new FragmentMicrowave();
                        getFragmentManager().beginTransaction().replace(R.id.detail, fragmentMicrowave).commit();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MicrowaveActivity.class);
                        startActivity(intent);
                    }
                } else if (button_List.getItemAtPosition(position).toString().charAt(0) == 'F') {
                    if (isTablet) {
                        fragmentFridge = new FragmentFridge(KitchenStartActivity.this);
                        Bundle bundle = new Bundle();
                        Cursor c = dbAdapter.getItem(button_List.getItemAtPosition(position).toString());
                        bundle.putInt("position", position);
                        bundle.putString("type", c.getString(c.getColumnIndex("type")));
                        bundle.putString("name", c.getString(c.getColumnIndex("name")));
                        bundle.putString("setting", c.getString(c.getColumnIndex("setting")));
                        fragmentFridge.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.detail, fragmentFridge).commit();
                    } else {
                        Cursor c = dbAdapter.getItem(button_List.getItemAtPosition(position).toString());
                        Intent intent = new Intent(getApplicationContext(), FridgeActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("type", c.getString(c.getColumnIndex("type")));
                        intent.putExtra("name", c.getString(c.getColumnIndex("name")));
                        intent.putExtra("setting", c.getString(c.getColumnIndex("setting")));
                        startActivityForResult(intent, fridge_Request_Code);
                    }
                } else {
                    if (isTablet) {
                        fragmentKitchenLight = new FragmentKitchenLight(KitchenStartActivity.this);
                        Bundle bundle = new Bundle();
                        Cursor c = dbAdapter.getItem(button_List.getItemAtPosition(position).toString());
                        bundle.putInt("position", position);
                        bundle.putString("type", c.getString(c.getColumnIndex("type")));
                        bundle.putString("name", c.getString(c.getColumnIndex("name")));
                        bundle.putString("setting", c.getString(c.getColumnIndex("setting")));
                        fragmentKitchenLight.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.detail, fragmentKitchenLight).commit();
                    } else {
                        Cursor c = dbAdapter.getItem(button_List.getItemAtPosition(position).toString());
                        Intent intent = new Intent(getApplicationContext(), KitchenLightActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("type", c.getString(c.getColumnIndex("type")));
                        intent.putExtra("name", c.getString(c.getColumnIndex("name")));
                        intent.putExtra("setting", c.getString(c.getColumnIndex("setting")));
                        startActivityForResult(intent, light_Request_Code);
                    }
                }

            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {    //----- when the add button is clicked, show user the appliances that could be added
            @Override
            public void onClick(View view) {
                alertDialogToAdd.show();
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() { //----- when the delete button is clicked, show user
                                                                     //----- the appliances (the same ones listed in the ListView) to delete
            @Override
            public void onClick(View view) {
                alertDialogToDel.show();
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() { //----- return to the calling activity
            @Override
            public void onClick(View view) {
                editor1.clear();
                editor2.clear();

                editor1.putStringSet("list1", set1);
                Log.i("set1 value", "" + set1);
                editor1.commit();
                set1.clear();

                editor2.putStringSet("list2", set2);
                Log.i("set2 value", "" + set2);
                editor2.commit();
                set2.clear();

                finish();
            }
        });
    }


    /**
     * To receive the data from relative activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  //---- to take some actions depends on the requestcode
        if (requestCode == fridge_Request_Code) {
            if (resultCode == RESULT_OK) {                                        //---- if the result is from fridge activity, show the
                                                                                  //---- fridge setting info to user, update the specific fridge DB info
                Toast.makeText(this, "Setting to: " + data.getStringExtra("setting") + DEGREE
                               + " Position is: " + data.getIntExtra("position", 0), Toast.LENGTH_SHORT).show();
                dbAdapter.updateItem(data.getStringExtra("name"), data.getStringExtra("setting"));
            }
        } else if (requestCode == light_Request_Code) {                           //---- if the result is from light activity, show the
                                                                                  //---- light seeting info to user, update the specific light DB info
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Setting to: " + data.getStringExtra("setting") + PERCENT
                               + " Position is: " + data.getIntExtra("position", 0), Toast.LENGTH_SHORT).show();
                dbAdapter.updateItem(data.getStringExtra("name"), data.getStringExtra("setting"));
            }
        }
    }

    /**
     * To use AsyncTask to create and initialize the DB
     */
    public class createDB extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String[] args) {

            dbAdapter.open();
            dbAdapter.insertItem("Fridge", "Fridge", "5");
            dbAdapter.insertItem("Light", "Main Light", "-20");
            dbAdapter.insertItem("Fridge", "Fridge (Samsung)", "3");
            dbAdapter.insertItem("Freezer", "Freezer (Samsung)", "-10");
            dbAdapter.insertItem("Light", "Main Ceiling Light", "60");
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    /**
     * To receive the data from fragment and update DB
     * @param name
     * @param setting
     */
    public void updateDB(String name, String setting) {
        dbAdapter.updateItem(name, setting);
    }

}
