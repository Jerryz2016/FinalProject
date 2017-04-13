package com.jieli.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * CST2335 Final Project-Automobile activity
 * The Class CarSettings, is the main inferface of the Automobile activity, it calls the related
 * other activity to set the Temperature,lights,GPS and radio of the car.
 * Group     3
 * @author Jieli Zhang
 * @version v1.0
 * Date      2017.04.12
 */
public class CarSettings extends AppCompatActivity {

    /** The ctx. */
    Context ctx;

    /**
     * The list view.
     */
    //    String temp;
    private ListView listView;

    /**
     * The car settings.
     */
    private String[] carSettings = {"Temperature", "Radio", "GPS", "Lights"};

    /**
     * The boolean for identify is tablet or not.
     */
    private Boolean isTablet;  // for to check if a phone or tablet

    /** The sp temperature. */
    private SharedPreferences spTemperature;

    /**
     * The tempfile.
     */
    private static String TEMPFILE = "com.jieli.finalproject.cartempsetting";

    /**
     * The temp.
     */
    private static String TEMP = "cartemperatrure";

    /**
     * The headlights.
     */
    private static String HEADLIGHTS = "headlights";

    /**
     * The dimmable.
     */
    private static String DIMMABLE = "dimmable";

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
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
                        //                       Toast toast0 = Toast.makeText(ctx, "Setting CarTemperature", Toast.LENGTH_SHORT);
                        //                        toast0.show();
                        //========= To get saved/ retrieve data from TEMPERATURE==============
                        spTemperature = getSharedPreferences(TEMPFILE, Context.MODE_PRIVATE);
                        String temp = spTemperature.getString(TEMP, "25");
                        Bundle bundle = new Bundle();
                        bundle.putString("carTemperature", temp);

                        if (isTablet) {
                            bundle.putBoolean("isTablet", true);
                            CarTemperatureFragment tfrag = new CarTemperatureFragment();
                            tfrag.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.car_framelayout, tfrag).commit();

                        } else {
                            Intent temIntent = new Intent(ctx, CarTemperature.class);
                            temIntent.putExtras(bundle);
                            startActivityForResult(temIntent, 10);
                        }
                        break;
                    case 1: //setting radio
                        //                       Log.d("listview", "radio");
                        // to start radioActivity() and retrieve data from DB and display; allow to add, remove and update stations
                        // and store all the changes to DB

                        //                        Toast toast1 = Toast.makeText(ctx, "Setting radio,Click the item which you want to set", Toast.LENGTH_SHORT);
                        //                        toast1.show();
                        Intent intentRadio = new Intent(ctx, CarRadio.class);
                        startActivity(intentRadio);

                        break;
                    case 2: //setting GPS
                        //                        Log.d("listview", "GPS");
                        //                        Toast toast2 = Toast.makeText(ctx, "Start Google Map", Toast.LENGTH_SHORT);
                        //                        toast2.show();
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=1385 Woodroffe Avenue,Ottawa");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                    case 3:     //setting Lights
                        Log.d("listview", "lights");
                        // to start lightsActivity() and retrieve data from DB and display; and then allow to reset
                        // and store all the changes to DB

                        Snackbar.make(view, "Setting Lights", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        spTemperature = getSharedPreferences(TEMPFILE, Context.MODE_PRIVATE);
                        String headlightSet = spTemperature.getString(HEADLIGHTS, "0");
                        String dimlightSet = spTemperature.getString(DIMMABLE, "0");
                        Log.i("onItemClick light sp: ", headlightSet + dimlightSet);
                        Bundle bundleLight = new Bundle();
                        bundleLight.putString(HEADLIGHTS, headlightSet);
                        bundleLight.putString(DIMMABLE, dimlightSet);

                        Intent intentLight = new Intent(ctx, CarLights.class);
                        intentLight.putExtras(bundleLight);
                        startActivityForResult(intentLight, 13);

                        break;
                }
            }
        });
        // listview for all the settings of the car
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

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data the data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10:                // temperature
                    Bundle extras = data.getExtras();
                    String temp = (String) extras.get("carTemperature");
                    //  store in db
                    saveTempPreference(TEMP, temp);
                    break;
                case 13:     // lights
                    Bundle lightset = data.getExtras();
                    //  store in db
                    saveTempPreference(HEADLIGHTS, lightset.getString(HEADLIGHTS));
                    saveTempPreference(DIMMABLE, lightset.getString(DIMMABLE));
                    break;
            }
        }
    }

    /**
     * Save temp preference.
     *
     * @param key  the key
     * @param temp the temp
     */
    public void saveTempPreference(String key, String temp) {
        //======== To save data to preference ===================
        Log.i("saveTempPreference: ", temp);
        spTemperature.edit().putString(key, temp).commit();

    }

    /**
     * On resume.
     */
    protected void onResume() {
        super.onResume();
        Log.d("CarSettings", "OnResume");
    }

    /**
     * On start.
     */
    protected void onStart() {
        super.onStart();
        Log.d("CarSettings", "OnStart");

    }

    /**
     * On pause.
     */
    protected void onPause() {
        super.onPause();
        Log.e("CarSettingsn", "onPause");
    }

    /**
     * On stop.
     */
    protected void onStop() {
        super.onStop();
        Log.e("CarSettings", "onStop");
	}

	/**
	 * On destory.
	 */
	public void onDestory() {
		super.onDestroy();
	}
}
