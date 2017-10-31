package com.lesgood.app.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.lesgood.app.base.config.DefaultConfig;
import com.lesgood.app.data.book.BookComponent;
import com.lesgood.app.data.book.BookModule;
import com.lesgood.app.data.detail_teacher.DetailTeacherComponent;
import com.lesgood.app.data.detail_teacher.DetailTeacherModule;
import com.lesgood.app.data.firebase.FirebaseModule;
import com.lesgood.app.data.main.MainComponent;
import com.lesgood.app.data.main.MainModule;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.network.NetworkModule;
import com.lesgood.app.data.order_detail.OrderDetailComponent;
import com.lesgood.app.data.order_detail.OrderDetailModule;
import com.lesgood.app.data.user.UserComponent;
import com.lesgood.app.data.user.UserModule;
import com.lesgood.app.ui.book_2.BookActivityModule;
import com.lesgood.app.ui.main.MainActivity;

/**
 * Created by Agus on 4/16/17.
 */

public class BaseApplication extends MultiDexApplication {
    public static AppComponent appComponent;
    public static UserComponent userComponent;
    public static MainComponent mainComponent;
    public static DefaultConfig defaultConfig;
    public static OrderDetailComponent orderDetailComponent;
    public static DetailTeacherComponent detailTeacherComponent;
    public static BookComponent bookComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //FirebaseApp.initializeApp(base);
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
        FirebaseApp.initializeApp(getBaseContext());
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

    public OrderDetailComponent createOrderDetailComponent(Order order){
        orderDetailComponent = mainComponent.plus(new OrderDetailModule((order)));
        return orderDetailComponent;
    }

    public OrderDetailComponent getOrderDetailComponent(){
        return orderDetailComponent;
    }

    public void releaseOrderDetailComponent(){
        orderDetailComponent = null;
    }

    public DetailTeacherComponent createDetailTeacherComponent(Guru guru){
        detailTeacherComponent = userComponent.plus(new DetailTeacherModule(guru));
        return detailTeacherComponent;
    }

    public DetailTeacherComponent getDetailTeacherComponent(){
        return detailTeacherComponent;
    }

    public void releaseDetailTeacherComponent(){
        detailTeacherComponent = null;
    }

    public BookComponent createBookComponent(Order order){
        bookComponent = detailTeacherComponent.plus(new BookModule(order));
        return bookComponent;
    }

    public BookComponent getBookComponent(){
        return bookComponent;
    }

    public void releaseBookComponent(){
        bookComponent = null;
    }

}
