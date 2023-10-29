package fr.pokemongeo.gr1;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import fr.pokemongeo.gr1.databinding.CaptureFragmentBinding;

public class CaptureFragment extends Fragment {
    private Pokemon pokemon;


    public CaptureFragment(Pokemon pokemon) { this.pokemon = pokemon; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CaptureFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.capture_fragment, container, false);
        View rootView = binding.getRoot();

        PokemonViewModel viewModel = new PokemonViewModel();
        viewModel.setPokemon(pokemon);
        binding.setPokemonViewModel(viewModel);

        // Buttons to capture the Pokemon
        Button pokeballButton = rootView.findViewById(R.id.pokeballButton);
        Button superballButton = rootView.findViewById(R.id.superballButton);
        Button hyperballButton = rootView.findViewById(R.id.hyperballButton);

        pokeballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pokeballQuantity = getBalls("Pokeball");
                if (pokeballQuantity > 0) {
                    catchPokemon("Pokeball", pokeballQuantity);
                }
                else {
                    Toast.makeText(getContext(), "You're out of Pokeball", Toast.LENGTH_SHORT).show();
                }
            }
        });
        superballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int superballQuantity = getBalls("Superball");
                if (superballQuantity > 0) {
                    catchPokemon("Superball", superballQuantity);
                }
                else {
                    Toast.makeText(getContext(), "You're out of Superball", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hyperballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hyperballQuantity = getBalls("Hyperball");
                if (hyperballQuantity > 0) {
                    catchPokemon("Hyperball", hyperballQuantity);
                }
                else {
                    Toast.makeText(getContext(), "You're out of Hyperball", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MaterialButton backButtonMaterial = rootView.findViewById(R.id.backButton);
        // Ajouter un listener de clic au bouton
        backButtonMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retourner au fragment précédent
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return rootView;
    }

    private void catchPokemon(String ballType, int ballQuantity) {
        // Décrémenter la quantité de Pokéballs dans la bdd
        ContentValues updateValues = new ContentValues();
        updateValues.put("quantity", ballQuantity - 1);
        String whereClause = "name = ?";
        String[] whereArgs = new String[]{ballType};
        Database database = Database.getInstance(getContext());
        database.update("Items", updateValues, whereClause, whereArgs);

        // Capturer le Pokémon
        ContentValues pokemonUpdateValues = new ContentValues();
        pokemonUpdateValues.put("capture", true);
        String pokemonWhereClause = "id = ?";
        String[] pokemonWhereArgs = new String[]{String.valueOf(pokemon.getId())};
        database.update("Pokemon", pokemonUpdateValues, pokemonWhereClause, pokemonWhereArgs);

        // Revenez au fragment précédent
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private int getBalls(String ballType) {
        // Vérifie si le joueur a au moins 1 Ball
        Database database = Database.getInstance(getContext());
        Cursor cursor = database.query("Items", new String[]{"quantity"}, "name = ?", new String[]{ballType}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int ballQuantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            cursor.close();

            return ballQuantity;
        } else {
            return -1;
        }
    }
}
