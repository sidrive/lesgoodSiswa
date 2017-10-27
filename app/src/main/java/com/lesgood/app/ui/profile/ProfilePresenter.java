package com.lesgood.app.ui.profile;

import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.UserService;

/**
 * Created by Agus on 2/28/17.
 */

public class ProfilePresenter implements BasePresenter {
    ProfileFragment fragment;
    User user;
    UserService userService;

    public ProfilePresenter(ProfileFragment fragment, User user, UserService userService){
        this.fragment = fragment;
        this.user = user;
        this.userService = userService;

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
