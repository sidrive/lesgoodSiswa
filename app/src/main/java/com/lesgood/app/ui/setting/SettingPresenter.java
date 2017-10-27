package com.lesgood.app.ui.setting;


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

    public SettingPresenter(SettingActivity activity, FirebaseUserService firebaseUserService, User user){
        this.activity = activity;
        this.firebaseUserService = firebaseUserService;
        this.user = user;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void logout(){
        firebaseUserService.logOut(user.getProvider());
        activity.logingOut();
    }
}
