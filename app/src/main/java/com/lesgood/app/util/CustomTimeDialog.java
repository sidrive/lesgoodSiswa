package com.lesgood.app.util;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lesgood.app.R;
import com.lesgood.app.ui.book_2.BookActivity;
import java.util.Calendar;

/**
 * Created by sim-x on 12/7/17.
 */

@SuppressLint("ValidFragment")
public class CustomTimeDialog extends DialogFragment {

  @Bind(R.id.time_picker)
  TimePicker timePicker;

  long startTime;
  long endTime;
  String day;
  BookActivity activity;
  @Bind(R.id.tv_time_start)
  TextView tvTimeStart;
  @Bind(R.id.tv_time_end)
  TextView tvTimeEnd;
  int h, s;
  long timeSelected;
  @Bind(R.id.tv_error)
  TextView tvError;

  @SuppressLint("ValidFragment")
  public CustomTimeDialog(long startTime, long endTime,
      BookActivity activity, String day) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.activity = activity;
    this.day = day;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_time, container, false);
    ButterKnife.bind(this, view);

    timePicker.setIs24HourView(true);
    timePicker.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,hourOfDay,minute);
      Log.e("onCreateView", "CustomTimeDialog" + Utils.getHours(calendar.getTimeInMillis()));
      timeSelected = calendar.getTimeInMillis();

    });
    initStartEndTime();
    return view;
  }

  private void initStartEndTime() {
    tvTimeStart.setText(Utils.longToString(startTime));
    tvTimeEnd.setText(Utils.longToString(endTime));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @OnClick(R.id.btn_set_time)
  public void onViewClicked() {
    Log.e("onViewClicked", "CustomTimeDialog" + timeSelected);
    activity.handleTimeSelected(startTime,endTime,day,timeSelected);
    dismiss();
  }
}
