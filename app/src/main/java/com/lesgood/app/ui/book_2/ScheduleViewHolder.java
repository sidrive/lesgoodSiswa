package com.lesgood.app.ui.book_2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.app.R;
import com.lesgood.app.data.model.Jadwal;
import com.lesgood.app.util.DateFormatter;
import java.util.Calendar;
import java.util.Date;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_day) TextView txt_day;
    @Bind(R.id.txt_month) TextView txt_month;
    @Bind(R.id.txt_year) TextView txt_year;



    private View itemView;

    public ScheduleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(String item) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(item));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String d = DateFormatter.getDate(Long.parseLong(item),"dd");
        String m = DateFormatter.getDate(Long.parseLong(item),"MMM");
        String y = DateFormatter.getDate(Long.parseLong(item),"yyyy");
        txt_day.setText(d);
        txt_month.setText(m);
        txt_year.setText(y);
        /*String tanggal = DateFormatter.getDate(item.getStartTime(), "EE dd MMM yyy");
        String star = DateFormatter.getDate(item.getStartTime(), "HH:mm");
        String end = DateFormatter.getDate(item.getEndTime(), "HH:mm");
        txtTitle.setText(tanggal+" ("+star+" - "+end+") ");*/
    }
}

