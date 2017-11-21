package com.lesgood.app.ui.reviews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Reviews;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.rating.RatingActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 6/1/17.
 */

public class ReviewsActivity extends BaseActivity {
    private static int REQUEST_CODE_SKIL = 1059;

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
    ReviewsPresenter presenter;

    @Inject
    ReviewsAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);

        toolbar.setTitle("Penilaian");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        showItems();

    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getDetailTeacherComponent()
                .plus(new ReviewsActivityModule((this)))
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showAddedItem(Reviews item) {
        adapter.onItemAdded(item);
        adapter.notifyDataSetChanged();
    }

    public void showChangedItem(Reviews item) {
        adapter.onItemChanged(item);
        adapter.notifyDataSetChanged();
    }

    public void showRemovedItem(Reviews item){
        adapter.onItemRemoved(item);
        adapter.notifyDataSetChanged();
    }

    public void showItems() {
        rvItems.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
    }




    public void startAddReview(Reviews reviews){
        RatingActivity.startWithData(this, reviews);
    }
}
