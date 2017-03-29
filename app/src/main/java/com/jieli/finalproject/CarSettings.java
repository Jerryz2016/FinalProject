package com.jieli.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CarSettings extends AppCompatActivity {
    Context ctx;
    String temp;
    private ListView listView;
    private String[] carSettings = {"Temperature", "Radio", "GPS", "Lights"};
    private Boolean isTablet;  // for to check if a phone or tablet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_car_settings);

        listView = (ListView) findViewById(R.id.car_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: //setting temperature
                        Log.d("listview", "temperature");
                        Toast toast0 = Toast.makeText(ctx, "Setting Temperature", Toast.LENGTH_SHORT);
                        toast0.show();
                        Bundle bundle = new Bundle();
                        bundle.putString("Temperature", temp);

                        if (isTablet) {
                            bundle.putBoolean("isTablet", true);
                            CarTemperatureFragment tfrag = new CarTemperatureFragment();
                            tfrag.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.car_framelayout, tfrag).commit();
                        } else {
                            Intent temIntent = new Intent(ctx, Temperature.class);
                            temIntent.putExtras(bundle);
                            startActivityForResult(temIntent, 10);
                        }
                        break;
                    case 1: //setting radio
                        Log.d("listview", "radio");
                        Toast toast1 = Toast.makeText(ctx, "Setting radio", Toast.LENGTH_SHORT);
                        toast1.show();
                        break;
                    case 2: //setting GPS
                        Log.d("listview", "GPS");
                        Toast toast2 = Toast.makeText(ctx, "Start Google Map", Toast.LENGTH_SHORT);
                        toast2.show();
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=1385 Woodroffe Avenue,Ottawa");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                    case 3:     //setting Lights
                        Log.d("listview", "temperature");
                        Toast toast3 = Toast.makeText(ctx, "Setting Lights", Toast.LENGTH_SHORT);
                        toast3.show();
                        //                       startActivity(new Intent(ctx, SharedPreferencesActivity.class));
                        break;
                }
            }
        });
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.car_lv_row, carSettings));
        //step 1, find out if you are on a phone or tablet.
        isTablet = (findViewById(R.id.car_framelayout) != null);
        //ext car setting
        Button carExitButton = (Button) findViewById(R.id.car_setting_exit);
        carExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.d("carSettings", "OnCreate");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10:                // temperature
                    Bundle extras = data.getExtras();
                    temp = (String) extras.get("Temperature");
                    // todo store in db

            }
        }
    }

    public void storeSettings() {

    }

    protected void onResume() {
        super.onResume();
        Log.d("CarSettings", "OnResume");
    }

    protected void onStart() {
        super.onStart();
        Log.d("CarSettings", "OnStart");
    }

    protected void onPause() {
        super.onPause();
        Log.e("CarSettingsn", "onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.e("CarSettings", "onStop");
    }

    public void onDestory() {
        super.onDestroy();
        //       if (cursor != null) cursor.close();
        //       if (dbHelper != null)  dbHelper.close();
    }


}
