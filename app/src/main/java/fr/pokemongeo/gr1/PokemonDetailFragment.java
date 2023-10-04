package fr.pokemongeo.gr1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import fr.pokemongeo.gr1.databinding.PokemonDetailFragmentBinding;

public class PokemonDetailFragment extends Fragment {
    private Pokemon pokemon;

    public PokemonDetailFragment(Pokemon pokemon) {
        this.pokemon = pokemon;
        pokemon.setHeight(100);
        pokemon.setWeight(50);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PokemonDetailFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.pokemon_detail_fragment, container, false);
        PokemonViewModel viewModel = new PokemonViewModel();
        viewModel.setPokemon(pokemon);
        binding.setPokemonViewModel(viewModel);

        // Trouver le bouton Material
        MaterialButton backButton = binding.getRoot().findViewById(R.id.backButton);

        // Ajouter un listener de clic au bouton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("i", "oui");
            }
        });

        return binding.getRoot();
    }
}
