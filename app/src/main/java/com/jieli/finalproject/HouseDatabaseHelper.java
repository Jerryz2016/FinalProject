/* File Name: HouseDatabaseHelper.java
* Author: HaiXia Feng
* Course: CST2335 Graphical Interface Programming
* Assignment: Final Project
* Date: April-7-2017
* Professor: Eric Torunski
* Purpose:
* This class extends SQLiteOpenHelper creating the database schema and the query as a String object
*/

package com.jieli.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * File name: HouseDatabaseHelper.java
 *
 * @author Haixia Feng
 * @version 1
 * @see android.support.v7.app.AppCompatActivity
 * Since: JavaSE 1.8
 * Course: CST2335 Graphical Interface Programming
 * Assignment: Final Project
 * Purpose:
 *  This class extends SQLiteOpenHelper creating the database schema and the query as a String object
 * Last Revision: April. 6, 2017
 */

public class HouseDatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION_NUM = 3;//if you install this program to others increase version_num can create a new database table
    public static final String TABLE_NAME = "Houseitems";
    public static final String DATABASE_NAME = "HouseDatabase.db";
    public static final String KEY_ID = "_id";
    public static final String KEY_DoorSwitch="DoorSwitch";
    public static final String KEY_LightSwitch="LightSwitch";
    public static final String KEY_Time="Time";
    public static final String Key_Temp ="Temp";
    //delete a table in database
    public static final String DROP_TABLE_MESSAGE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + " ;";
    //create a table in database
    public static final String CREATE_TABLE_MESSAGE ="CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_DoorSwitch + " BOOLEAN DEFAULT 0, "
            + KEY_LightSwitch+ " BOOLEAN DEFAULT 0, "
            + KEY_Time + " TEXT, "
            + Key_Temp + " TEXT); ";

    protected HouseDatabaseHelper(Context ctx) {

        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * This method creates the database
     * @param db Type SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) //only called if not yet created
    {
        db.execSQL(HouseDatabaseHelper.CREATE_TABLE_MESSAGE);

    }

    /**
     * This method upgrade the database
     * @param db Type SQLiteDatabase
     * @param oldVersion Type int
     * @param newVersion Type int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        Log.i("HouseDatabasehelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }

    /**
     * This method controls the version of the database
     * @param db Type SQLiteDatabase
     * @param oldVersion Type int
     * @param newVersion Type int
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("HouseDatabasehelper", "Calling onDowngrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }


}//end of the class