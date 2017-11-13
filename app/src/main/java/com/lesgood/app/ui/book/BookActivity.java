package com.lesgood.app.ui.book;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Jadwal;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Prestasi;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.dialog.DialogAddPertemuan;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Agus on 6/14/17.
 */

public class BookActivity extends BaseActivity implements DialogAddPertemuan.OnDialogUploadOptionClickListener{

    @BindString(R.string.error_field_required)
    String errRequired;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.rv_skill)
    RecyclerView rvSkill;

    @Bind(R.id.rv_pertemuan)
    RecyclerView rvPertemuan;

    @Bind(R.id.input_total_siswa)
    EditText inputTotalSiswa;

    @Bind(R.id.input_total_pertemuan)
    EditText totalPertemuan;

    @Bind(R.id.txt_nama_pengajar)
    TextView txtNamaPengajar;

    @Bind(R.id.img_avatar)
    CircleImageView imgAvatar;

    @Inject
    Guru guru;

    @Inject
    User user;

    @Inject
    BookActivityPresenter presenter;

    @Inject
    PertemuanAdapter pertemuanAdapter;

    @Inject
    SkillAdapter skillAdapter;

    Dialog addPertemuanDialog;

    Order order;

    Skill skill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        //showLoading(true);
        toolbar.setTitle("Pesan Pengajar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        order = new Order();

        showItems();
        showPertemuanItems();
        initGuru();
    }

    public void initGuru(){
        txtNamaPengajar.setText(guru.getFull_name());

        StorageReference ref = presenter.getThumbAvatar(guru.getUid());
        Glide.with(getApplicationContext())
                .using(new FirebaseImageLoader())
                .load(ref)
                .into(imgAvatar);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getDetailTeacherComponent()
                .plus(new BookActivityModule(this))
                .inject(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultF();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            setResultF();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        skillAdapter.clearList();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        skillAdapter.clearList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public void showLoading(boolean show){
        if(show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    private void setResultF(){
        finish();
    }

    public void showAddedItem(Skill item) {
        skillAdapter.onItemAdded(item);
    }

    public void showChangedItem(Skill item) {
        skillAdapter.onItemChanged(item);
    }

    public void showRemovedItem(Skill item){
        skillAdapter.onItemRemoved(item);
    }

    public void showItems() {
        rvSkill.setAdapter(skillAdapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSkill.setLayoutManager(layoutManager);
    }

    public void selectedItem(Skill item){
        this.skill = item;
    }

    public void showAddedItem(Jadwal item) {
        pertemuanAdapter.onItemAdded(item);

    }

    public void showChangedItem(Jadwal item) {
        pertemuanAdapter.onItemChanged(item);
    }

    public void showRemovedItem(Jadwal item){
        pertemuanAdapter.onItemRemoved(item);
    }

    public void showPertemuanItems() {
        rvPertemuan.setAdapter(pertemuanAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPertemuan.setLayoutManager(linearLayoutManager);
    }

    public void showDeleteItem(Prestasi Prestasi){
        showLoading(true);
    }

    @OnClick(R.id.btn_add_pertemuan)
    void showAddPertemuan(){
        addPertemuan();
    }

    public void addPertemuan(){
        DialogAddPertemuan dialogAddPertemuan = new DialogAddPertemuan(this, user);
        dialogAddPertemuan.show();


    }

    @Override
    public void onPositif(Dialog dialog, Jadwal jadwal) {
        showAddedItem(jadwal);
        dialog.dismiss();
    }

    @Override
    public void onNegatif(Dialog dialog) {
        dialog.dismiss();
    }

    public void updateTotalPertemuan(int total){
        String strTotall = String.valueOf(total);
        totalPertemuan.setText(strTotall);

    }

    public void validate(){
        boolean cancel = false;

        int totalPertemuan = pertemuanAdapter.getItemCount();
        int totalSiswa = Integer.valueOf(inputTotalSiswa.getText().toString());

        if (totalPertemuan < 10){
            cancel = true;
            Toast.makeText(this, "Minimal 10 pertemuan", Toast.LENGTH_SHORT).show();
        }

        if (totalSiswa < 1 || totalSiswa > 5){
            cancel = true;
            Toast.makeText(this, "Minumal siswa 1 dan maksimal 5", Toast.LENGTH_SHORT).show();
        }

        if(skill == null){
            cancel = true;
            Toast.makeText(this, "Pilih pelajaran terlebih dahulu", Toast.LENGTH_SHORT).show();
        }

        if (cancel){

        }else{
            String oid = UUID.randomUUID().toString();
            long ordertime = System.currentTimeMillis();
            String title = skill.getSkill()+" "+skill.getLevel();

            int amount = (totalPertemuan*skill.getPrice1())*totalSiswa;
            double fee = amount*0.5;

            order.setOid(oid);
            order.setAmount(amount);
            order.setCode(skill.getCode());
            order.setGid(guru.getUid());
            order.setOrdertime(ordertime);
            order.setPertemuan(pertemuanAdapter.items);
            order.setUid(user.getUid());
            order.setFee(fee);
            order.setTotal(amount+fee);
            order.setTotalPertemuan(totalPertemuan);
            order.setTotal(totalSiswa);
            order.setTitle(title);
            order.setStatus("waiting");
        }
    }
    public static class DialogCalender extends DialogFragment{
        static DialogCalender newInstance(){
            return new DialogCalender();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_calender,container,false);

            return view;
        }
    }
}
