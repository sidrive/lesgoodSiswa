package com.lesgood.app.ui.book;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesgood.app.R;
import com.lesgood.app.data.model.Jadwal;
import com.lesgood.app.data.model.Prestasi;
import com.lesgood.app.ui.prestasi.PrestasiActivity;

import java.util.ArrayList;
import java.util.List;


public class PertemuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BookActivity activity;

    public final List<Jadwal> items = new ArrayList<>();

    public PertemuanAdapter(BookActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pertemuan_text, parent, false);
        return new PertemuanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((PertemuanViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

        ((PertemuanViewHolder) holder).imgRemove.setOnClickListener(
            v -> onItemRemoved(items.get(position)));
    }

    private void onItemClicked(Jadwal item) {
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onItemAdded(Jadwal item) {
        items.add(item);
        notifyDataSetChanged();
        activity.updateTotalPertemuan(getItemCount());
    }

    public void onItemChanged(Jadwal item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Jadwal item){
        items.remove(item);
        notifyDataSetChanged();
        activity.updateTotalPertemuan(getItemCount());
    }

    public void onitemremovedindex(int position){
        items.remove(position);
        notifyDataSetChanged();
    }

    public void clearList() {
        items.clear();
    }
}
