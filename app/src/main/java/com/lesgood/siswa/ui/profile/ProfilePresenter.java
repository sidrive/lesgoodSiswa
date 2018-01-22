package com.lesgood.siswa.ui.profile;

import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.UserService;

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
