package fr.pokemongeo.gr1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Database {
    private static Database instance = null;
    private DBHelper helper;
    private SQLiteDatabase db;
    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
            instance.loadPokemonFromJSON(context);
        }
        return instance;
    }

    private Database(Context context) {
        helper = new DBHelper(context);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        db = helper.getWritableDatabase();
        long rowId = db.insert("Pokemon", null, values);
        db.close();
        return rowId;
    }

    public Cursor query(String table,String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy) {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(table, columns, whereClause, whereArgs, groupBy, having, orderBy);
        return cursor; //PENSER A CLOSE LA BDD DANS LE CODE APPELANT
    }

    private void loadPokemonFromJSON(Context context) {
        // Ouverture du fichier dans assets
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(context.getResources().getAssets().open("pokemons.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String data = "";
        //lecture du fichier. data == null => EOF
        while (data != null) {
            try {
                data = reader.readLine();
                builder.append(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Traitement du fichier
        try {
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = array.getJSONObject(i);
                values.put("ordre", i + 1);
                String name = object.getString("name");
                values.put("name", name);

                String image = object.getString("image");
                double height = object.getDouble("height");
                values.put("height", height);
                double weight = object.getDouble("weight");
                values.put("weight", weight);
                int imageId = context.getResources().getIdentifier(image, "drawable",
                        context.getPackageName());
                values.put("image", imageId);
                String type1String = object.getString("type1");
                values.put("type1", type1String);
                if (object.has("type2")) {
                    String type2String = object.getString("type2");
                    values.put("type2", type2String);
                }
                values.put("capture", false);
                instance.insert("Pokemon", null, values);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
