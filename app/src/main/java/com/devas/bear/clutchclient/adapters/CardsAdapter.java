package com.devas.bear.clutchclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devas.bear.clutchclient.R;
import com.devas.bear.clutchclient.activitys.card.ActivityItemCard;
import com.devas.bear.clutchclient.model.CardModel;

import java.util.List;

/**
 * Created by script972 on 24.08.2017.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.MyViewHolder> {

    private Context mContext;
    private List<CardModel> cardList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            Log.i("checkcard","init ready");
        }
    }


    public CardsAdapter(Context mContext, List<CardModel> cardList) {
        this.mContext = mContext;
        this.cardList = cardList;
        Log.i("checkcard","CardsAdapter finish");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("checkcard","onCreateViewHolder start");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_inflator, parent, false);
        Log.i("checkcard","onCreateViewHolder finish");

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.i("checkcard","onBindViewHolder start");

        CardModel cardModel = cardList.get(position);
        holder.title.setText(cardModel.getName());
        holder.count.setText("Rang "+cardModel.getRang());

        // loading album cover using Glide library
     //!   Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        Log.i("checkcard","onBindViewHolder finish");

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityItemCard.class);
                mContext.startActivity(intent);
            }
        });


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        Log.i("checkcard","showPopupMenu start");

        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.cards_dots_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
        Log.i("checkcard","showPopupMenu finish");

    }



    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_del_card:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
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
        return cardList.size();
    }
}
