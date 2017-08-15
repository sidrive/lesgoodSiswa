package com.lesgood.app.ui.book;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lesgood.app.R;
import com.lesgood.app.data.model.Jadwal;
import com.lesgood.app.data.model.Prestasi;
import com.lesgood.app.util.DateFormatter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PertemuanViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_date)
    TextView txtTitle;

    @Bind(R.id.img_remove)
    ImageView imgRemove;

    private View itemView;

    public PertemuanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Jadwal item) {

        String tanggal = DateFormatter.getDate(item.getStartTime(), "EE dd MMM yyy");
        String star = DateFormatter.getDate(item.getStartTime(), "HH:mm");
        String end = DateFormatter.getDate(item.getEndTime(), "HH:mm");
        txtTitle.setText(tanggal+" ("+star+" - "+end+") ");
    }
}

