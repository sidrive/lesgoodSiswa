package com.lesgood.siswa.ui.reviews;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikun on 20/11/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ReviewsActivity activity;

    private final List<Reviews> items = new ArrayList<>();

    public ReviewsAdapter(ReviewsActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_reviews, parent, false);
        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ReviewsViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

    }

    private void onItemClicked(Reviews item) {
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    /*public int getMinRate(){
        int rate = 0;
        if (items.size()>0){
            rate = items.get(0).getRating();
            for (int i=0;i<items.size();i++){
                int rateNew = items.get(i).getRating();
                if (rateNew < rate){
                    rate = rateNew;
                }
            }
        }
        return rate;
    }*/

    public void onItemAdded(Reviews item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(Reviews item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Reviews item){
        items.remove(item);
    }

    public void clearList() {
        items.clear();
    }
}
