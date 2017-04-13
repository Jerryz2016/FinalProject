package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;

import me.priyesh.chroma.ChromaDialog;

import static com.jieli.finalproject.LivingDatabaseHelper.KEY_ITEM;
import static com.jieli.finalproject.LivingDatabaseHelper.KEY_MESSAGE;
import static com.jieli.finalproject.LivingDatabaseHelper.KEY_NUMBER;
import static com.jieli.finalproject.LivingItemsDbHelper.SETCOL;

/**
 * The Class LivingRoomActivity extends AppCompatActivity. It is used to
 * be a Living room smart environment remote interface. There is a
 * ListView which lists several items that you would find in the living romm.
 * You can add, delete and set any of the the items which are listed in the ListView.
 * These items include Lamp1, Lamp2, Lam3, TV and Smart Window Blinds.
 *
 * @author   Jie Wang
 * @version v1.3.  Date: Apr 12, 2017
 */
public class LivingRoomActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LivingRoomActivity";
    /**   ListView objects used to hold the Items in the Living Room	*/
    ListView livingRoomListView;

    /**  the Items in the Living Room: Switch of lamp1Sw for Lamp1,
     *                                 SeekBar of lamp2Sb for Lamp2,
     *                                 ChromaDialog.Builder lamp3Cb for Lamp3
     *                                 SeekBar of windowBlind for Smart Window Blinds
     *                                 */
    Switch lamp1Sw;
    SeekBar lamp2Sb;
    ChromaDialog.Builder lamp3Cb;
    SeekBar windowBlind;

    /**   These variables are used to record the status of each items in the living room.
     * Boolean of lmp1Value is for Lamp1; int of lastColor is for Lamp3;
     * int of lmp2Value is for Lamp2; int wbValue is for smart window blinds;
     * String tVChannelID is for TV
     * */
    int lastColor;
    Boolean lmp1Value;
    int lmp2Value, wbValue;
    String tVChannelID;

    /**   counters for items: 	*/
    int id0, id1,id2,id3,id4;

    /**   define SharedPreference object which is used to record each item's status 	*/
    SharedPreferences prefs  ;
    SharedPreferences.Editor myEditor ;

    /**   adapter, database, SQLiteOpenHelper,SQLiteDatabase
     *    ContentValues,  Cursor for the ListView of livingRoomListView	*/
    private SimpleCursorAdapter adapter;
    LivingItemsDbHelper dbItemHelper;
    private String[] from;
    private int[] to;
    ContentValues item;
    Cursor cursor2;
    SQLiteDatabase db;

    /**   used to record the clicked Item in the ListView and ID in the database 	*/
    int clickedID;
    int clickedItem;

    /**   database, SQLiteOpenHelper,SQLiteDatabase
     *    ContentValues,  Cursor are used to record the status of each items. 	*/
    LivingDatabaseHelper dbHelper;
    SQLiteDatabase livingDB = null;
    ContentValues newMessage;
    Cursor cursor1;

    /**   to judge it is a Tablet or phone	*/
    public Boolean isTablet;
    /**   define the Fragment object for TV panel	*/
    TVFragment tvfrag;

    /**
     * callback function by Android.
     * *@param savedInstanceState: Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        // define SharedPreferences object to store the stutas of each item in the living room.
        prefs = getSharedPreferences("livingRoomItems", Context.MODE_PRIVATE);
        myEditor = prefs.edit();

        id0=prefs.getInt("lmp1",0);
        id1=prefs.getInt("lmp2",0);
        id2=prefs.getInt("lmp3",0);
        id3=prefs.getInt("tv",0);
        id4=prefs.getInt("blinds",0);

        // database for
        dbHelper = new LivingDatabaseHelper(this);
        livingDB = dbHelper.getWritableDatabase();

        dbItemHelper = new LivingItemsDbHelper(this);
        db = dbItemHelper.getWritableDatabase();

        newMessage = new ContentValues();
        item = new ContentValues();

        Button bnt_lamp1 = (Button) findViewById(R.id.lamp1);
        bnt_lamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.put(SETCOL,"lamp1");
                db.insert(LivingItemsDbHelper.TABLE_NAME, null, item);
                cursor2 = getCursor();
                adapter.changeCursor(cursor2);
            }
        });

        Button bnt_lamp2 = (Button) findViewById(R.id.lamp2);
        bnt_lamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.put(SETCOL,"lamp2");
                db.insert(LivingItemsDbHelper.TABLE_NAME, null, item);
                cursor2 = getCursor();
                adapter.changeCursor(cursor2);
            }
        });

        Button bnt_lamp3 = (Button) findViewById(R.id.lamp3);
        bnt_lamp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.put(SETCOL,"lamp3");
                db.insert(LivingItemsDbHelper.TABLE_NAME, null, item);
                cursor2 = getCursor();
                adapter.changeCursor(cursor2);
            }
        });

        Button bnt_tv = (Button) findViewById(R.id.tv);
        bnt_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.put(SETCOL,"tv");
                db.insert(LivingItemsDbHelper.TABLE_NAME, null, item);
                cursor2 = getCursor();
                adapter.changeCursor(cursor2);
            }
        });

        Button bnt_blinds = (Button) findViewById(R.id.blinds);
        bnt_blinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.put(SETCOL,"window blinds");
                db.insert(LivingItemsDbHelper.TABLE_NAME, null, item);
                cursor2 = getCursor();
                adapter.changeCursor(cursor2);
            }
        });

        cursor1 = livingDB.query(false, LivingDatabaseHelper.TABLE_NAME,
                new String[] {LivingDatabaseHelper.KEY_ID,
                        LivingDatabaseHelper.KEY_ITEM,
                        LivingDatabaseHelper.KEY_NUMBER,
                        LivingDatabaseHelper.KEY_MESSAGE
                },
                "Message not null", null,null,null,null,null);

        cursor2 = db.query(false, LivingItemsDbHelper.TABLE_NAME,
                new String[]{LivingItemsDbHelper.KEY_ID,
                        SETCOL},
                null, null, null, null, null, null);

        if (cursor1 !=null){
            int rows = cursor1.getCount();
            cursor1.moveToFirst();
        }
        // Log.i(ACTIVITY_NAME, "cursor.getCount() =="  + cursor.getCount());

        for(int i = 0; i < cursor2.getColumnCount(); i++)
        {
            Log.i(ACTIVITY_NAME, "Column's name =="  + cursor2.getColumnName(i));
        }
        //Resources resource = getResources();
        //livingRoomItems = getResources().getStringArray(R.array.livingroom_array);
        if (cursor2 != null) {
            from = new String[]{SETCOL};
            to = new int[]{R.id.items_name };
            adapter = new SimpleCursorAdapter(this, R.layout.living_room_items, cursor2, from, to, 0);
        }

        livingRoomListView = (ListView) findViewById(R.id.livingRoomListView);
        //livingRoomListView.setAdapter(new ArrayAdapter<String>(this, R.layout.living_room_items, livingRoomItems));
        livingRoomListView.setAdapter(adapter);

        isTablet = (findViewById(R.id.fragmentHolder) != null);

        livingRoomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                cursor2.moveToPosition(position);
                // Get the item's ID  from this row in the database.
                clickedID = cursor2.getInt(cursor2.getColumnIndex(dbItemHelper.KEY_ID));

                String clickedItem = cursor2.getString(cursor2.getColumnIndex(SETCOL));

                switch (clickedItem) {
                    case "lamp1"://setting lamp1
                        Log.i("lamp1's id0==== ", Integer.toString(id0));
                        Log.i("ListView", "Setting Lamp1....");
                      // create a AlertDialog.Builder for lamp1
                        AlertDialog.Builder builder = new AlertDialog.Builder(LivingRoomActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                        final View v = inflater.inflate(R.layout.lamp1_layout, null);
                        builder.setView(inflater.inflate(R.layout.lamp1_layout, null));
                        builder.setTitle("Setting Lamp1 ...");

                        lamp1Sw = (Switch)v.findViewById(R.id.lmp1_switch1);
                        lamp1Sw.setChecked(prefs.getBoolean("lmp1Value"+Integer.toString(clickedID),true));

                        builder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked ON button
                                Log.i("Ok! ", "Ok");
                                if (lamp1Sw.isChecked() ==true ){
                                    lamp1Sw.setChecked(true);
                                    lmp1Value = true;

                                    myEditor.putBoolean("lmp1Value"+Integer.toString(clickedID),lmp1Value);
                                    id0++;
                                    myEditor.putInt("lmp1",id0);
                                    myEditor.commit();
                                    // to  store in livingDB
                                    newMessage.put(KEY_ITEM,"1");
                                    newMessage.put(KEY_NUMBER,Integer.toString(id0));
                                    newMessage.put(KEY_MESSAGE, "Lamp1 is ON!"
                                            +"----------------"
                                            + prefs.getInt("lmp1",id0));
                                    livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                                    FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                    Snackbar.make(fab, "Lamp is On!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                }else {
                                    lamp1Sw.setChecked(false);
                                    lmp1Value = false;
                                    // save lmp1 by using SharedPreference
                                    myEditor.putBoolean("lmp1Value"+Integer.toString(clickedID),lmp1Value);
                                    id0++;
                                    myEditor.putInt("lmp1",id0);
                                    myEditor.commit();
                                    // to  store in livingDB
                                    newMessage.put(KEY_ITEM,"1");
                                    newMessage.put(KEY_NUMBER,Integer.toString(id0));
                                    newMessage.put(KEY_MESSAGE,"Lamp1 is OFF!"
                                            +"----------------"
                                            + prefs.getInt("lmp1",id0));
                                    livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                                }

                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "    Lamp is Off!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                        builder.setNegativeButton("DELETE!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("delete! ", "delete");
                                db.delete(dbItemHelper.TABLE_NAME, "_id=?", new String[]{Integer.toString(clickedID)});
                                cursor2 = db.query(false, LivingItemsDbHelper.TABLE_NAME,
                                        new String[]{LivingItemsDbHelper.KEY_ID,
                                                SETCOL},
                                        null, null, null, null, null, null);
                                adapter.changeCursor(cursor2);
                            }
                        });
                        builder.setView(v);
                        builder.create().show();
                        break;

                    case "lamp2"://setting lamp2
                        Log.i("lamp2's id1==== ", Integer.toString(id1));
                        Log.i("ListView", "Setting Lamp2....");

                        // create a AlertDialog.Builder for lamp2
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
                                myEditor.putInt("lmp2Value"+Integer.toString(clickedID),lmp2Value);
                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {}
                        });
                        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Living Room ", "lamp2 exit!");
                               // create a Snackbar
                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "Lamp2 has been adjusted brightness!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                // save lmp2 by using SharedPreference
                                id1++;//counter for lamp2
                                myEditor.putInt("lmp2",id1);
                                myEditor.commit();
                                // to  store in livingDB
                                newMessage.put(KEY_ITEM,"2");
                                newMessage.put(KEY_NUMBER,Integer.toString(id1));
                                newMessage.put(KEY_MESSAGE,  "Lamp2's brightness is : "+ lmp2Value
                                        +"----------------"
                                        + prefs.getInt("lmp2",id1));
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        });
                        // delete lamp1
                        builder2.setNegativeButton("DELETE!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("delete! ", "delete");
                                db.delete(dbItemHelper.TABLE_NAME, "_id=?", new String[]{Integer.toString(clickedID)});
                                cursor2 = db.query(false, LivingItemsDbHelper.TABLE_NAME,
                                        new String[]{LivingItemsDbHelper.KEY_ID,
                                                SETCOL},
                                        null, null, null, null, null, null);
                                adapter.changeCursor(cursor2);
                            }
                        });

                        lamp2Sb.setProgress(prefs.getInt("lmp2Value"+Integer.toString(clickedID), 50));
                        builder2.setView(v2);
                        builder2.create().show();
                        break;

                    case "window blinds":   //setting Smart Window Blinds

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
                               // save blinds by using SharedPreference
                                myEditor.putInt("wbValue"+Integer.toString(clickedID),wbValue);

                            }
                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }
                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        builder4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Living Room ", "Window Blinds exit!");

                                FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
                                Snackbar.make(fab, "Window Blinds have been adjusted brightness!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                               // save blinds by using SharedPreference
                                id4++; //counter for window blinds
                                myEditor.putInt("blinds",id4);
                                myEditor.commit();
                                // to  store in livingDB
                                newMessage.put(KEY_ITEM,"5");
                                newMessage.put(KEY_NUMBER,Integer.toString(id4));
                                newMessage.put(KEY_MESSAGE,  "Smart Window Blinds: "+ wbValue
                                        + "------------"
                                        + prefs.getInt("blinds",id4) );
                                livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                            }
                        });
                        // delete lamp2
                        builder4.setNegativeButton("DELETE!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("delete! ", "delete");
                                db.delete(dbItemHelper.TABLE_NAME, "_id=?", new String[]{Integer.toString(clickedID)});
                                cursor2 = db.query(false, LivingItemsDbHelper.TABLE_NAME,
                                        new String[]{LivingItemsDbHelper.KEY_ID,
                                                SETCOL},
                                        null, null, null, null, null, null);
                                adapter.changeCursor(cursor2);
                            }
                        });
                        windowBlind.setProgress(prefs.getInt("wbValue"+Integer.toString(clickedID), 150));
                        builder4.setView(v4);
                        builder4.create().show();
                        break;

                    case "lamp3"://setting lamp3
                        Log.i("ListView", "Lamp3");
                        Log.i("Lamp3's id2==== ", Integer.toString(id2));

                        // create a bundle to save the clicked Item and ID
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", clickedID);//clickedID is the database ID of selected item
                        bundle.putString("lamp3", clickedItem); //clickedItem is selected Item

                        Intent intent = new Intent(LivingRoomActivity.this, Lamp3.class);
                        intent.putExtras(bundle); //pass the clicked item Lamp3 to next activity
                        startActivityForResult(intent, 11); //go to Lamp3 activity to set or delete this lamp3
                        break;

                    case "tv":   //setting Television
                        Log.i("ListView", "Television");
                        Log.i("TV's id3==== ", Integer.toString(id3));

                        // create a bundle to save the clicked Item and ID
                        Bundle bundleTV = new Bundle();
                        bundleTV.putInt("id", clickedID);//id is the database ID of selected item
                        bundleTV.putString("tv", clickedItem);

                        bundleTV.putString("TVChannelID", tVChannelID);
                        bundleTV.putBoolean("TVStatus", true);

                        if (isTablet) {
                            tvfrag = new TVFragment(LivingRoomActivity.this);
                            tvfrag.setArguments(bundleTV);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,tvfrag).commit();

                        } else {
                            Intent tvIntent = new Intent(LivingRoomActivity.this, Tv.class);
                            tvIntent.putExtras(bundleTV);
                            startActivityForResult(tvIntent, 10);
                        }
                        break;
                }
            }
        });
        // Button bnt_Exit is used to exit living room and back to the main window
        Button bnt_Exit = (Button)findViewById(R.id.button_living);
        bnt_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Exit Button!");
                Intent exitIntent = new Intent(LivingRoomActivity.this, MainActivity.class);
                startActivity(exitIntent);
            }
        });
        // Button bnt_db is used to open the database window
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
    /**
     * callback: get a result from startActivityForResult()
     * *@param requestCode: int, resultCode,int, data: Intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10:                // delete the item TV
                    if (data.getBooleanExtra("delete", false)) {
                        int id = data.getIntExtra("id", -1);
                        db.delete(dbItemHelper.TABLE_NAME, "_id=?", new String[]{Integer.toString(id)});
                        cursor2 = getCursor();
                        adapter.changeCursor(cursor2);
                    }else {
                         Bundle extras = data.getExtras();
                         tVChannelID = (String) extras.get("TVChannelID");

                        // save tVChannelID by using SharedPreference
                         Log.i("TVChannelID ====", tVChannelID);
                         id3++; //counter for tv
                         myEditor.putInt("tv",id3);
                         myEditor.commit();

                        // to  store in livingDB
                         newMessage.put(KEY_ITEM,"4");
                         newMessage.put(KEY_NUMBER,Integer.toString(id3));
                        newMessage.put(KEY_MESSAGE,"TV Channel:  " + tVChannelID
                                + "------------"
                                + prefs.getInt("tv",id3));
                         livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);
                    }
                    break;
                case 11:               // window blinds
                     if (data.getBooleanExtra("delete", false)) {
                         int id = data.getIntExtra("id", -1);
                         db.delete(dbItemHelper.TABLE_NAME, "_id=?", new String[]{Integer.toString(id)});
                         cursor2 = getCursor();
                         adapter.changeCursor(cursor2);
                     }
                 break;
            }
        }
    }
    /**
     * method to get database's cursor
     * * @return database cursor: Cursor
     */
    protected Cursor getCursor(){
        return db.query(false, LivingItemsDbHelper.TABLE_NAME,
                new String[]{LivingItemsDbHelper.KEY_ID,
                        SETCOL},
                null, null, null, null, null, null);
    }
    /**
     * method to set up TV channel and save to the database in TVfragment
      * *@param tvc: String, TV's channel
     */
    public void setTVChannel(String tvc){
        tVChannelID = tvc;
        id3++; //counter for tv
        myEditor.putInt("tv",id3);
        myEditor.commit();

// to  store in livingDB
        newMessage.put(KEY_ITEM,"4");
        newMessage.put(KEY_NUMBER,Integer.toString(id3));
        newMessage.put(KEY_MESSAGE,"TV Channel:  " + tVChannelID
                + "------------"
                + prefs.getInt("tv",id3));
        livingDB.insert(LivingDatabaseHelper.TABLE_NAME, null, newMessage);

    }
    /**
     * method to detele TV fragment
     *  @param clickedItem: int, clicked Item in the ListView
     */
    public void delTV(int clickedItem){
        this.clickedItem = clickedItem;
        db.delete(dbItemHelper.TABLE_NAME, "_id=?", new String[]{Integer.toString(clickedItem)});
        cursor2 = getCursor();
        adapter.changeCursor(cursor2);
    }
    /**
     * method to remove the fragment
     */
    public void removeFragment()
    {
        getSupportFragmentManager().beginTransaction().remove(tvfrag).commit();
    }
}

