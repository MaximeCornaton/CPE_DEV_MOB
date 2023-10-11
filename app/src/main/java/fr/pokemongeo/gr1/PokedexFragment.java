package fr.pokemongeo.gr1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.pokemongeo.gr1.databinding.PokedexFragmentBinding;

public class PokedexFragment extends Fragment {
    private OnClickOnNoteListener listener;
    //private DBHelper dbHelper;
    private PokedexFragmentBinding binding;


    public void setOnClickOnNoteListener(OnClickOnNoteListener listener)
    {
        this.listener = listener;
    }

    public void onEventFunction(Pokemon pokemon) {
        if (listener != null)
            listener.onClickOnNote(pokemon);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.pokedex_fragment,container,false);

        // Charger les Pokémons à partir de la base de données
        List<Pokemon> pokemonList = getAllPokemonsFromDatabase();

        // Création de l'adapter pour la liste
        PokemonListAdapter adapter = new PokemonListAdapter(pokemonList, listener);

        // Définition de l'adapter sur le RecyclerView
        binding.pokemonList.setAdapter(adapter);
        binding.pokemonList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));
        return binding.getRoot();
    }

    private List<Pokemon> getAllPokemonsFromDatabase() {
        List<Pokemon> pokemonList = new ArrayList<>();
        Database database = Database.getInstance(getContext());
        String[] columns = {"ordre", "name", "capture", "image", "height", "weight", "type1", "type2"};
        Cursor cursor = database.query("Pokemon", columns, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int ordre = cursor.getInt(cursor.getColumnIndex("ordre"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                int frontResource = getResources().getIdentifier(image, "drawable", getContext().getPackageName());
                double height = cursor.getDouble(cursor.getColumnIndex("height"));
                double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
                String type1 = cursor.getString(cursor.getColumnIndex("type1"));
                String type2 = cursor.getString(cursor.getColumnIndex("type2"));
                boolean isCapture = cursor.getInt(cursor.getColumnIndex("capture")) == 1;

                POKEMON_TYPE enumType1 = POKEMON_TYPE.valueOf(type1);
                POKEMON_TYPE enumType2 = (type2 != null) ? POKEMON_TYPE.valueOf(type2) : null;
                Pokemon pokemon = new Pokemon(ordre, name, frontResource, enumType1, enumType2, weight, height);
                pokemonList.add(pokemon);
            }
            cursor.close();
        }

        return pokemonList;
    }

}