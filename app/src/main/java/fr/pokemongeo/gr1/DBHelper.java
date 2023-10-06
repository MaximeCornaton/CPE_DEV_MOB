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
    private static final int DATABASE_VERSION = 1;
    boolean isDatabaseCreated = false;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (checkDatabaseExists(context)) {
            isDatabaseCreated = true;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez ici les tables de votre base de données
        db.execSQL("CREATE TABLE Pokemon (id INTEGER PRIMARY KEY, capture BOOLEAN, name TEXT, image TEXT, type1 TEXT, type2 TEXT, height DOUBLE, weight DOUBLE)");
        // Ajoutez d'autres tables au besoin
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettez à jour la structure de la base de données en cas de besoin
        // Vous pouvez ajouter des instructions ALTER TABLE ici
    }

    private boolean checkDatabaseExists(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    public void insertPokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("capture", pokemon.isCapture());
        values.put("name", pokemon.getName());
        values.put("image", pokemon.getFrontResource());
        values.put("type1", pokemon.getType1String());
        values.put("type2", pokemon.getType2String());
        values.put("height", pokemon.getHeight());
        values.put("weight", pokemon.getWeight());
        db.insert("Pokemon", null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Pokemon> getAllCapturedPokemon() {
        List<Pokemon> capturedPokemonList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Pokemon WHERE capture = 1", null);

        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setOrder(cursor.getInt(cursor.getColumnIndex("id")));
                // Remplissez les autres attributs du Pokémon ici
                capturedPokemonList.add(pokemon);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return capturedPokemonList;
    }


}
