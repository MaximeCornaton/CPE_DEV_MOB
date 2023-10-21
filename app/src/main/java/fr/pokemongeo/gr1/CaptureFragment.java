package fr.pokemongeo.gr1;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import fr.pokemongeo.gr1.databinding.CaptureFragmentBinding;
import fr.pokemongeo.gr1.databinding.PokemonDetailFragmentBinding;

public class CaptureFragment extends Fragment {
    private MapFragment mapFragment;
    private Pokemon pokemon;


    public CaptureFragment(Pokemon pokemon) { this.pokemon = pokemon; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CaptureFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.capture_fragment, container, false);
        View rootView = binding.getRoot();
        mapFragment = (MapFragment) getFragmentManager().findFragmentByTag("capture_fragment");

        PokemonViewModel viewModel = new PokemonViewModel();
        viewModel.setPokemon(pokemon);
        binding.setPokemonViewModel(viewModel);

        // Button to capture the Pokémon
        Button catchButton = rootView.findViewById(R.id.catchButton);
        catchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = Database.getInstance(getContext());
                ContentValues updateValues = new ContentValues();
                updateValues.put("capture", true);
                String whereClause = "name = ?";
                String[] whereArgs = new String[] { pokemon.getName() };
                database.update("Pokemon", updateValues, whereClause, whereArgs);
                // Handle the Pokémon capture logic here
                // You can update the database or perform any other actions
                // For example, you can update the "capture" status of the Pokemon

                // After capturing, you might want to navigate back to the previous fragment or activity
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        MaterialButton backButtonMaterial = rootView.findViewById(R.id.backButton);
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

        return rootView;
    }
}
