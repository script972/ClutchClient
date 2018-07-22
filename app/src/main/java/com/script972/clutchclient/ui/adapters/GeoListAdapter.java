package com.script972.clutchclient.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.model.api.Company;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GeoListAdapter extends RecyclerView.Adapter<GeoListAdapter.ViewHolder>{

    /**
     * Company list for display of adapter
     */
    List<Company> items;

    public GeoListAdapter(List<Company> lists) {
        this.items=lists;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView companyTitle;
        TextView companyDescribe;
        ImageView companyImg;
        ViewHolder(View v) {
            super(v);
            companyTitle = v.findViewById(R.id.company_title);
            companyDescribe = v.findViewById(R.id.company_describe);
            companyImg = v.findViewById(R.id.company_img);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_geo_company, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Company company = items.get(position);
        holder.companyTitle.setText(company.getTitle());
        holder.companyDescribe.setText(company.getNotice());
        Picasso.get().load(company.getLogo()).into(holder.companyImg);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }




}
