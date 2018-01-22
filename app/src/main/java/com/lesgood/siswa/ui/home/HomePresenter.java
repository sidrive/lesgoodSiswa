package com.lesgood.siswa.ui.home;

import com.google.firebase.database.DatabaseReference;
import com.lesgood.siswa.base.BasePresenter;


/**
 * Created by Agus on 4/27/17.
 */

public class HomePresenter implements BasePresenter {
    HomeFragment fragment;
    DatabaseReference databaseRef;

    public HomePresenter(HomeFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }


}
