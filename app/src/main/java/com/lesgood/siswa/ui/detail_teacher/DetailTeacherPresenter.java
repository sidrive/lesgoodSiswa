package com.lesgood.siswa.ui.detail_teacher;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;

/**
 * Created by Agus on 2/28/17.
 */

public class DetailTeacherPresenter implements BasePresenter {
    DetailTeacherActivity fragment;
    Guru user;
    UserService userService;

    public DetailTeacherPresenter(DetailTeacherActivity fragment, Guru user,
        UserService userService ){
        this.fragment = fragment;
        this.user = user;
        this.userService = userService;

    }

    @Override
    public void subscribe() {
        if (user != null){
            getUserAbout(user.getUid());
        }

    }

    @Override
    public void unsubscribe() {

    }

    public void getUserAbout(String uid){
        userService.getUserAbout(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null){
                    fragment.initAbout(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "DetailTeacherPresenter" + databaseError.getDetails());
            }
        });
    }


}
