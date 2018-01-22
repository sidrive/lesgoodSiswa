package com.lesgood.siswa.data.firebase;

import android.app.Application;


import com.lesgood.siswa.data.remote.FirebaseImageService;
import com.lesgood.siswa.data.remote.FirebaseUserService;
import com.lesgood.siswa.data.remote.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FirebaseUserService provideFirebaseUserService(Application application) {
        return new FirebaseUserService(application);
    }

    @Provides
    @Singleton
    public UserService provideUserService(Application application) {
        return new UserService(application);
    }

    @Provides
    @Singleton
    public FirebaseImageService provideFirebaseImageService(Application application) {
        return new FirebaseImageService(application);
    }

}
