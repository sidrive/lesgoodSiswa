package com.lesgood.siswa.ui.skill;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.model.Skill;
import com.lesgood.siswa.data.remote.UserService;


/**
 * Created by Agus on 5/10/17.
 */

public class SkillPresenter implements BasePresenter {

    SkillActivity activity;
    UserService userService;
    DatabaseReference databaseRef;
    ChildEventListener childEventListener;
    Guru user;

    public SkillPresenter(SkillActivity activity, UserService userService, Guru user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public void subscribe() {
        if (user != null){
            getSkills();
        }
    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseRef.removeEventListener(childEventListener);
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

    public void deleteSkill(Skill skill){
        userService.removeSkill(user.getUid(), skill).addOnCompleteListener(
            task -> activity.showLoading(false));
    }
}