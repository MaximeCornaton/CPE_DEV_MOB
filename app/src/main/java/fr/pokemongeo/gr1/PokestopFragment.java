package fr.pokemongeo.gr1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import fr.pokemongeo.gr1.databinding.PokestopFragmentBinding;

public class PokestopFragment extends Fragment {
    private PokestopFragmentBinding binding;
    public PokestopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.pokestop_fragment, container, false);
        View rootView = binding.getRoot();

        // Button to capture the Pokémon
        /*Button catchButton = rootView.findViewById(R.id.catchButton);
        catchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = Database.getInstance(getContext());
                ContentValues updateValues = new ContentValues();
                updateValues.put("capture", true);
                String whereClause = "id = ?";
                String[] whereArgs = new String[] { String.valueOf(pokemon.getId()) };
                database.update("Pokemon", updateValues, whereClause, whereArgs);

                // After capturing, you might want to navigate back to the previous fragment or activity
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });*/

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
}