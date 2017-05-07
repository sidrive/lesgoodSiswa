package com.lesgood.app.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.edit_profile.EditProfileActivity;
import com.lesgood.app.ui.login.LoginActivity;
import com.lesgood.app.ui.main.MainActivity;

import javax.inject.Inject;


public class SplashActivity extends BaseActivity {

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public void showLoginActivity() {
        Intent a=new Intent(this,LoginActivity.class);
        startActivity(a);
        finish();

    }

    public void showRegisterActivity(User user){
        EditProfileActivity.startWithUser(this, user, true);
        finish();
    }

    public void showMainActivity(User user){
        MainActivity.startWithUser(this, user);
        finish();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new SplashActivityModule(this))
                .inject(this);
    }


}
