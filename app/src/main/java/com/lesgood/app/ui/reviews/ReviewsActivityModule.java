package com.lesgood.app.ui.reviews;

import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.remote.UserService;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 6/1/17.
 */
@Module
public class ReviewsActivityModule {
    ReviewsActivity activity;

    public ReviewsActivityModule(ReviewsActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    ReviewsActivity provideReviewsActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    ReviewsPresenter provideReviewsPresenter(UserService userService, Guru user){
        return new ReviewsPresenter(activity, userService, user);
    }
}
