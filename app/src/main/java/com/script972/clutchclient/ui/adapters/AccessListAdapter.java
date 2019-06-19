package com.script972.clutchclient.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.script972.clutchclient.databinding.ItemAccessListBinding;
import com.script972.clutchclient.ui.model.AccessPersonItem;

import java.util.ArrayList;
import java.util.List;

public class AccessListAdapter extends RecyclerView.Adapter<AccessListAdapter.AccessListViewHolder> {

    private List<AccessPersonItem> data;

    public AccessListAdapter(List<AccessPersonItem> data) {
        this.data = data;
    }

    class AccessListViewHolder extends RecyclerView.ViewHolder {

        private final ItemAccessListBinding binding;

        AccessListViewHolder(@NonNull ItemAccessListBinding bindingView) {
            super(bindingView.getRoot());
            binding = bindingView;
        }

        private void bind() {
            binding.setItem(data.get(getAdapterPosition()));
            binding.invalidateAll();
        }
    }

    @NonNull
    @Override
    public AccessListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAccessListBinding binding = ItemAccessListBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new AccessListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessListViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
