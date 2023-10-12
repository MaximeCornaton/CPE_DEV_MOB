package fr.pokemongeo.gr1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import fr.pokemongeo.gr1.databinding.InventoryFragmentBinding;

public class InventoryFragment extends Fragment {

    private InventoryFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(inflater,
                R.layout.inventory_fragment,container,false);

        List<Item> itemList = getAllItemsFromDatabase();
        itemList.add(new Potion("Potion", "potion", "Restaure 20 PV", 3, 20));
        itemList.add(new Potion("Super Potion", "super_potion", "Restaure 50 PV", 1, 50));

        ItemListAdapter adapter = new ItemListAdapter(itemList);

        binding.itemList.setAdapter(adapter);
        binding.itemList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));

        return binding.getRoot();
    }

    private List<Item> getAllItemsFromDatabase() {
        List<Item> itemList = new ArrayList<>();
        Database database = Database.getInstance(getContext());
        String column[] = {"name", "image", "description", "quantity", "item_type"};
        Cursor cursor = database.query("Items", column, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String image = cursor.getString(1);
                String description = cursor.getString(2);
                int quantity = cursor.getInt(3);
                String itemType = cursor.getString(4); // Lire le type d'item depuis la base de données

                // Créez l'instance appropriée en fonction du type d'item
                Item item;
                if ("pokeball".equals(itemType)) {
                    item = new Ball(name, image, description, quantity);
                } else {
                    item = new Potion(name, image, description, quantity);
                }

                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

}
