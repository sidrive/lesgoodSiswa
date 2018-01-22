package com.lesgood.siswa.ui.order_detail;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Pustaka;
import com.lesgood.siswa.data.remote.OrderService;
import java.util.ArrayList;
import java.util.List;


public class PustakaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OrderDetailActivity activity;

    private final List<Pustaka> pustakas;

    public PustakaAdapter(OrderDetailActivity activity,
        OrderService orderService) {
        this.activity = activity;
        this.pustakas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_pustaka, parent, false);
        return new PustakaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((PustakaViewHolder)holder).bind(pustakas.get(position));
        holder.itemView.setOnClickListener(v -> onUserItemClicked(pustakas.get(position)));
    }

    private void onUserItemClicked(Pustaka pustaka) {
        activity.showDetailPustaka(pustaka);
    }

    @Override
    public int getItemCount() {
        return pustakas.size();
    }

    public void onPustakaAdded(Pustaka pustaka) {
        pustakas.add(pustaka);
        notifyDataSetChanged();
        //notifyItemChanged(pustakas.size()-1);
    }

    public void onPustakaChanged(Pustaka order) {
        int index = pustakas.indexOf(order);
        if(index > -1) {
            pustakas.set(index, order);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void clearList() {
        pustakas.clear();
    }
}
