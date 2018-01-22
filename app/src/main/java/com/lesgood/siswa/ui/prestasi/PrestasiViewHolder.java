package com.lesgood.siswa.ui.prestasi;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Prestasi;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PrestasiViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_name)
    TextView txtTitle;

   /* @Bind(R.id.img_remove)
    ImageView imgRemove;*/

    private View itemView;

    public PrestasiViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Prestasi item) {
        txtTitle.setText(item.getTitle());
    }
}

