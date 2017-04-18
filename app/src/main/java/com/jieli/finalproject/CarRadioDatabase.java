package com.jieli.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * CST2335 Final Project-Automobile activity
 *
 * The class CarRadioDatabase, is defining the database for storing the radio settings.
 *
 * Group     3
 * @author Jieli Zhang
 * @version v1.0
 * Date      2017.04.12
 */

public class CarRadioDatabase extends SQLiteOpenHelper {

    /**
     * The Constant VERSION_NUM of the DB.
     */
    private static final int VERSION_NUM = 1;

    /**
     * The Constant ACTIVITY_NAME.
     */
    private static final String ACTIVITY_NAME = "CarRadioDB";

    /**
     * The Constant DATABASE_NAME.
     */
    public static final String DATABASE_NAME = "CarRadiodb";

    /**
     * The Constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "RadioSet";

    /** The Constant KEY_ID of the id column. */
    public static final String KEY_ID = "_id";

    /**
     * The Constant RADIOCOL of the radio name column.
     */
    public static final String RADIOCOL = "radio";

    /**
     * The Constant SETCOL of the radio channnel column.
     */
    static final String SETCOL = "channel";

    /**
     * The Constant CREATE_RADIO_TABLE.
     */
    public static final String CREATE_RADIO_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RADIOCOL + " TEXT, " + SETCOL + " DOUBLE )";

    /**
     * Instantiates a new car radio database.
     *
     * @param ctx the ctx of Context
     */
    //constructor
    public CarRadioDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Oncreate() method creates the db.
     *
     * @param db the db of the radio setting database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RADIO_TABLE);

        Log.i(" " + ACTIVITY_NAME, "Calling onCreate() ");

    }

    /**
     * On upgrade if the newVersion is greater than the oldVersion.
     *
     * @param db         the db
     * @param oldVersion the old version
     * @param newVersion the new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i(" " + ACTIVITY_NAME, "Calling OnUpgade(), oldVersion = " + oldVersion + ", newVersion = " + newVersion);
    }

    /**
     * Ondowngrade method, if the newVersion is less than the oldVerion.
     *
     * @param db         the db
     * @param oldVersion the old version
     * @param newVersion the new version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
	}
}
