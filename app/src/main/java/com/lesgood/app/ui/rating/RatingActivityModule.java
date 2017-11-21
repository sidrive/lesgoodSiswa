package com.lesgood.app.ui.rating;

import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.UserService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ikun on 17/11/17.
 */
@Module
public class RatingActivityModule {
   /* RatingActivity activity;

    public RatingActivityModule (RatingActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    RatingActivity provideRatingActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    RatingActivityPresenter provideRatingActivityPresenter(UserService userService, Guru guru, User user){
        return new RatingActivityPresenter(activity,userService,guru, user);
    }*/
}
