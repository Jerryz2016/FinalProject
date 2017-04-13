package com.jieli.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Class LivingItemsDbHelper extends SQLiteOpenHelper. It is used to
 * create a database for saving the items in the living room
 * @author   Jie Wang
 * @version v1.3.  Date: Apr 12, 2017
 */
public class LivingItemsDbHelper extends SQLiteOpenHelper {
    private static final int VERSION_NUM = 1;
    private static final String ACTIVITY_NAME = "LivingItemsDatabase";
    public static final String DATABASE_NAME = "LivingItemsDB";
    public static final String TABLE_NAME = "LivingItems";
    public static final String KEY_ID = "_id";

    public static final String SETCOL = "item";
    public static final String CREATE_RADIO_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + SETCOL + " TEXT )";

    //constructor
    public LivingItemsDbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    // function of onCreate to use to create a table in the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RADIO_TABLE);

        Log.i(" " + ACTIVITY_NAME, "Calling onCreate() ");

    }
    /** function of onUpgrade to use to update database
     * *@param db: SQLiteDatabase
     * *@param oldVersion: int
     * *@param newVersion: int
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i(" " + ACTIVITY_NAME, "Calling OnUpgade(), oldVersion = " + oldVersion + ", newVersion = " + newVersion);
    }
    /** function of onUpgrade to downgrade database version
     * * *@param db: SQLiteDatabase
     * *@param oldVersion: int
     * *@param newVersion: int
     * */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}