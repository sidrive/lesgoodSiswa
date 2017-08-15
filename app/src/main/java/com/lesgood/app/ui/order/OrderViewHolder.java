package com.lesgood.app.ui.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.lesgood.app.R;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.util.DateFormatter;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.txt_day)
    TextView txtDay;

    @Bind(R.id.txt_month)
    TextView txtMonth;

    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Bind(R.id.txt_status)
    TextView txtStatus;


    @Bind(R.id.txt_price)
    TextView txtPrice;


    private View itemView;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(Order order) {

        txtTitle.setText(order.getTitle());
        String angka = Integer.toString(order.getAmount());
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka));

        txtStatus.setText(order.getStatus());
        txtDay.setText(DateFormatter.getDate(order.getPertemuan().get(0).getStartTime(), "dd"));
        txtMonth.setText(DateFormatter.getDate(order.getPertemuan().get(0).getStartTime(), "MMM"));
    }
}

