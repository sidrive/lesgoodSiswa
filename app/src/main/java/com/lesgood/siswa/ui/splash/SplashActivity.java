package com.lesgood.siswa.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.lesgood.siswa.R;
import com.lesgood.siswa.base.BaseActivity;
import com.lesgood.siswa.base.BaseApplication;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.ui.edit_profile.EditProfileActivity;
import com.lesgood.siswa.ui.login.LoginActivity;
import com.lesgood.siswa.ui.main.MainActivity;

import javax.inject.Inject;


public class SplashActivity extends BaseActivity {

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*Intent intent = new Intent(this, LocationService.class);
        startService(intent);*/
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
