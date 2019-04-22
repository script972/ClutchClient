package com.script972.clutchclient.ui.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.script972.clutchclient.R;
import com.script972.clutchclient.databinding.CardItemBinding;
import com.script972.clutchclient.ui.model.CardItem;

import java.util.List;

/**
 * Created by script972 on 24.08.2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.MyViewHolder> {

    private List<CardItem> data;

    private Clicker clicker;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardItemBinding binding;

        MyViewHolder(CardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind() {
            binding.setItem(data.get(getAdapterPosition()));
            Uri selectedImage = Uri.parse(data.get(getAdapterPosition()).getPhotoFront());
            binding.thumbnail.setImageURI(selectedImage);

            binding.setListener(clicker);
            binding.invalidateAll();
        }
    }


    public CardsAdapter(List<CardItem> cardList, Clicker clicker) {
        this.data = cardList;
        this.clicker = clicker;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardItemBinding binding = CardItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.bind();

    }

    /**
     * Click listener for popup main_toolbar_menu items
     */
    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
           /* switch (menuItem.getItemId()) {
                case R.id.action_del_card:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }*/
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface Clicker {
        void onItemClick(CardItem cardItem);
    }

}
