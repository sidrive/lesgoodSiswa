package com.lesgood.siswa.ui.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.base.config.DefaultConfig;
import com.lesgood.siswa.data.model.User;

import com.lesgood.siswa.data.remote.LocationService;
import com.lesgood.siswa.data.remote.UserService;
import com.lesgood.siswa.util.preference.UserPreferences;

/**
 * Created by Agus on 5/11/17.
 */

public class ListPresenter implements BasePresenter {
    ListActivity activity;
    UserService userService;
    User user;
    DatabaseReference databaseRef;
    ChildEventListener childEventListener;
    SharedPreferences preferences;
    UserPreferences userPreferences;

    public ListPresenter(ListActivity activity, UserService userService, User user ){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.userPreferences = new UserPreferences(activity.getApplicationContext());
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.preferences = activity.getSharedPreferences(DefaultConfig.KEY_USER_PREF, Context.MODE_PRIVATE);

    }

    @Override
    public void subscribe() {
    }

    private void getGeofireguru(String uid) {
        if (userPreferences!=null){
            String sLat = userPreferences.read(DefaultConfig.KEY_USER_LAT,String.class);
            String sLng = userPreferences.read(DefaultConfig.KEY_USER_LNG,String.class);

            Log.e("geofire","sLat"+sLat);
            Log.e("geofire","sLng"+sLng);
            if (!sLat.isEmpty()  && !sLng.isEmpty()){
                GeoLocation location = new GeoLocation(Double.valueOf(sLat),Double.valueOf(sLng));
                Log.e("getGeofireguru", "ListPresenter" + location);
                if (location!=null){
                    Log.e("getGeofireguru", "Running....." );
                    GeoQuery geoQuery = userService.getUserGeofire(databaseRef.child("user-geofire"))
                        .queryAtLocation(location,30);//20km
                    geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                        @Override
                        public void onKeyEntered(String s, GeoLocation geoLocation) {
                            if (s.equals(uid)){
                                activity.showAddedItem(s);
                            }
                        }

                        @Override
                        public void onKeyExited(String s) {

                        }

                        @Override
                        public void onKeyMoved(String s, GeoLocation geoLocation) {

                        }

                        @Override
                        public void onGeoQueryReady() {

                        }

                        @Override
                        public void onGeoQueryError(DatabaseError databaseError) {

                        }
                    });
                }else {
                    Toast.makeText( activity.getApplicationContext(), "Gps Disabled, current location is approximate", Toast.LENGTH_LONG ).show();
                }
            }else {
                activity.startService(new Intent(activity,LocationService.class));
            }
            }

    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseRef.removeEventListener(childEventListener);
    }

    public void getGurus(String code){
        Log.e("getGuru","code "+code);
        childEventListener = userService.getGurus(code).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    //activity.showAddedItem(uid);
                    Log.e("onChildAdded", "ListPresenter getGurus " + uid);
                    //getGuruIsActive(uid);
                    getGeofireguru(uid);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    //activity.showChangedItem(uid);
                    //getGeofireguru(uid);
                    getGeofireguru(uid);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    //activity.showRemovedItem(uid);
                    //getGeofireguru(uid);
                    getGeofireguru(uid);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "ListPresenter" + databaseError.getMessage());
            }
        });
    }
    public void getGuruIsActive(String uid){
        userService.getGutuIsActive(uid)
            .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = dataSnapshot.getValue(User.class);
                Log.e("onDataChange", "ListPresenter getGuruIsActive " + user.getUid());
                //getGeofireguru(user.getUid());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
