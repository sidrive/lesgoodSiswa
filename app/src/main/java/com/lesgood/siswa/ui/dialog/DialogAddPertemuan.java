package com.lesgood.siswa.ui.dialog;

import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lesgood.siswa.R;
import com.lesgood.siswa.data.model.Jadwal;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.ui.book.BookActivity;
import com.lesgood.siswa.util.DateFormatter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 6/14/17.
 */

public class DialogAddPertemuan extends Dialog implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    OnDialogUploadOptionClickListener mCallBack;

    private BookActivity activity;
    private User user;

    @Bind(R.id.btn_tanggal)
    Button btnTanggal;

    @Bind(R.id.btn_start_time)
    Button btnStartTime;

    @Bind(R.id.btn_end_time)
    Button btnEndTime;

    Jadwal jadwal;

    boolean start_time = false;
    boolean complete = false;

    public interface OnDialogUploadOptionClickListener {
        public void onPositif(Dialog dialog, Jadwal jadwal);

        public void onNegatif(Dialog dialog);
    }

    public DialogAddPertemuan(BookActivity activity, User user) {
        super(activity);
        this.activity = activity;
        this.user = user;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_insert_pertemuan);
        Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        setCancelable(true);

        jadwal = new Jadwal();

        init();

        try {
            mCallBack = (OnDialogUploadOptionClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDialogUploadOptionClickListener");
        }

    }

    private void init() {
        jadwal.setName(user.getFull_name());

    }

    @OnClick(R.id.btn_positif)
    void positifClicked() {
        validation();
    }

    @OnClick(R.id.btn_tanggal)
    void showAddTanggal(){
        showDialogDatePicker();
    }

    @OnClick(R.id.btn_start_time)
    void showStartTime(){
        if(jadwal.getStartTime() == 0){
            Toast.makeText(activity, "Pilih tanggal terlebih dahulu", Toast.LENGTH_SHORT).show();
        }else{
            showDialogTimePicker();
        }
    }

    @OnClick(R.id.btn_negatif)
    void negatifClicked() {
        mCallBack.onNegatif(this);
    }

    private void validation(){
        boolean cancel = false;

        if (jadwal.getStartTime() == 0){
            cancel = true;
            Toast.makeText(activity, "Lengakapi data", Toast.LENGTH_SHORT).show();
        }

        if (!complete) {
            cancel= true;
            Toast.makeText(activity, "Lengakapi data", Toast.LENGTH_SHORT).show();
        }

        if (!cancel){
            mCallBack.onPositif(this, jadwal);
        }
    }


    private void showDialogDatePicker(){
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setTitle("Tanggal");
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    private void showDialogTimePicker(){
        Calendar cal = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                this,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        );

        dpd.setTitle("Waktu mulai");
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.show(activity.getFragmentManager(), "TImepickerdialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        jadwal.setStartTime(c.getTimeInMillis());
        jadwal.setEndTime(c.getTimeInMillis());

        btnTanggal.setText(DateFormatter.getDate(c.getTimeInMillis(), "EE dd MMM yyyy"));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar c = Calendar.getInstance();
        Calendar ce = Calendar.getInstance();
        if (jadwal.getStartTime() != 0){
            c.setTimeInMillis(jadwal.getStartTime());
            ce.setTimeInMillis(jadwal.getStartTime());
        }

        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
        ce.setTimeInMillis(c.getTimeInMillis());
        ce.add(Calendar.MINUTE, 100);

        jadwal.setStartTime(c.getTimeInMillis());
        jadwal.setEndTime(ce.getTimeInMillis());

        btnStartTime.setText(DateFormatter.getDate(c.getTimeInMillis(), "HH:mm"));
        btnEndTime.setText(DateFormatter.getDate(ce.getTimeInMillis(), "HH:mm"));

        complete = true;
    }
}
