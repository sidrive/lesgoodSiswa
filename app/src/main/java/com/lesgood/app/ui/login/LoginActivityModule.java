package com.lesgood.app.ui.login;




import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.remote.FirebaseUserService;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 2/27/17.
 */

@Module
public class LoginActivityModule {
    LoginActivity activity;

    public LoginActivityModule(LoginActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivity provideLoginActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    LoginPresenter provideLoginPresenter(UserService userService, FirebaseUserService firebaseUserService){
        return new LoginPresenter(activity, userService, firebaseUserService);
    }
}
