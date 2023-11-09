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

import java.util.ArrayList;
import java.util.List;

import fr.pokemongeo.gr1.databinding.CaughtFragmentBinding;


public class CaughtFragment extends Fragment {
    private OnClickOnNoteListener listener;
    private DBHelper dbHelper;

    public void setOnClickOnNoteListener(OnClickOnNoteListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CaughtFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.caught_fragment, container, false);


        // Récupérez la liste des Pokémon capturés depuis la base de données
        List<Pokemon> caughtPokemonList = getAllCapturedPokemonsFromDatabase();

        // Créez un adapter pour la liste des Pokémon capturés
        PokemonListAdapter adapter = new PokemonListAdapter(caughtPokemonList, listener);

        // Définissez l'adapter sur le RecyclerView
        binding.caughtPokemonList.setAdapter(adapter);
        binding.caughtPokemonList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));

        return binding.getRoot();
    }

    /**
     * Récupérez la liste des Pokémon capturés depuis la base de données
     * @return
     */
    private List<Pokemon> getAllCapturedPokemonsFromDatabase() {
        List<Pokemon> pokemonList = new ArrayList<>();
        Database database = Database.getInstance(getContext());
        String[] columns = {"id", "ordre", "name", "capture", "image", "height", "weight", "type1", "type2"};
        String selection = "capture = ?"; // WHERE clause
        String[] selectionArgs = {"1"};    // Value for capture = true

        Cursor cursor = database.query("Pokemon", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int ordre = cursor.getInt(cursor.getColumnIndex("ordre"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int frontResource = cursor.getInt(cursor.getColumnIndex("image"));
                double height = cursor.getDouble(cursor.getColumnIndex("height"));
                double weight = cursor.getDouble(cursor.getColumnIndex("weight"));
                String type1 = cursor.getString(cursor.getColumnIndex("type1"));
                String type2 = cursor.getString(cursor.getColumnIndex("type2"));
                boolean isCapture = cursor.getInt(cursor.getColumnIndex("capture")) == 1;

                POKEMON_TYPE enumType1 = POKEMON_TYPE.valueOf(type1);
                POKEMON_TYPE enumType2 = (type2 != null) ? POKEMON_TYPE.valueOf(type2) : null;
                Pokemon pokemon = new Pokemon(ordre, name, frontResource, enumType1, enumType2, weight, height);
                pokemon.setId(cursor.getInt(cursor.getColumnIndex("id")));
                pokemonList.add(pokemon);
            }
            cursor.close();
        }
        return pokemonList;
    }

}
