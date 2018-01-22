package com.lesgood.siswa.data.rating;

import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.model.Reviews;

import dagger.Module;
import dagger.Provides;


/**
 * Created by ikun on 20/11/17.
 */

@Module
public class RatingModule {

    Reviews reviews;

    public RatingModule(Reviews reviews){
        this.reviews = reviews;
    }

    @UserScope
    @Provides
    Reviews provideReviews(){
        return reviews;
    }
}
