package com.jieli.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jie on 30/03/17.
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

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                       + KEY_ITEM    + " text, "
                                                       + KEY_NUMBER  + " text, "
                                                       + KEY_MESSAGE + " text)");

        Log.i("LivingDatabaseHelper","Calling onCreate" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);

        onCreate(db);

        Log.i(ACTIVITY_NAME, "Calling onUpgrade():  oldVer= " + oldVer + "newVer= " + newVer);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        Log.i(ACTIVITY_NAME, "Calling onOpne()");
    }
}
