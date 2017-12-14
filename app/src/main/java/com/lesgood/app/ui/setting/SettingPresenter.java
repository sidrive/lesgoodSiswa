package com.lesgood.app.ui.setting;


import com.google.firebase.auth.FirebaseAuth;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.FirebaseUserService;

/**
 * Created by Agus on 4/21/17.
 */

public class SettingPresenter implements BasePresenter {
    SettingActivity activity;
    FirebaseUserService firebaseUserService;
    User user;
    FirebaseAuth mAuth;
    public SettingPresenter(SettingActivity activity, FirebaseUserService firebaseUserService, User user){
        this.activity = activity;
        this.firebaseUserService = firebaseUserService;
        this.user = user;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void logout(){
        /*firebaseUserService.logOut(user.getProvider());
        activity.logingOut();*/
        firebaseUserService.logingOut();
        activity.logingOut();
    }
}
