package com.lesgood.siswa.ui.pengalaman;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.model.Pengalaman;
import com.lesgood.siswa.data.remote.UserService;


/**
 * Created by Agus on 6/1/17.
 */

public class PengalamanPresenter implements BasePresenter {

    PengalamanActivity activity;
    UserService userService;
    Guru user;
    ChildEventListener childEventListener;
    DatabaseReference databaseReference;

    public PengalamanPresenter(PengalamanActivity activity, UserService userService, Guru user){
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
        childEventListener = userService.getUserPengalaman(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pengalaman pengalaman = dataSnapshot.getValue(Pengalaman.class);
                if (pengalaman != null){
                    activity.showAddedItem(pengalaman);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Pengalaman pengalaman = dataSnapshot.getValue(Pengalaman.class);
                if (pengalaman != null){
                    activity.showChangedItem(pengalaman);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Pengalaman pengalaman = dataSnapshot.getValue(Pengalaman.class);
                if (pengalaman != null){
                    activity.showRemovedItem(pengalaman);
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

    public void deletePrestasi(final Pengalaman pengalaman){
        userService.removePengalaman(user.getUid(), pengalaman).addOnCompleteListener(task -> {
            activity.showLoading(false);
            activity.showRemovedItem(pengalaman);
        });
    }

    public void updatePrestasi(Pengalaman pengalaman){
        userService.updatePengalaman(user.getUid(), pengalaman).addOnCompleteListener(
            task -> activity.showLoading(false));
    }
}
