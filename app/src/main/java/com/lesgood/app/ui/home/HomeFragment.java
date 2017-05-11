package com.lesgood.app.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lesgood.app.R;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.base.BaseFragment;
import com.lesgood.app.data.model.Category;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.main.MainActivity;
import com.lesgood.app.ui.search.SearchActivity;
import com.lesgood.app.ui.setting.SettingActivity;
import com.lesgood.app.util.GridSpacingItemDecoration;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 4/27/17.
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.rv_items)
    RecyclerView rvItems;

    @Inject
    HomePresenter presenter;

    @Inject
    User user;

    @Inject
    MainActivity activity;

    @Inject
    CategoryAdapter adapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupFragmentComponent() {
        BaseApplication.get(getActivity())
                .getMainComponent()
                .plus(new HomeFragmentModule(this))
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle("Yogyakarta");

        showItems();

        initCateogies();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_change_location){

        }

        return super.onOptionsItemSelected(item);
    }

    public void initCateogies(){
        showAddItem(new Category("BM", "Materi Sekolah", R.drawable.courses));
        showAddItem(new Category("MS", "Musik", R.drawable.music));
        showAddItem(new Category("BA", "Bahasa Asing", R.drawable.google_translate_copyrighted));
        showAddItem(new Category("BD", "Bela Diri", R.drawable.boxing));
        showAddItem(new Category("KM", "Komputerisasi", R.drawable.workstation));
        showAddItem(new Category("MG", "Mengaji", R.drawable.koran));
        showAddItem(new Category("KT", "Keterampilan", R.drawable.connectdevelop));
        showAddItem(new Category("OL", "Olahraga", R.drawable.basketball));
    }

    private void showItems(){
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
        rvItems.setLayoutManager(mLayoutManager);
        rvItems.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvItems.setItemAnimator(new DefaultItemAnimator());
        rvItems.setAdapter(adapter);
    }

    public void showAddItem(Category item){
        adapter.onItemAdded(item);
    }

    public void showItemClicked(Category item){
        SearchActivity.startWithData(activity, item.getId());
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
