package com.jieli.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class KitchenStartActivity extends AppCompatActivity {
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    String buttonText;
    ArrayAdapter<String> adapter;
    ListView button_List, option_List;
    Boolean isTablet;
    Button returnButton;
    FragmentMicrowave fragmentMicrowave;
    DBAdapter dbAdapter;
    int fridge_Request_Code = 1;
    int light_Request_Code = 2;
    Context context = this;
    final String DEGREE = "\u00b0";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_start);

        list1.add("Microwave");
        list1.add("Fridge");
        list1.add("Main Light");
        list2.add("Fridge (Samsung)");
        list2.add("Freezer (Samsung)");
        list2.add("Main Ceiling Light");
        isTablet = (findViewById(R.id.detail) != null);

/*
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        dbAdapter.insertItem("Fridge", "Regular Fridge", "5" + DEGREE);
        dbAdapter.insertItem("Light", "Main Light", "OFF");
        dbAdapter.insertItem("Fridge", "Samsung Fridge", "3" + DEGREE);
        dbAdapter.insertItem("Freezer", "Samsung Freezer", "-20" + DEGREE);
        dbAdapter.insertItem("Light", "Main Ceiling Light", "ON");
*/
new createDB().execute("");

        button_List = (ListView) findViewById(R.id.list_item);

        button_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (button_List.getItemAtPosition(position).toString().equals("Microwave")) {
                    if (isTablet) {
                        fragmentMicrowave = new FragmentMicrowave();
                        getSupportFragmentManager().beginTransaction().replace(R.id.detail, fragmentMicrowave).commit();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), MicrowaveActivity.class);
                        startActivity(intent);
                    }
                } else if (button_List.getItemAtPosition(position).toString().charAt(0) == 'F') {
                    if (isTablet) {
                        /*
                        Bundle bundle = new Bundle();
                        Cursor c = dbAdapter.getItem(position);

                        bundle.putString("type", c.getString(c.getColumnIndex("type")));
                        bundle.putString("name", c.getString(c.getColumnIndex("name")));
                        bundle.putString("setting", c.getString(c.getColumnIndex("setting")));
                        frag.setArguments(bundle);
                        */
                    } else {
                        Cursor c = dbAdapter.getItem(position);
                        Intent intent = new Intent(getApplicationContext(), FridgeActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("type", c.getString(c.getColumnIndex("type")));
                        intent.putExtra("name", c.getString(c.getColumnIndex("name")));
                        intent.putExtra("setting", c.getString(c.getColumnIndex("setting")));
                        startActivityForResult(intent, fridge_Request_Code);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        option_List = (ListView) findViewById(R.id.list_option);
        option_List.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list2));
        option_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogBox dialogFragment = DialogBox.newInstance("Do you want to add this appliance into Control Panel?");
                dialogFragment.show(getFragmentManager(), "dialog");
                buttonText = option_List.getItemAtPosition(position).toString();

            }
        });
        returnButton = (Button) findViewById(R.id.button1);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void doPositiveClick() {
        //---perform steps when user clicks on OK---
        if (list1.indexOf(buttonText) == -1) {
            list1.add(buttonText);
        }
        adapter.notifyDataSetChanged();
    }

    public void doNegativeClick() {
        //---perform steps when user clicks on Cancel---
        Toast.makeText(getApplicationContext(), "User clicks on Cancel", Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == fridge_Request_Code) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Setting to: " + data.getStringExtra("setting") + " Position is: " + data.getIntExtra("position", 0), Toast.LENGTH_SHORT).show();
                dbAdapter.updateItem(data.getIntExtra("position", 0), data.getStringExtra("setting"));
            }
        }
    }

    public class createDB extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String[] args) {
            dbAdapter = new DBAdapter(context);
            dbAdapter.open();
            dbAdapter.insertItem("Fridge", "Regular Fridge", "5" + DEGREE);
            dbAdapter.insertItem("Light", "Main Light", "OFF");
            dbAdapter.insertItem("Fridge", "Samsung Fridge", "3" + DEGREE);
            dbAdapter.insertItem("Freezer", "Samsung Freezer", "-20" + DEGREE);
            dbAdapter.insertItem("Light", "Main Ceiling Light", "ON");
            return "";
        }
        @Override
        protected void onProgressUpdate(Integer... value) {

        }
        @Override
        protected void onPostExecute(String result) {
            adapter = new ArrayAdapter<String>(KitchenStartActivity.this, android.R.layout.simple_list_item_1, list1);
            button_List.setAdapter(adapter);
        }
    }

}
