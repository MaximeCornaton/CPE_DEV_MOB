package fr.pokemongeo.gr1;

import android.content.ContentValues;
import android.database.Cursor;
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

        String[] ballNames = { "Pokeball", "Superball", "Hyperball" };
        String[] potionNames = { "Potion", "Super Potion", "Hyper Potion" };

        for (int i = 0; i < itemCount; i++) {
            // Générer aléatoirement un type d'item (pokeball ou potion)
            if (random.nextBoolean()) {
                // Sélectionnez un nom de ball au hasard
                String randomBallName = ballNames[random.nextInt(ballNames.length)];
                Ball ball = new Ball(randomBallName, "description", 1, 50);
                items.add(ball);
                updateDatabase(randomBallName);
            } else {
                // Sélectionnez un nom de potion au hasard
                String randomPotionName = potionNames[random.nextInt(potionNames.length)];
                Potion potion = new Potion(randomPotionName, "description", 1, 50);
                items.add(potion);
                updateDatabase(randomPotionName);
            }
        }

        return items;
    }

    private void updateDatabase(String name) {
        Database db = Database.getInstance(getContext());

        ContentValues values = new ContentValues();
        values.put("quantity", getItemQuantity(name) + 1); // Ajoute 1 à la quantité actuelle

        // Spécifiez la condition pour mettre à jour l'item avec le nom donné
        String whereClause = "name = ?";
        String[] whereArgs = { name };

        // Mettez à jour la base de données en utilisant la méthode update
        db.update("Items", values, whereClause, whereArgs);
    }

    // Méthode pour obtenir la quantité actuelle d'un item
    private int getItemQuantity(String name) {
        Database db = Database.getInstance(getContext());
        String[] columns = { "quantity" };
        String whereClause = "name = ?";
        String[] whereArgs = { name };

        // Exécutez une requête pour obtenir la quantité actuelle
        Cursor cursor = db.query("Items", columns, whereClause, whereArgs, null, null, null);

        int quantity = 0;
        if (cursor != null && cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
            cursor.close();
        }

        return quantity;
    }


}