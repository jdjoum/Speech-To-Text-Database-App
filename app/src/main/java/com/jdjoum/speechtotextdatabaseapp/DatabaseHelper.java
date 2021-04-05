package com.jdjoum.speechtotextdatabaseapp;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Member Variables
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "SPEECH_TABLE";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        String createTable = "CREATE TABLE " +  TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        // Create the database object
        SQLiteDatabase db = this.getWritableDatabase();
        // Variable to help write to the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        // Variable to check if the item was inserted correctly
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            // Data inserted incorrectly
            return false;
        } else {
            // Data inserted correctly
            return true;
        }
    }

    // Returns all the data from the database
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // Returns the ID that matches the speechText passed in
    public Cursor getItemID(String speechText) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM "+ TABLE_NAME + " WHERE " + COL2 + " = '" + speechText + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // Updates the NAME field
    public void updateName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET "+ COL2 + " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" + " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG,"updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    // Delete a row from the database
    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "+ COL1 + " = '" + id + "' AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG,"deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
