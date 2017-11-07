package com.lesgood.app.ui.list;

import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.UserService;

/**
 * Created by Agus on 5/11/17.
 */

public class ListPresenter implements BasePresenter {
    ListActivity activity;
    UserService userService;
    User user;
    DatabaseReference databaseRef;
    ChildEventListener childEventListener;

    public ListPresenter(ListActivity activity, UserService userService, User user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {

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
                        getUserStatus(uid);
                        //activity.showAddedItem(uid);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    activity.showChangedItem(uid);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    activity.showRemovedItem(uid);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUserStatus(String code) {
        //activity.showAddedItem(code);
        String pelajaran = "BINGPM";
        String code2 = "hQg3kf6isfXDNsD3JuphFt79ffg2";
        childEventListener = userService.getStatus(code).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getValue() != null){
                    String status = (String) dataSnapshot.getValue();
                    if (status.equals("true")){
                        activity.showAddedItem(code);
                    }/*else {
                        Toast.makeText(activity, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }*/

               }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    activity.showChangedItem(uid);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    String uid = dataSnapshot.getKey();
                    activity.showRemovedItem(uid);
                }
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
