package fr.pokemongeo.gr1;

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
    public void setOnClickOnNoteListener(OnClickOnNoteListener listener)
    {
        this.listener = listener;
    }

    public void onEventFunction(int pokemonId) {
        if (listener != null)
            listener.onClickOnNote(pokemonId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PokedexFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.pokedex_fragment,container,false);
        List<Pokemon> pokemonList = new ArrayList<>();

        // Ouverture du fichier dans assets
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(getResources().getAssets().open("pokemons.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String data = "";
        //lecture du fichier. data == null => EOF
        while(data != null) {
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
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String image = object.getString("image");
                int imageId = getResources().getIdentifier(image,"drawable",
                        binding.getRoot().getContext().getPackageName());
                String type1String = object.getString("type1");
                POKEMON_TYPE type1 = POKEMON_TYPE.valueOf(type1String);
                POKEMON_TYPE type2 = null;
                if (object.has("type2")) {
                    String type2String = object.getString("type2");
                    type2 = POKEMON_TYPE.valueOf(type2String);
                }
                pokemonList.add(new Pokemon(i+1,name,imageId,type1,type2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Création de l'adapter pour la liste
        PokemonListAdapter adapter = new PokemonListAdapter(pokemonList);

        // Définition de l'adapter sur le RecyclerView
        binding.pokemonList.setAdapter(adapter);
        binding.pokemonList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));
        return binding.getRoot();
    }
}