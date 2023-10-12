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
        itemList.add(new Item("Potion", "potion", "Restaure 20 PV", 3));
        itemList.add(new Item("Super Potion", "super_potion", "Restaure 50 PV", 1));

        ItemListAdapter adapter = new ItemListAdapter(itemList);

        binding.itemList.setAdapter(adapter);
        binding.itemList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));

        return binding.getRoot();
    }

    private List<Item> getAllItemsFromDatabase() {
        List<Item> itemList = new ArrayList<>();
        Database database = Database.getInstance(getContext());
        String column[] = {"name", "image", "description", "quantity"};
        Cursor cursor = database.query("Items", column, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String image = cursor.getString(1);
                String description = cursor.getString(2);
                int quantity = cursor.getInt(3);
                itemList.add(new Item(name, image, description, quantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }
}
