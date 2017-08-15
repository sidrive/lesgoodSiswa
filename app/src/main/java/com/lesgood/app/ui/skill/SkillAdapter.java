package com.lesgood.app.ui.skill;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.lesgood.app.R;
import com.lesgood.app.data.model.Skill;

import java.util.ArrayList;
import java.util.List;


public class SkillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SkillActivity activity;

    private final List<Skill> items = new ArrayList<>();

    public SkillAdapter(SkillActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_skill, parent, false);
        return new SkillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SkillViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(items.get(position));
            }
        });

    }

    private void onItemClicked(Skill item) {
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getMinRate(){
        int rate = 0;
        if (items.size()>0){
            rate = items.get(0).getPrice1();
            for (int i=0;i<items.size();i++){
                int rateNew = items.get(i).getPrice1();
                if (rateNew < rate){
                    rate = rateNew;
                }
            }
        }
        return rate;
    }

    public void onItemAdded(Skill item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void onItemChanged(Skill item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(Skill item){
       items.remove(item);
    }

    public void clearList() {
        items.clear();
    }
}
