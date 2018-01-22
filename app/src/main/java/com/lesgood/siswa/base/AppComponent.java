package com.lesgood.siswa.base;


import com.lesgood.siswa.data.firebase.FirebaseModule;
import com.lesgood.siswa.data.network.NetworkModule;
import com.lesgood.siswa.data.user.UserComponent;
import com.lesgood.siswa.data.user.UserModule;
import com.lesgood.siswa.ui.login.LoginActivityComponent;
import com.lesgood.siswa.ui.login.LoginActivityModule;
import com.lesgood.siswa.ui.splash.SplashActivityComponent;
import com.lesgood.siswa.ui.splash.SplashActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Agus on 4/16/17.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                FirebaseModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    SplashActivityComponent plus(SplashActivityModule activityModule);

    LoginActivityComponent plus(LoginActivityModule activityModule);

    UserComponent plus(UserModule userModule);

    Retrofit retrofit();

}