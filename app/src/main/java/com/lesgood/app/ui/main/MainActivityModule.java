package com.lesgood.app.ui.main;


import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/16/17.
 */

@Module
public class MainActivityModule {
    MainActivity activity;

    public MainActivityModule(MainActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    MainPresenter provideMainPresenter(UserService userService){
        return new MainPresenter(activity, userService);
    }
}
