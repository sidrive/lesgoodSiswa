package com.lesgood.app.ui.book;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.FirebaseImageService;
import com.lesgood.app.data.remote.UserService;

import java.util.Date;

/**
 * Created by Agus on 6/14/17.
 */

public class BookActivityPresenter implements BasePresenter {

    BookActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;
    Guru user;
    ChildEventListener childEventListener;
    DatabaseReference databaseReference;

    public BookActivityPresenter(BookActivity activity, UserService userService, FirebaseImageService firebaseImageService, Guru user){
        this.activity = activity;
        this.user = user;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public void subscribe() {
        getSkills();
    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseReference.removeEventListener(childEventListener);
    }

    public void getSkills(){
        childEventListener = userService.getUserSkills(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Skill skill = dataSnapshot.getValue(Skill.class);
                if (skill != null){
                    activity.showAddedItem(skill);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Skill skill = dataSnapshot.getValue(Skill.class);
                if (skill != null){
                    activity.showChangedItem(skill);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Skill skill = dataSnapshot.getValue(Skill.class);
                if (skill != null){
                    activity.showRemovedItem(skill);
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

    public StorageReference getThumbAvatar(String uid){
        return firebaseImageService.getImageRefThumb(uid);
    }
}
