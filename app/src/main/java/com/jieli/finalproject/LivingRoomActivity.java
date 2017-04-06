package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

import static com.jieli.finalproject.LivingDatabaseHelper.KEY_ITEM;
import static com.jieli.finalproject.LivingDatabaseHelper.KEY_MESSAGE;
import static com.jieli.finalproject.LivingDatabaseHelper.KEY_NUMBER;

public class LivingRoomActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LivingRoomActivity";
    ListView livingRoomListView;
    String[] livingRoomItems;
    public Boolean isTablet;

    Switch lamp1Sw;
    SeekBar lamp2Sb;
    ChromaDialog.Builder lamp3Cb;
    SeekBar windowBlind;

    protected ArrayList<Long> ids = new ArrayList<>();
    protected ArrayList<String> itemsStutas = new ArrayList<String>();

    int lastColor;
    Boolean lmp1Value;
    int lmp2Value, wbValue, id0, id1,id2,id3,id4;

    String tVChannelID, tvON ;

    LivingDatabaseHelper dbHelper;
    ContentValues newMessage;
    SQLiteDatabase livingDB = null;
    Cursor cursor;

    SharedPreferences prefs  ;
    SharedPreferences.Editor myEditor ;

    TVFragment tvfrag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        prefs = getSharedPreferences("livingRoomItems", Context.MODE_PRIVATE);
        myEditor = prefs.edit();

        id0=prefs.getInt("lmp1",0);
        id1=prefs.getInt("lmp2",0);
        id2=prefs.getInt("lmp3",0);
        id3=prefs.getInt("tv",0);
        id4=prefs.getInt("blinds",0);

        dbHelper = new LivingDatabaseHelper(this);
        livingDB = dbHelper.getWritableDatabase();

        cursor = livingDB.query(false, LivingDatabaseHelper.TABLE_NAME,
                new String[] {LivingDatabaseHelper.KEY_ID,
                               LivingDatabaseHelper.KEY_ITEM,
                              LivingDatabaseHelper.KEY_NUMBER,
                              LivingDatabaseHelper.KEY_MESSAGE
                              },
                "Message not null", null,null,null,null,null);

        if (cursor !=null){
            int rows = cursor.getCount();
            cursor.moveToFirst();
        }

       // Log.i(ACTIVITY_NAME, "cursor.getCount() =="  + cursor.getCount());

        for(int i = 0; i < cursor.getColumnCount(); i++)
        {
            Log.i(ACTIVITY_NAME, "Column's name =="  + cursor.getColumnName(i));
        }

        newMessage = new ContentValues();


        Resources resource = getResources();
        livingRoomItems = getResources().getStringArray(R.array.livingroom_array);

        livingRoomListView = (ListView) findViewById(R.id.livingRoomListView);
        livingRoomListView.setAdapter(new ArrayAdapter<String>(this, R.layout.living_room_items, livingRoomItems));

        isTablet = (findViewById(R.id.fragmentHolder) != null);

        livingRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Toast.makeText(LivingRoomActivity.this, "Setting " + livingRoomItems[position], Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:  //setting Lamp1

                        Log.i("ListView", "Lamp1");
                        Log.i("lamp1's id0==== ", Integer.toString(id0));
                        Log.i("ListView", "Setting Lamp1....");

                        AlertDialog.Builder builder = new AlertDialog.Builder(LivingRoomActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View v = inflater.inflate(R.layout.lamp1_layout, null);
                        builder.setView(inflater.inflate(R.layout.lamp1_layout, null));
                        builder.setTitle("Setting Lamp1 ...");

                        lamp1Sw = (Switch)v.findViewById(R.id.lmp1_switch1);
                        lamp1Sw.setChecked(prefs.getBoolean("lmp1Value",true));

                        builder.setPositiveButton("On!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked ON button
                                Log.i("ON! ", "On");
                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "Lamp is On!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                lamp1Sw.setChecked(true);
                                lmp1Value = true;
                                myEditor.putBoolean("lmp1Value",lmp1Value);
                                id0++;
                                myEditor.putInt("lmp1",id0);
                                myEditor.commit();

                                newMessage.put(KEY_ITEM,"1");
                                newMessage.put(KEY_NUMBER,Integer.toString(id0));
                                newMessage.put(KEY_MESSAGE, "Lamp1 is ON!"
                                                            +"----------------"
                                                            + prefs.getInt("lmp1",id0));
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        });
                        builder.setNegativeButton("Off!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("OFF! ", "Off");

                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "    Lamp is Off!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                lamp1Sw.setChecked(false);
                                lmp1Value = false;
                                myEditor.putBoolean("lmp1Value",lmp1Value);
                                id0++;
                                myEditor.putInt("lmp1",id0);
                                myEditor.commit();

                                newMessage.put(KEY_ITEM,"1");
                                newMessage.put(KEY_NUMBER,Integer.toString(id0));
                                newMessage.put(KEY_MESSAGE,"Lamp1 is OFF!"
                                                            +"----------------"
                                                            + prefs.getInt("lmp1",id0));
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        });
                        builder.setView(v);
                        builder.create().show();
                        break;

                    case 1:  //setting Lamp2

                        Log.i("ListView", "Lamp2");
                        Log.i("lamp2's id2==== ", Integer.toString(id1));
                        Log.i("ListView", "Setting Lamp2....");

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(LivingRoomActivity.this);
                        LayoutInflater inflater2 = getLayoutInflater();
                        final View v2 = inflater2.inflate(R.layout.lamp2_layout, null);
                        builder2.setView(inflater2.inflate(R.layout.lamp2_layout, null));
                        builder2.setTitle("Setting Lamp2 ...");
                        
                        lamp2Sb =(SeekBar) v2.findViewById(R.id.seekBar_lamp2);

                        lamp2Sb.setProgress(lmp2Value);
                        lamp2Sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                                lmp2Value = i;
                                Log.i("SeekBar======", Integer.toString(lmp2Value));
                                myEditor.putInt("lmp2Value",lmp2Value);
                                id1++;
                                myEditor.putInt("lmp2",id1);
                                myEditor.commit();
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        builder2.setNegativeButton("exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Living Room ", "lamp2 exit!");

                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "Lamp2 has been adjusted brightness!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                newMessage.put(KEY_ITEM,"2");
                                newMessage.put(KEY_NUMBER,Integer.toString(id1));
                                newMessage.put(KEY_MESSAGE,  "Lamp2's brightness is : "+ lmp2Value
                                                                +"----------------"
                                                                + prefs.getInt("lmp2",id1));
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        });

                        lamp2Sb.setProgress(prefs.getInt("lmp2Value", 50));
                        builder2.setView(v2);
                        builder2.create().show();
                        break;

                    case 2:   //setting Lamp3

                        Log.i("ListView", "Lamp3");
                        Log.i("lamp3's id2==== ", Integer.toString(id3));
                        Log.i("ListView", "Setting Lamp3....");

                        lamp3Cb = new ChromaDialog.Builder();
                        lamp3Cb.initialColor(prefs.getInt("lastColor",0))
                                            .colorMode(ColorMode.RGB).onColorSelected(new ColorSelectListener() {
                            @Override
                            public void onColorSelected(@ColorInt int i) {
                                int green = Color.green(i);
                                int red = Color.red(i);
                                int blue = Color.blue(i);
                                lastColor=  i;
                                Log.i("Color of lmp3 ===", "" + lastColor);

                                myEditor.putInt("lastColor",lastColor);
                                id2++;
                                myEditor.putInt("lmp3",id2);
                                myEditor.commit();

                                newMessage.put(KEY_ITEM,"3");
                                newMessage.put(KEY_NUMBER,Integer.toString(id2));
                                newMessage.put(KEY_MESSAGE,  "The color of Lamp3 is : " + lastColor
                                                              +"----------------"
                                                              + prefs.getInt("lmp3",id2));
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        }).create().show( getSupportFragmentManager(), "pick a color");

                        break;

                    case 3:   //setting Television

                        Log.i("ListView", "Television");
                        Log.i("TV's id3==== ", Integer.toString(id1));
                        Log.i("ListView", "Setting tv....");


                        Bundle bundle = new Bundle();
                        bundle.putString("TVChannelID", tVChannelID);
                        bundle.putBoolean("TVStatus", true);


                        if (isTablet) {
                            tvfrag = new TVFragment(LivingRoomActivity.this);
                            tvfrag.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,tvfrag).commit();

                        } else {
                            Intent temIntent = new Intent(LivingRoomActivity.this, Tv.class);
                            temIntent.putExtras(bundle);
                            startActivityForResult(temIntent, 10);
                        }

                        break;

                    case 4:   //setting Smart Window Blinds

                        Log.i("ListView", "Smart Window Blinds");
                        Log.i("Window Blinds' id4==== ", Integer.toString(id4));
                        Log.i("ListView", "Setting tv....");

                        AlertDialog.Builder builder4 = new AlertDialog.Builder(LivingRoomActivity.this);
                        LayoutInflater inflater4 = getLayoutInflater();

                        final View v4 = inflater4.inflate(R.layout.windowblinds_layout, null);
                        builder4.setView(inflater4.inflate(R.layout.windowblinds_layout, null));

                        windowBlind =(SeekBar) v4.findViewById(R.id.seekBar_blinds);

                        windowBlind.setProgress(wbValue);
                        windowBlind.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                // update (lmp2.getRootView());
                                wbValue = i;
                                Log.i("Blinds SeekBar======", Integer.toString(wbValue));

                                myEditor.putInt("wbValue",wbValue);
                                id4++;
                                myEditor.putInt("blinds",id4);
                                myEditor.commit();

                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        builder4.setNegativeButton("exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Living Room ", "Window Blinds exit!");

                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "Window Blinds have been adjusted brightness!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                newMessage.put(KEY_ITEM,"5");
                                newMessage.put(KEY_NUMBER,Integer.toString(id4));
                                newMessage.put(KEY_MESSAGE,  "Smart Window Blinds: "+ wbValue
                                                              + "------------"
                                                              + prefs.getInt("blinds",id4) );
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        });
                        windowBlind.setProgress(prefs.getInt("wbValue", 150));
                        builder4.setView(v4);
                        builder4.create().show();
                        break;
              }
            }
        });

        Button bnt_Exit = (Button)findViewById(R.id.button_living);
        bnt_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Exit Button!");
                Intent exitIntent = new Intent(LivingRoomActivity.this, MainActivity.class);
                startActivity(exitIntent);
            }
        });

        Button bnt_db = (Button)findViewById(R.id.button_living1);
        bnt_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked DataBase Button!");
                Intent dbIntent = new Intent(LivingRoomActivity.this, LivingDatabaseActivity.class);
                startActivity(dbIntent);
            }
        });


    }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case 10:                // tVChannelID
                        Bundle extras = data.getExtras();
                        tVChannelID = (String) extras.get("TVChannelID");
                        // to  store in db
                        Log.i("TVChannelID ====", tVChannelID);
                        newMessage.put(KEY_ITEM,"4");
                        newMessage.put(KEY_NUMBER,Integer.toString(id3));
                        newMessage.put(KEY_MESSAGE,"TV Channel: NO. " + tVChannelID);
                        livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                }
            }
        }
        public void setTVChannel(String tvc){
            tVChannelID = tvc;
            newMessage.put(KEY_ITEM,"4");
            newMessage.put(KEY_NUMBER,Integer.toString(id3));
            newMessage.put(KEY_MESSAGE,"TV Channel: NO. " + tVChannelID);
            livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
        }
    public void removeFragment()
    {
        getSupportFragmentManager().beginTransaction().remove(tvfrag).commit();
    }
}

