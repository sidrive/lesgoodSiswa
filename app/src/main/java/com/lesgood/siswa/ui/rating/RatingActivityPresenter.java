package com.lesgood.siswa.ui.rating;

import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;

/**
 * Created by ikun on 17/11/17.
 */

public class RatingActivityPresenter implements BasePresenter {
    RatingActivity activity;
    UserService userService;
    Guru guru;
    /*User user;*/


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public RatingActivityPresenter (RatingActivity activity, UserService userService, Guru guru/*, User user*/){
        this.activity       = activity;
        this.userService    = userService;
        this.guru           = guru;
        /*this.user           = user;*/
    }
}
