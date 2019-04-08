package com.script972.clutchclient.ui.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.script972.clutchclient.databinding.CardItemBinding;
import com.script972.clutchclient.ui.model.CardItem;

import java.util.List;

/**
 * Created by script972 on 24.08.2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.MyViewHolder> {

    private List<CardItem> data;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardItemBinding binding;

        MyViewHolder(CardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind() {
            binding.setItem(data.get(getAdapterPosition()));
            binding.invalidateAll();
        }
    }


    public CardsAdapter(List<CardItem> cardList) {
        this.data = cardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardItemBinding binding = CardItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyViewHolder(binding);

       /* View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(itemView);*/
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.bind();
       /* final CardItem cardModel = data.get(position);
        final String jsonCardItem = new Gson().toJson(cardModel);

        if(cardModel.getTitle()!=null && !cardModel.getTitle().isEmpty()) {
            holder.title.setText(cardModel.getTitle());
        } else if(cardModel.getCompany()!=null && cardModel.getCompany().getTitle()!=null && !cardModel.getCompany().getTitle().isEmpty()){
            holder.title.setText(cardModel.getCompany().getTitle());
        }*/
        // holder.count.setText("Rang "+cardModel.getS());
       /* holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });*/

      /*  Picasso.get()
                .load(cardModel.getFacePhoto())
                .placeholder(R.drawable.cardtemplate)
                .error(R.drawable.cardtemplate)
                .into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityItemCard.class);
                intent.putExtra("cardItem", jsonCardItem);
                mContext.startActivity(intent);
            }
        });*/

    }

    /**
     * Click listener for popup main_toolbar_menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
               /* case R.id.action_del_card:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;*/
             /*   case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;*/
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
