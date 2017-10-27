package com.lesgood.app.ui.home;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.app.base.BasePresenter;


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
