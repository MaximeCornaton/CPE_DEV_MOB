package fr.pokemongeo.gr1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class CaptureFragment extends Fragment {
    private Pokemon capturedPokemon; // You should set this to the captured Pokemon
    private MapFragment mapFragment;

    public CaptureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.capture_fragment, container, false);
        mapFragment = (MapFragment) getFragmentManager().findFragmentByTag("capture_fragment");

        // Display the captured Pokémon's image
        ImageView pokemonImageView = rootView.findViewById(R.id.pokemonImageView);
        if (capturedPokemon != null) {
            pokemonImageView.setImageResource(capturedPokemon.getFrontResource());
        }

        // Button to capture the Pokémon
        Button catchButton = rootView.findViewById(R.id.catchButton);
        catchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Pokémon capture logic here
                // You can update the database or perform any other actions
                // For example, you can update the "capture" status of the Pokemon

                // After capturing, you might want to navigate back to the previous fragment or activity
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return rootView;
    }
}
