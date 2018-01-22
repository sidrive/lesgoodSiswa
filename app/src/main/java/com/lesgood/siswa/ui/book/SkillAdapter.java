package com.lesgood.siswa.ui.book;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Skill;

import java.util.ArrayList;
import java.util.List;


public class SkillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BookActivity activity;

    private final List<Skill> items = new ArrayList<>();

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    public SkillAdapter(BookActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_skill_selectable, parent, false);
        return new SkillViewHolder(itemView, lastChecked, lastCheckedPos);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SkillViewHolder)holder).bind(items.get(position), position);
        holder.itemView.setOnClickListener(v -> {
            RadioButton cb = ((SkillViewHolder) holder).radioButton;
            int clickedPos = ((Integer)cb.getTag()).intValue();

            if (cb.isChecked()) cb.setChecked(false);
            else cb.setChecked(true);

            if(cb.isChecked())
            {

                if(lastChecked != null)
                {
                    lastChecked.setChecked(false);
                    items.get(lastCheckedPos).setSelected(false);
                }

                lastChecked = cb;
                lastCheckedPos = clickedPos;
            }
            else

                lastChecked = null;

            items.get(clickedPos).setSelected(cb.isSelected());

            onItemClicked(items.get(position));
        });

    }

    private void onItemClicked(Skill item) {
        activity.selectedItem(item);
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
