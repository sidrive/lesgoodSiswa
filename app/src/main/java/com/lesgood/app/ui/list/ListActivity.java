package com.lesgood.app.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import android.widget.Toast;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivity;

import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by Agus on 5/11/17.
 */

public class ListActivity extends BaseActivity {

    @BindString(R.string.error_field_required)
    String errRequired;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.rv_items)
    RecyclerView rvItems;

    @Inject
    ListPresenter presenter;

    @Inject
    ListAdapter adapter;

    UserService userService;
    Guru user;

    public String code, pelajaran, oldOrderId;

    public static void startWithData(BaseActivity activity, String code, String pelajaran){
        Intent intent = new Intent(activity, ListActivity.class);
        if (code != null){
            intent.putExtra("code", code);
            intent.putExtra("pelajaran", pelajaran);
        }
        activity.startActivity(intent);
    }
    public static void startFromChangeTeacher(BaseActivity activity, String code, String pelajaran,String orderId){
        Intent intent = new Intent(activity, ListActivity.class);
        if (code != null){
            intent.putExtra("code", code);
            intent.putExtra("pelajaran", pelajaran);
            intent.putExtra("oldOrderId",orderId);
        }
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        showItems();
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            code = extras.getString("code");
            pelajaran = extras.getString("pelajaran");
            oldOrderId = extras.getString("oldOrderId");
            Log.e("onCreate", "ListActivity" + oldOrderId);
            Log.e("onCreate", "ListActivity" + code);
            Log.e("onCreate", "ListActivity" + pelajaran);
            if (code != null) {
                presenter.getGurus(code);
            }
        }
        toolbar.setTitle("Hasil Pencarian");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new ListActivityModule((this)))
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public String showStatus(String item) {

    return item;

    }


    public void showAddedItem(String item) {

        String stat = showStatus(item);
        if (stat != null ){
             adapter.onItemAdded(item);
      }else{
        Toast.makeText(this, stat+"Data Tidak Ada", Toast.LENGTH_SHORT).show();
      }
    }

    public void showChangedItem(String item) {
        adapter.onItemChanged(item);
    }

    public void showRemovedItem(String item){
        adapter.onItemRemoved(item);
    }

    public void showItems() {
        rvItems.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
    }

    public void showItemClicked(Guru user){
        Log.e("showItemClicked", "ListActivity" + oldOrderId);
        Random rand = new Random();
        String oid = System.currentTimeMillis()+""+Integer.toString(rand.nextInt(99999));
        Order order = new Order(oid, code, pelajaran);
        if (oldOrderId!=null){
            DetailTeacherActivity.startFromChangeTeacher(this,user,order,oldOrderId);
        }else {
            DetailTeacherActivity.startWithData(this, user, order);
        }

    }
}
