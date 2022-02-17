package com.example.fa_simranjitsingh_c0809865_android;
//Created by Simranjit Singh C0809865 on 16-02-2022

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "map_db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        // create categories table
        db.execSQL(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }



    public long insert(String name,String lat,String lng,String completed) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_NAME, name);
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_LAT, lat);
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_LNG, lng);
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_COMPLETED, completed);

        // insert row
        long id = db.insert(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    @SuppressLint("Range")
    public List<com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel> getAll() {
        List<com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.TABLE_NAME + " ORDER BY " +
                com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel note = new com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel();
                note.setId(cursor.getInt(cursor.getColumnIndex(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_ID)));
                note.setName(cursor.getString(cursor.getColumnIndex(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_NAME)));
                note.setLat(cursor.getString(cursor.getColumnIndex(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_LAT)));
                note.setLng(cursor.getString(cursor.getColumnIndex(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_LNG)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_TIMESTAMP)));
                note.setCompleted(cursor.getString(cursor.getColumnIndex(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_COMPLETED)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public void delete(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.TABLE_NAME, com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public int update(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel note, String name, String lat, String lng, String completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_NAME, name);
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_LAT, lat);
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_LNG, lng);
        values.put(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_COMPLETED, completed);
        // updating row
        return db.update(com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.TABLE_NAME, values, com.example.fa_simranjitsingh_c0809865_android.AddPlaceModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }



}
