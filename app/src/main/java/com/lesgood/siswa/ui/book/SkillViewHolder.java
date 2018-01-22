package com.lesgood.siswa.ui.book;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
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

    @Bind(R.id.radio)
    RadioButton radioButton;


    private View itemView;
    private RadioButton lastChecked = null;
    private int lastCheckedPos = 0;

    public SkillViewHolder(View itemView, RadioButton lastChecked, int lastCheckedPos) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
        this.lastChecked = lastChecked;
        this.lastCheckedPos = lastCheckedPos;
    }

    public void bind(Skill item, int position) {

        radioButton.setChecked(item.isSelected());
        radioButton.setTag(new Integer(position));

        if(position == 0 && item.isSelected() && radioButton.isChecked())
        {
            lastChecked = radioButton;
            lastCheckedPos = 0;
        }

        txtSkill.setText(item.getSkill());
        txtLevel.setText(item.getLevel());
        txtPrice.setText(Utils.getRupiah(item.getPrice1())+"/100 menit");
    }
}

