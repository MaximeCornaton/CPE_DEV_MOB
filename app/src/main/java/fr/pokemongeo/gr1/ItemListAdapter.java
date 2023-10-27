package fr.pokemongeo.gr1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.pokemongeo.gr1.databinding.ItemItemBinding;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private List<Item> itemList;

    public ItemListAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_item, parent, false);
        return new ItemViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.binding.itemImage.setImageDrawable(holder.viewModel.getImage(holder.itemView.getContext(), item.getFront()));
        holder.viewModel.setItem(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemItemBinding binding;
        private ItemViewModel viewModel = new ItemViewModel();
        public ItemViewHolder(ItemItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setItemViewModel(viewModel);
        }
    }
}
