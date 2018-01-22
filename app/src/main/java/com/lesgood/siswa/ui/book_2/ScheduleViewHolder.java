package com.lesgood.siswa.ui.book_2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.TimeSchedule;
import com.lesgood.siswa.util.Utils;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tvDay) TextView tvDay;
    @Bind(R.id.tvTime) TextView tvTime;




    private View itemView;

    public ScheduleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }

    public void bind(TimeSchedule schedule) {
        tvTime.setText(Utils.longToString(schedule.getStartTime())+" - "+Utils.longToString(schedule.getEndTime()));
        tvDay.setText(Utils.dayFormated(schedule.getDay()));


    }
}

