package fr.pokemongeo.gr1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        // Générer entre 3 et 6 items aléatoirement
        List<Item> items = generateRandomItems(3, 6);
        ItemListAdapter adapter = new ItemListAdapter(items);
        binding.items.setAdapter(adapter);
        binding.items.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));

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

    private List<Item> generateRandomItems(int minCount, int maxCount) {
        Random random = new Random();
        int itemCount = random.nextInt(maxCount - minCount + 1) + minCount; // Générer un nombre entre minCount et maxCount inclus
        List<Item> items = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            // Générer aléatoirement un type d'item (pokeball ou potion)
            if (random.nextBoolean()) {
                items.add(new Ball("Pokeball", "description", 1, 50));
            } else {
                items.add(new Potion("Potion", "description", 1, 50));
            }
        }

        return items;
    }
}