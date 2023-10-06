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

        if (dbHelper == null) {
            dbHelper = new DBHelper(getContext());
        }

        // Récupérez la liste des Pokémon capturés depuis la base de données
        List<Pokemon> caughtPokemonList = dbHelper.getAllCapturedPokemon();

        // Créez un adapter pour la liste des Pokémon capturés
        PokemonListAdapter adapter = new PokemonListAdapter(caughtPokemonList, listener);

        // Définissez l'adapter sur le RecyclerView
        binding.caughtPokemonList.setAdapter(adapter);
        binding.caughtPokemonList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));

        return binding.getRoot();
    }
}
