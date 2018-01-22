package com.lesgood.siswa.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Category;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 3/6/17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.txt_name)
    TextView txtName;

    @Bind(R.id.img_icon)
    ImageView imgIcon;

    private View itemView;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Category item) {
        txtName.setText(item.getName());
        imgIcon.setImageResource(item.getIcon());

    }

}
