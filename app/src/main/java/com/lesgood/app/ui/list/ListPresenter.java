package com.lesgood.app.ui.list;

import android.content.Context;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.base.config.DefaultConfig;
import com.lesgood.app.data.model.GeoFire;
import com.lesgood.app.data.model.User;

import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.util.Utils;

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


    public ListPresenter(ListActivity activity, UserService userService, User user ){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.preferences = activity.getSharedPreferences(DefaultConfig.KEY_USER_PREF, Context.MODE_PRIVATE);

    }

    @Override
    public void subscribe() {
    }

    private void getGeofireguru(String uid) {
        Log.e("getGeofireguru", "Running....." );
           GeoLocation location = new GeoLocation(Utils.getDouble(preferences,DefaultConfig.KEY_USER_LAT,0.00), Utils.getDouble(preferences,DefaultConfig.KEY_USER_LNG,0.00));
        Log.e("getGeofireguru", "ListPresenter" + location);
        if (location!=null){
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

    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseRef.removeEventListener(childEventListener);
    }

    public void getGurus(String code){
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
