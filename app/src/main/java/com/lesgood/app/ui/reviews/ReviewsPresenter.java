package com.lesgood.app.ui.reviews;

import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.remote.UserService;

/**
 * Created by Agus on 6/1/17.
 */

public class ReviewsPresenter implements BasePresenter {
    ReviewsActivity activity;
    UserService userService;
    Guru user;

    public ReviewsPresenter(ReviewsActivity activity, UserService userService, Guru user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
