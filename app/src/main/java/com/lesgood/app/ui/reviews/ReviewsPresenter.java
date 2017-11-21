package com.lesgood.app.ui.reviews;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.app.data.model.Reviews;

import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.util.AppUtils;

/**
 * Created by Agus on 6/1/17.
 */

public class ReviewsPresenter implements BasePresenter {
    ReviewsActivity activity;
    UserService userService;
    Guru user;
    DatabaseReference databaseRef;
    ChildEventListener childEventListener;

    public ReviewsPresenter(ReviewsActivity activity, UserService userService, Guru user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {
        if (user != null){
            getReviews();
        }
    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseRef.removeEventListener(childEventListener);
    }

    public void getReviews(){
        
        childEventListener = userService.getUserReviews(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Reviews reviews = dataSnapshot.getValue(Reviews.class);
                if (reviews != null){

                    activity.showAddedItem(reviews);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Reviews reviews = dataSnapshot.getValue(Reviews.class);
                if (reviews != null){
                    activity.showChangedItem(reviews);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Reviews reviews = dataSnapshot.getValue(Reviews.class);
                if (reviews != null){
                    activity.showRemovedItem(reviews);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               /* AppUtils.showToas(activity.getApplicationContext(),databaseError.getMessage());*/
            }
        });
    }
}
