package com.jieli.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jerry on 04-Apr-17.
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RADIO_TABLE);

        Log.i(" " + ACTIVITY_NAME, "Calling onCreate() ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i(" " + ACTIVITY_NAME, "Calling OnUpgade(), oldVersion = " + oldVersion + ", newVersion = " + newVersion);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}