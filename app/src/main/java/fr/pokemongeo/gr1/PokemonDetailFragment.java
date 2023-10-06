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
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import fr.pokemongeo.gr1.databinding.PokemonDetailFragmentBinding;

public class PokemonDetailFragment extends Fragment {
    private Pokemon pokemon;

    public PokemonDetailFragment(Pokemon pokemon) {
        this.pokemon = pokemon;
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

        MaterialButton backButtonMaterial = binding.getRoot().findViewById(R.id.backButton);

        // Ajouter un listener de clic au bouton
        backButtonMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PokemonDetailFragment", "Bouton back cliqué"); // Ajoutez cette ligne
                // Retourner au fragment précédent
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });


        return binding.getRoot();
    }
}
