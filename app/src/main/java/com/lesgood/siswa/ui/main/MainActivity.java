package com.lesgood.siswa.ui.main;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;
import com.lesgood.siswa.R;
import com.lesgood.siswa.base.BaseActivity;
import com.lesgood.siswa.base.BaseApplication;
import com.lesgood.siswa.base.config.DefaultConfig;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.permission.LocationHelper;
import com.lesgood.siswa.data.remote.FetchAddressIntentService;
import com.lesgood.siswa.data.remote.LocationService;
import com.lesgood.siswa.ui.home.HomeFragment;
import com.lesgood.siswa.ui.order.OrderFragment;
import com.lesgood.siswa.ui.profile.ProfileFragment;
import com.lesgood.siswa.ui.splash.SplashActivity;
import com.lesgood.siswa.util.Utils;

import com.lesgood.siswa.util.preference.UserPreferences;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.navigation)
    BottomNavigationView navigation;

    @Inject
    MainPresenter presenter;

    @Inject
    User user;

    private BroadcastReceiver broadcastReceiver;
    private AddressResultReceiver mResultReceiver;
    LocationHelper  helper;
    Location location;
    boolean allGrant = false;

    private UserPreferences preferences;
    private static final int RC_ALL_PERMISSION= 111;

    private static final String[] PERMISION =
        {permission.ACCESS_FINE_LOCATION,
            permission.ACCESS_COARSE_LOCATION,
            permission.READ_CONTACTS,
            permission.READ_EXTERNAL_STORAGE,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.CAMERA
        };
    private void startService(){
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(LocationService.BROADCAST_ACTION));
    }

    protected void startServiceAddress(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(DefaultConfig.RECEIVER, mResultReceiver);
        intent.putExtra(DefaultConfig.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = item -> {

        Fragment fragment = HomeFragment.newInstance();

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = HomeFragment.newInstance();
                break;
            case R.id.navigation_order:
                fragment = OrderFragment.newInstance();
                break;
            case R.id.navigation_profile:
                fragment = ProfileFragment.newInstance();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            return true;
        }

        return false;
    };



    public static void startWithUser(BaseActivity activity, final User user) {
        Intent intent = new Intent(activity, MainActivity.class);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    public static void openFragment(Fragment f, FragmentManager fm){
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, f);
        ft.commit();
    }
    public static String KEY_PARAM_MSG = "msg";
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(tokenReceiver,
            new IntentFilter("tokenReceiver"));
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        preferences = new UserPreferences(this);
        pd = new ProgressDialog(this);
        pd.setTitle("Loading...");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        startService();
        /*if (allGrant){

        }else {
            if (android.os.Build.VERSION.SDK_INT >= VERSION_CODES.M) {
                requestPermissionForMvers();
            }
        }*/


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MainActivity.this.MethodName(intent);
            }
        };
        mResultReceiver = new AddressResultReceiver(new Handler());

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (user != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            presenter.updateFCMToken(user.getUid(),token);
        }
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String order = extras.getString("order");
            Fragment fragment = OrderFragment.newInstance();
            openFragment(fragment,getSupportFragmentManager());

        }else {
            Fragment fragment = HomeFragment.newInstance();
            openFragment(fragment,getSupportFragmentManager());
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
            startService();


            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allGrant = true;
                } else {
                    allGrant = false;
                }
            }
            if (allGrant){
                startService();
            }else {
                requestPermissionForMvers();
            }

        }
    }

    private void reloadActivity() {
        Intent i = new Intent(this,SplashActivity.class);
        startActivity(i);
        finish();
    }


    public void MethodName(Intent intent){
        final double lat = intent.getDoubleExtra("Latitude", 0);
        final double lng = intent.getDoubleExtra("Longitude", 0);
        final String provider = intent.getStringExtra("Provider");

        Log.d("GETLOCATIONSERVICE", "provider = "+provider);
        Log.d("GETLOCATIONSERVICE", "Latitude = "+lat);
        Log.d("GETLOCATIONSERVICE", "Longitude = "+lng);
        preferences.write(DefaultConfig.KEY_USER_LNG,String.valueOf(lng),String.class);
        preferences.write(DefaultConfig.KEY_USER_LAT,String.valueOf(lat),String.class);
        Location bestLocation = new Location(provider);
        bestLocation.setLatitude(lat);
        bestLocation.setLongitude(lng);
        SharedPreferences sharedPreferences = getSharedPreferences(DefaultConfig.KEY_USER_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Utils.putDouble(editor, DefaultConfig.KEY_USER_LAT, lat);
        Utils.putDouble(editor, DefaultConfig.KEY_USER_LNG, lng);
        editor.apply();
        if (lat!=0 && lng !=0){
            startServiceAddress(bestLocation);
        }else {
            pd.show();
            startService();
        }

    }

    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            if(token != null)
            {
                presenter.updateFCMToken(user.getUid(),token);
            }

        }
    };

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getUserComponent()
            .plus(new MainActivityModule(this))
            .inject(this);
        BaseApplication.get(this).createMainComponent(this);
    }

    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            // Show a toast message if an address was found.
            if (resultCode == DefaultConfig.SUCCESS_RESULT) {
                pd.dismiss();
                String adminArea = resultData.getString(DefaultConfig.ADMIN_AREA_DATA_EXTRA);
                String locality = resultData.getString(DefaultConfig.LOCALITY_DATA_EXTRA);
                String postalCode = resultData.getString(DefaultConfig.POSTAL_CODE_DATA_EXTRA);
                String countryCode = resultData.getString(DefaultConfig.COUNTRY_CODE_DATA_EXTRA);
                preferences.write(DefaultConfig.KEY_USER_AREA,adminArea,String.class);
                Log.d("onReceiveResult", "adminArea = "+adminArea);
                Log.d("onReceiveResult", "locality = "+locality);
                Log.d("onReceiveResult", "postalCode = "+postalCode);
                Log.d("onReceiveResult", "countryCode = "+countryCode);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocationService.class);
        if (intent!=null){
            startService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, LocationService.class);
        if(intent != null) {
            stopService(intent);
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Intent intent = new Intent(this, LocationService.class);
        if(intent != null) {
            unregisterReceiver(broadcastReceiver);
            stopService(intent);
        }
    }
}