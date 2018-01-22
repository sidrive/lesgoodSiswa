package com.lesgood.siswa.ui.splash;



import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {
    private SplashActivity activity;

    public SplashActivityModule(SplashActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    SplashActivity provideSplashActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    SplashPresenter provideSplashActivityPresenter(UserService userService) {
        return new SplashPresenter(activity, userService);
    }

}
