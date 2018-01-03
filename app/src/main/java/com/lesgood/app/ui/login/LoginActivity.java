package com.lesgood.app.ui.login;

import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.CallbackManagerImpl;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.edit_profile.EditProfileActivity;
import com.lesgood.app.ui.main.MainActivity;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @BindString(R.string.error_field_required)
    String errorRequired;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Inject
    LoginPresenter presenter;

    private CallbackManager callbackManager;
    boolean allGrant = false;
    boolean isLoginMode = false;
    private static final int RC_ALL_PERMISSION= 111;
    private static final String[] PERMISION =
        {permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
            permission.READ_CONTACTS,
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.CAMERA
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.M) {
            requestPermissionForMvers();
        }
    }

    private void requestPermissionForMvers() {
        if (
            ActivityCompat.checkSelfPermission(this, PERMISION[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISION[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISION[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISION[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISION[4]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, PERMISION[5]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[3])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[4])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISION[5])) {
            } else {
                ActivityCompat.requestPermissions(this,PERMISION,RC_ALL_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_ALL_PERMISSION){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allGrant = true;
                } else {
                    allGrant = false;
                }
            }
            if (allGrant){

            }else {
                requestPermissionForMvers();
            }

        }
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
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

    @OnClick(R.id.btn_login_with_facebook)
    public void onBtnLoginWithFacebook() {
        callbackManager = presenter.loginWithFacebook();
    }

    @OnClick(R.id.btn_login_with_google)
    public void loginwithgoogle(){

        Intent intent = presenter.loginWithGoogle();
        startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
    }


    public void showLoginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showLoginSuccess(User user) {
        MainActivity.startWithUser(this, user);
        finish();
    }

    public void showRegisterUser(User user){
        EditProfileActivity.startWithUser(this, user, true);
        finish();

    }

    public void showLoading(boolean show){
        if (show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    public void showLoginMode(){
        isLoginMode = true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // google
        if(requestCode == REQUEST_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.getAuthWithGoogle(result);
        }
        // facebook
        else if(requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}

