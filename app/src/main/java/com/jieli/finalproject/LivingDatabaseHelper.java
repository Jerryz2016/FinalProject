package com.jieli.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The Class LivingDatabaseHelper extends SQLiteOpenHelper. It is used to
 * create a database for saving the items in the living room
 * @author   Jie Wang
 * @version v1.3.  Date: Apr 12, 2017
 */

public class LivingDatabaseHelper extends SQLiteOpenHelper {
    public static final String ACTIVITY_NAME="LivingDatabaseHelper";
    public static String DATABASE_NAME = "LivingRoom.db";
    public static int VERSION_NUM = 5;
    public static final String KEY_MESSAGE = "MESSAGE";
    public static final String KEY_ITEM =  "ITEM";
    public static final String KEY_NUMBER =  "NUMBER";
    public static final String KEY_ID = "_id";
    public static final String TABLE_NAME = "TableMessage";

    public LivingDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /** function of onCreate to use to create a table in the database
     * *@param db: SQLiteDatabase
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                       + KEY_ITEM    + " text, "
                                                       + KEY_NUMBER  + " text, "
                                                       + KEY_MESSAGE + " text)");

        Log.i("LivingDatabaseHelper","Calling onCreate" );
    }
    /** function of onUpgrade to use to update database
     * *@param db: SQLiteDatabase
     * *@param oldVersion: int
     * *@param newVersion: int
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);

        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onUpgrade():  oldVer= " + oldVer + "newVer= " + newVer);
    }
    // function of onOen to open database
    @Override
    public void onOpen(SQLiteDatabase db){
        Log.i(ACTIVITY_NAME, "Calling onOpne()");
    }
}
