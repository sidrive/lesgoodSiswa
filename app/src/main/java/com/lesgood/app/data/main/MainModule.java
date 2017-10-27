package com.lesgood.app.data.main;


import com.lesgood.app.base.annotation.MainScope;
import com.lesgood.app.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/20/17.
 */

@Module
public class MainModule {
    MainActivity activity;

    public MainModule(MainActivity activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    MainActivity provideMainActivity(){
        return activity;
    }
}
