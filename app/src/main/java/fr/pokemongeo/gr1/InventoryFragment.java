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

import fr.pokemongeo.gr1.databinding.InventoryFragmentBinding;

public class InventoryFragment extends Fragment {

    private InventoryFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(inflater,
                R.layout.inventory_fragment,container,false);

        List<Item> itemList = getAllItemsFromDatabase();

        ItemListAdapter adapter = new ItemListAdapter(itemList);

        binding.itemList.setAdapter(adapter);
        binding.itemList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));

        return binding.getRoot();
    }

    private List<Item> getAllItemsFromDatabase() {
        return null;
    }
}
