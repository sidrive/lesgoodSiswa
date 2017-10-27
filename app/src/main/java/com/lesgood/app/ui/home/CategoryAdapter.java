package com.lesgood.app.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lesgood.app.R;
import com.lesgood.app.data.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agus on 3/6/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    HomeFragment fragment;
    private List<Category> items = new ArrayList<>();

    public CategoryAdapter(HomeFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((CategoryViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserItemClicked(items.get(position));
            }
        });
    }

    private void onUserItemClicked(Category item) {
        fragment.showItemClicked(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onItemAdded(Category item) {
        items.add(item);
        notifyItemChanged(items.size()-1);
    }

    public void onItemChanged(Category item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Category item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.remove(index);
            notifyItemRemoved(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void clearList() {
        items.clear();
    }
}

