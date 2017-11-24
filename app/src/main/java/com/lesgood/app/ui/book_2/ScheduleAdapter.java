package com.lesgood.app.ui.book_2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lesgood.app.R;

import com.lesgood.app.data.model.TimeSchedule;
import java.util.ArrayList;
import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BookActivity activity;

    public final List<TimeSchedule> items = new ArrayList<>();

    public ScheduleAdapter(BookActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ScheduleViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClicked(items.get(position)));

    }

    private void onItemClicked(TimeSchedule time) {
        activity.showDialogTimePicker(time.getStartTime(),time.getEndTime(),time.getDay());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onItemAdded(TimeSchedule item) {
        items.add(item);
        notifyDataSetChanged();
        //activity.updateTotalPertemuan(getItemCount());
    }

    public void onItemChanged(TimeSchedule item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(TimeSchedule item){
        items.remove(item);
        notifyDataSetChanged();
        //activity.updateTotalPertemuan(getItemCount());
    }

    public void onitemremovedindex(int position){
        items.remove(position);
        notifyDataSetChanged();
    }

    public void clearList() {
        items.clear();
    }
}
