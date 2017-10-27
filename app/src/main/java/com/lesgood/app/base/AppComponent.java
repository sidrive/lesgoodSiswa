package com.lesgood.app.base;


import com.lesgood.app.data.firebase.FirebaseModule;
import com.lesgood.app.data.network.NetworkModule;
import com.lesgood.app.data.user.UserComponent;
import com.lesgood.app.data.user.UserModule;
import com.lesgood.app.ui.login.LoginActivityComponent;
import com.lesgood.app.ui.login.LoginActivityModule;
import com.lesgood.app.ui.splash.SplashActivityComponent;
import com.lesgood.app.ui.splash.SplashActivityModule;

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