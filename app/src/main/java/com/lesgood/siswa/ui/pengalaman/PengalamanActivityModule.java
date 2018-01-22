package com.lesgood.siswa.ui.pengalaman;


import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 6/1/17.
 */

@Module
public class PengalamanActivityModule {
    PengalamanActivity activity;

    public PengalamanActivityModule(PengalamanActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    PengalamanActivity providePrestasiActivity(){
        return activity = activity;
    }

    @ActivityScope
    @Provides
    PengalamanPresenter providePrestasiPresenter(UserService userService, Guru user){
        return new PengalamanPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    PengalamanAdapter providePrestasiAdapter(){
        return new PengalamanAdapter(activity);
    }
}
