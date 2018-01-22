package com.lesgood.siswa.ui.skill;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Skill;
import com.lesgood.siswa.util.Utils;

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
        initPrice(item.getPrice1());
    }

    public void initPrice(int price) {

        double fee = 0;

        fee = price * 0.3;

        int finaltarif = (int) ((price + fee) + 0.5d);
        txtPrice.setText("Mulai dari " + Utils.getRupiah(finaltarif) + " /100 menit");
    }
}

