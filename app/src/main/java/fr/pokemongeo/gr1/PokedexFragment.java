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

import java.util.ArrayList;
import java.util.List;

import fr.pokemongeo.gr1.databinding.PokedexFragmentBinding;

public class PokedexFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PokedexFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.pokedex_fragment,container,false);
        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(new Pokemon());
        pokemonList.add(new Pokemon());

        // Création de l'adapter pour la liste
        PokemonListAdapter adapter = new PokemonListAdapter(pokemonList);

        // Définition de l'adapter sur le RecyclerView
        binding.pokemonList.setAdapter(adapter);
        binding.pokemonList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));
        return binding.getRoot();
    }
}