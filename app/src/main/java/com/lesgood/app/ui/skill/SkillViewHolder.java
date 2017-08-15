package com.lesgood.app.ui.skill;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.lesgood.app.R;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SkillViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_skill)
    TextView txtSkill;

    @Bind(R.id.txt_level)
    TextView txtLevel;

    @Bind(R.id.txt_price)
    TextView txtPrice;


    private View itemView;

    public SkillViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Skill item) {
        txtSkill.setText(item.getSkill());
        txtLevel.setText(item.getLevel());
        txtPrice.setText(Utils.getRupiah(item.getPrice1())+"/100 menit");
    }
}

