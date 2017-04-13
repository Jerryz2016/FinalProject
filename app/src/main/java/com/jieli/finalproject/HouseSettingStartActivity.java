package com.jieli.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;

public class HouseSettingStartActivity extends AppCompatActivity {
    Context ctx;
    String timeSchedule, temp ;//holding input time and temperature
    private ListView listView;
    private String[] houseSetting = {"Garage", "House Temperature", "Outside Weather"};
    private Boolean isTablet;  // for to check if a phone or tablet
    OutsideWeatherFragment frag;
    Bundle bun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_housesetting_start);

        listView = (ListView) findViewById(R.id.houseSettingButton_List);


       isTablet = (findViewById(R.id.house_framelayout)!=null);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0: //setting Garage
                       Log.d("listview", "Garage");
                        Toast toast0 = Toast.makeText(ctx, "Setting Garage", Toast.LENGTH_SHORT);
                        toast0.show();
                      Bundle bundle0 = new Bundle();
                        Intent temIntent0 = new Intent(ctx, HouseGarage.class);
                       temIntent0.putExtras(bundle0);
                       startActivityForResult(temIntent0, 10);

//                       if (isTablet) {
//                         bundle0.putBoolean("isTablet", true);
//                           HouseGarageFragment gfrag = new HouseGarageFragment();
//                          gfrag.setArguments(bundle0);
//                          getFragmentManager().beginTransaction().replace(R.id.house_fragholder,gfrag).commit();}
//                        else {
//                          Intent temIntent = new Intent(ctx, HouseGarage.class);
//                          temIntent.putExtras(bundle0);
//                           startActivityForResult(temIntent, 10);}
                        break;

                    case 1: //setting temperature
                       Log.d("listview", "House Temperature");
                       Toast toast1 = Toast.makeText(ctx, "Setting Time and Temperature", Toast.LENGTH_SHORT);
                       toast1.show();
                       Bundle bundle = new Bundle();
                       bundle.putString("Time", timeSchedule);
                      bundle.putString("Temperature", temp);
                      Intent temIntent = new Intent(ctx, HouseTemperature.class);
                      temIntent.putExtras(bundle);
                      startActivityForResult(temIntent, 10);
                        break;

                    case 2: //setting Outside Weather
                      Log.d("listview", "Outside Weather");
                      Toast toast2 = Toast.makeText(ctx, "Start Ottawa Weather", Toast.LENGTH_SHORT);
                      toast2.show();

                        if (isTablet) {
                            frag = new OutsideWeatherFragment(HouseSettingStartActivity.this);
                             bun =new Bundle();
                            frag.setArguments(bun);
                            getSupportFragmentManager().beginTransaction().replace(R.id.house_framelayout, frag).commit();
                        }
                        //step 3 if a phone, transition to empty Activity that has FrameLayout
                        else //isPhone
                        {
                            Intent intnt = new Intent(HouseSettingStartActivity.this, OutsideWeather.class);
                           intnt.putExtra("ID",id);
                            startActivity(intnt); //go to view fragment details
                       }

//                     bundle = new Bundle();
//                    bundle.putString("Temperature", temp);
//
//                        Intent temIntent1 = new Intent(ctx, WeatherForcast.class);
//                        temIntent1.putExtras(bundle);
//                        startActivityForResult(temIntent1, 10);

                    break;

                }
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, houseSetting));
        //step 1, find out if you are on a phone or tablet.

        //exit setting
        Button houseExitButton = (Button) findViewById(R.id.houseSettingButton1);
//        houseExitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });





        Log.d("houseSetting", "OnCreate");
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
        Log.d("HouseSettings", "OnResume");
    }

    protected void onStart() {
        super.onStart();
        Log.d("HouseSettings", "OnStart");
    }

    protected void onPause() {
        super.onPause();
        Log.e("HouseSettings", "onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.e("HouseSettings", "onStop");
    }

    public void onDestory() {
        super.onDestroy();
//              if (cursor != null) cursor.close();
//              if (dbHelper != null)  dbHelper.close();
    }










}
