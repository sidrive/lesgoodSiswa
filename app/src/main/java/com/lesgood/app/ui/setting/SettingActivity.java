package com.lesgood.app.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;


import com.lesgood.app.AppCompatPreferenceActivity;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.login.LoginActivity;

import javax.inject.Inject;

/**
 * Created by Agus on 4/21/17.
 */

public class SettingActivity extends AppCompatPreferenceActivity {

    @Inject
    SettingPresenter presenter;

    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.get(this).getUserComponent().plus(new SettingActivityModule(this)).inject(this);

        addPreferencesFromResource(R.xml.preferences);

        setupActionBar();

        Preference myPref = (Preference) findPreference("logout");
        myPref.setOnPreferenceClickListener(preference -> {
            presenter.logout();
            return true;
        });


    }

    private void setupActionBar() {
        Toolbar toolbar;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewGroup root = (ViewGroup) findViewById(android.R.id.list).getParent().getParent().getParent();
            toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar, root, false);
            root.addView(toolbar, 0);
        } else {
            toolbar = null;
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Pengaturan");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    public void logingOut(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}