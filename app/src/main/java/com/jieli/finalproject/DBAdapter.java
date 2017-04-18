/**
 * File name: 	DBAdapter.java
 * Author:  	Group3 Chao Gu
 * Course: 		CST2335 – Graphical Interface Programming
 * Project: 	Final
 * Date: 		April 14, 2017
 * Professor: 	ERIC TORUNSKI
 * Purpose: 	To create a DB adapter to read/write/update/delete the items stored in the DB.
 */
package com.jieli.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This class is to create a DbAdapter for kitchen sub-application
 * @author Group3 Chao Gu
 * @version v1.0.
 */

public class DBAdapter {
    /* ATTRIBUTES	-----------------------------------------------------	*/
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
                    + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_TYPE + " text not null, "
                    + KEY_NAME + " text not null, "
                    + KEY_SETTING + " text not null"
                    + ");";

    /* CONSTRUCTORS	-----------------------------------------------------	*/
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
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
            onCreate(db);
        }
    }

    /**
     * To open the DB
     * @return DBAdapter
     * @throws SQLException
     */

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /**
     * To close the database
     */

    public void close() {
        if (DBHelper != null) {
            DBHelper.close();
        }
    }


    /**
     * To insert an item into the database
     * @param type
     * @param name
     * @param setting
     * @return long integer
     */
    public long insertItem(String type, String name, String setting) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SETTING, setting);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }


    /**
     * To delete a particular item
     * @param rowId
     * @return boolean
     */
    public boolean deleteItem(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * To retrieve all items
     * @return cursor
     */
    public Cursor getAllItems() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_TYPE, KEY_NAME,
                KEY_SETTING}, null, null, null, null, null);
    }


    /**
     * To retrieve a particular item by rowID
     * @param rowId
     * @return cursor
     * @throws SQLException
     */
   public Cursor getItem(long rowId) throws SQLException {   //---retrieves item by ID---

        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_TYPE, KEY_NAME, KEY_SETTING},
                        KEY_ROWID + "=" + rowId, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * To retrieve a particular item by name
     * @param name
     * @return cursor
     * @throws SQLException
     */
    public Cursor getItem(String name) throws SQLException {  //---retrieves item by name---
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[]{KEY_TYPE, KEY_NAME, KEY_SETTING},
                KEY_NAME + " like ?", new String[]{name}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    /**
     * To update an item by rowID
     * @param rowId
     * @param setting
     * @return boolean
     */
    public boolean updateItem(long rowId, String setting) {   //---update item by ID---
        ContentValues args = new ContentValues();
        //args.put(KEY_TYPE, type);
        //args.put(KEY_NAME, name);
        args.put(KEY_SETTING, setting);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * To update an item by name
     * @param name
     * @param setting
     * @return boolean
     */
    public boolean updateItem(String name, String setting) {  //---update item by name---
        ContentValues args = new ContentValues();
        //args.put(KEY_TYPE, type);
        //args.put(KEY_NAME, name);
        args.put(KEY_SETTING, setting);
        return db.update(DATABASE_TABLE, args, KEY_NAME + " like ?", new String[]{name}) > 0;
    }


    /**
     * To drop DB
     */
    public void drop() {
        db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
    }
}
