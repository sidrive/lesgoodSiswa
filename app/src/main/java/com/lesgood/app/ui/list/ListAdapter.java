package com.lesgood.app.ui.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lesgood.app.R;
import com.lesgood.app.data.model.Category;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.FirebaseImageService;
import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agus on 3/6/17.
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ListActivity activity;
    private List<String> items = new ArrayList<>();
    UserService userService;
    FirebaseImageService firebaseImageService;

    public ListAdapter(ListActivity activity, UserService userService, FirebaseImageService firebaseImageService) {
        this.activity = activity;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_teacher, parent, false);
        return new ListViewHolder(itemView, userService, firebaseImageService);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ListViewHolder)holder).bind(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserItemClicked(((ListViewHolder) holder).user);
            }
        });
    }

    private void onUserItemClicked(Guru item) {
        activity.showItemClicked(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onItemAdded(String item) {
        items.add(item);
        notifyItemChanged(items.size()-1);
    }

    public void onItemChanged(String item) {
        int index = items.indexOf(item);
        if(index > -1) {
            items.set(index, item);
            notifyItemChanged(index);
        } else {
            // TODO : wrong friend
            Log.d("fisache", "onListingNull null");
        }
    }

    public void onItemRemoved(String item) {
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

