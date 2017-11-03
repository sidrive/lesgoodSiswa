package com.lesgood.app.ui.prestasi;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Prestasi;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.UserService;

/**
 * Created by Agus on 6/1/17.
 */

public class PrestasiPresenter implements BasePresenter {

    PrestasiActivity activity;
    UserService userService;
    Guru user;
    ChildEventListener childEventListener;
    DatabaseReference databaseReference;

    public PrestasiPresenter(PrestasiActivity activity, UserService userService, Guru user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void subscribe() {
        getPrestasi();
    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseReference.removeEventListener(childEventListener);
    }

    public void getPrestasi(){
        childEventListener = userService.getUserPrestasi(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Prestasi prestasi = dataSnapshot.getValue(Prestasi.class);
                if (prestasi != null){
                    activity.showAddedItem(prestasi);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Prestasi prestasi = dataSnapshot.getValue(Prestasi.class);
                if (prestasi != null){
                    activity.showChangedItem(prestasi);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Prestasi prestasi = dataSnapshot.getValue(Prestasi.class);
                if (prestasi != null){
                    activity.showRemovedItem(prestasi);
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

    public void deletePrestasi(final Prestasi prestasi){
        userService.removePrestasi(user.getUid(), prestasi).addOnCompleteListener(task -> {
            activity.showLoading(false);
            activity.showRemovedItem(prestasi);
        });
    }

    public void updatePrestasi(Prestasi prestasi){
        userService.updatePrestasi(user.getUid(), prestasi).addOnCompleteListener(
            task -> activity.showLoading(false));
    }
}
