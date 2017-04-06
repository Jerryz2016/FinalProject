package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    private static final String DATABASE_NAME = "DB";
    private static final String DATABASE_TABLE = "info";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBAdapter";
    private final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    private static final String KEY_ROWID = "_id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_SETTING = "setting";

    private static final String CREATE_TABLE =
            "create table " + DATABASE_TABLE
                    + "("
                    + KEY_ROWID   + " integer primary key autoincrement, "
                    + KEY_TYPE    + " text not null, "
                    + KEY_NAME    + " text not null, "
                    + KEY_SETTING + " text not null"
                    + ");";


    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE );
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        if(DBHelper != null) {
            DBHelper.close();
        }
    }

    //---insert an item into the database---
    public long insertItem(String type, String name, String setting) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SETTING, setting);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular item---
    public boolean deleteItem(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all items---
    public Cursor getAllItems() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_TYPE, KEY_NAME,
                KEY_SETTING}, null, null, null, null, null);
    }

    //---retrieves a particular item---
    public Cursor getItem(long rowId) throws SQLException {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_TYPE, KEY_NAME, KEY_SETTING},
                        KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates an item---
    public boolean updateItem(long rowId, String setting) {
        ContentValues args = new ContentValues();
        //args.put(KEY_TYPE, type);
        //args.put(KEY_NAME, name);
        args.put(KEY_SETTING, setting);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
