package com.lesgood.app.ui.main;


import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.remote.UserService;

/**
 * Created by Agus on 4/16/17.
 */

public class MainPresenter implements BasePresenter {

    MainActivity activity;
    UserService userService;

    public MainPresenter(MainActivity activity, UserService userService){
        this.activity = activity;
        this.userService = userService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
