package fr.pokemongeo.gr1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.pokemongeo.gr1.MainActivity;
import fr.pokemongeo.gr1.R;

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_fragment, container, false);

        // Set click listeners for Pokémon selection buttons
        view.findViewById(R.id.buttonPokemon1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).onPokemonSelected("Carapuce", getContext());
            }
        });

        view.findViewById(R.id.buttonPokemon2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).onPokemonSelected("Salamèche", getContext());
            }
        });

        view.findViewById(R.id.buttonPokemon3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).onPokemonSelected("Bulbizarre", getContext());
            }
        });

        return view;
    }
}
