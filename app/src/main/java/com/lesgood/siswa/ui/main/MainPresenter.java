package com.lesgood.siswa.ui.main;


import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.remote.UserService;

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

    public void updateFCMToken(String uid, String token){
        userService.updateUserToken(uid, token);
    }
}
