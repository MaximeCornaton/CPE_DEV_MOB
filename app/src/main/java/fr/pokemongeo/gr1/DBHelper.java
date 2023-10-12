package fr.pokemongeo.gr1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pokebase.db";
    private static final int DATABASE_VERSION = 5;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Pokemon (id INTEGER PRIMARY KEY, ordre INTEGER, capture BOOLEAN, name TEXT, image TEXT, type1 TEXT, type2 TEXT, height DOUBLE, weight DOUBLE)");
        db.execSQL("CREATE TABLE Items (id INTEGER PRIMARY KEY, name TEXT, image TEXT, description TEXT, quantity INTEGER, item_type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if (oldVersion < 2) {
            // Add the "ordre" column for version 2
            //db.execSQL("ALTER TABLE Pokemon ADD COLUMN ordre INTEGER");
        //}
        db.execSQL("CREATE TABLE Items (id INTEGER PRIMARY KEY, name TEXT, image TEXT, description TEXT, quantity INTEGER, item_type TEXT)");
    }
}
