package com.lesgood.app.ui.prestasi;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Prestasi;
import com.lesgood.app.ui.search.SearchActivity;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 6/1/17.
 */

public class PrestasiActivity extends BaseActivity {
    @BindString(R.string.error_field_required)
    String errRequired;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rv_items)
    RecyclerView rvItems;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Inject
    Guru user;

    @Inject
    PrestasiPresenter presenter;

    @Inject
    PrestasiAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestasi);
        ButterKnife.bind(this);

        toolbar.setTitle("Prestasi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showItems();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getDetailTeacherComponent()
                .plus(new PrestasiActivityModule((this)))
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clearList();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.clearList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultF();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            setResultF();
        }

        return super.onOptionsItemSelected(item);
    }


    public void showLoading(boolean show){
        if(show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    private void setResultF(){
        Intent intent = new Intent(this, SearchActivity.class);
        int totalPrestasi = adapter.getItemCount();
        intent.putExtra("totalPrestasi", totalPrestasi);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void showAddedItem(Prestasi item) {
        adapter.onItemAdded(item);
    }

    public void showChangedItem(Prestasi item) {
        adapter.onItemChanged(item);
    }

    public void showRemovedItem(Prestasi item){
        adapter.onItemRemoved(item);
    }

    public void showItems() {
        rvItems.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
    }

    /*public void showDeleteItem(Prestasi Prestasi){
        showLoading(true);
        presenter.deletePrestasi(Prestasi);
    }
*/
}
