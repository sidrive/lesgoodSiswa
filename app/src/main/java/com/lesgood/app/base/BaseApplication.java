package com.lesgood.app.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.lesgood.app.base.config.DefaultConfig;
import com.lesgood.app.data.firebase.FirebaseModule;
import com.lesgood.app.data.main.MainComponent;
import com.lesgood.app.data.main.MainModule;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.network.NetworkModule;
import com.lesgood.app.data.user.UserComponent;
import com.lesgood.app.data.user.UserModule;
import com.lesgood.app.ui.main.MainActivity;

/**
 * Created by Agus on 4/16/17.
 */

public class BaseApplication extends Application {
    private AppComponent appComponent;
    private UserComponent userComponent;
    private MainComponent mainComponent;
    private DefaultConfig defaultConfig;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        FirebaseApp.initializeApp(base);
        defaultConfig = new DefaultConfig(base);
        MultiDex.install(getBaseContext());
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        Log.d("initappcomponent", " = "+defaultConfig.getApiUrl());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .networkModule(new NetworkModule(defaultConfig.getApiUrl()))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UserComponent createUserComponent(User user) {
        userComponent = appComponent.plus(new UserModule(user));
        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

    public void releaseUserComponent() {
        userComponent = null;
    }

    public MainComponent createMainComponent(MainActivity activity) {
        mainComponent = userComponent.plus(new MainModule(activity));
        return mainComponent;
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public void releaseMainComponent() {
        mainComponent = null;
    }

}
