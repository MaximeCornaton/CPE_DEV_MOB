package fr.pokemongeo.gr1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pokebase.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créez ici les tables de votre base de données
        db.execSQL("CREATE TABLE Pokemon (id INTEGER PRIMARY KEY, name TEXT, type TEXT, level INTEGER, attack INTEGER, defense INTEGER, hp INTEGER)");
        db.execSQL("CREATE TABLE PokemonCapture (id INTEGER PRIMARY KEY, pokemon_id INTEGER, capture_date TEXT)");
        // Ajoutez d'autres tables au besoin
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettez à jour la structure de la base de données en cas de besoin
        // Vous pouvez ajouter des instructions ALTER TABLE ici
    }
}
