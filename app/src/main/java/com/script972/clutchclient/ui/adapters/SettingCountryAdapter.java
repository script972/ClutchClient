package com.script972.clutchclient.ui.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.domain.api.model.LocationPosition;

import java.util.ArrayList;
import java.util.List;

public class SettingCountryAdapter extends RecyclerView.Adapter<SettingCountryAdapter.ViewHolder> /*implements Filterable*/ {

    private List<LocationPosition> items;
    private  ValueFilter valueFilter;

    List<LocationPosition> mStringFilterList;

    public SettingCountryAdapter(List<LocationPosition> countryList) {
        this.items = countryList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView countryTitle;
        ViewHolder(View v) {
            super(v);
            countryTitle = v.findViewById(R.id.value_title);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country_setting, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationPosition item = items.get(position);
        Log.i("recyclerproblem", position+" "+item.getCountry());
        holder.countryTitle.setText(item.getCountry());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Method for filter country
     */
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<LocationPosition> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getCountry().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            items = (List<LocationPosition>) results.values;
            notifyDataSetChanged();
        }

    }
}
