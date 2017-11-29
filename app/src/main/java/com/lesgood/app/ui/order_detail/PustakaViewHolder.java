package com.lesgood.app.ui.order_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.app.R;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Pustaka;
import com.lesgood.app.util.DateFormatter;
import java.text.NumberFormat;
import java.util.Locale;

public class PustakaViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tvTitle)
    TextView tvTitle;



    private View itemView;

    public PustakaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Pustaka pustaka) {
        tvTitle.setText(pustaka.getName());
    }


}

