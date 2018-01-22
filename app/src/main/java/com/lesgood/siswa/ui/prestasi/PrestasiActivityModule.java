package com.lesgood.siswa.ui.prestasi;


import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;

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
