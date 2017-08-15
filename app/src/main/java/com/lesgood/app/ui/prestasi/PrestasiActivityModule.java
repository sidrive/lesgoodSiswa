package com.lesgood.app.ui.prestasi;


import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 6/1/17.
 */

@Module
public class PrestasiActivityModule {
    PrestasiActivity activity;

    public PrestasiActivityModule(PrestasiActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    PrestasiActivity providePrestasiActivity(){
        return activity = activity;
    }

    @ActivityScope
    @Provides
    PrestasiPresenter providePrestasiPresenter(UserService userService, Guru user){
        return new PrestasiPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    PrestasiAdapter providePrestasiAdapter(){
        return new PrestasiAdapter(activity);
    }
}
