package com.lesgood.app.ui.book_2;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.remote.FirebaseImageService;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.ui.book.*;


/**
 * Created by Agus on 8/10/17.
 */

public class BookActivityPresenter implements BasePresenter {
    BookActivity activity;
    UserService userService;
    OrderService orderService;
    Guru user;


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public BookActivityPresenter(BookActivity activity, UserService userService, OrderService orderService, Guru user){
        this.activity = activity;
        this.user = user;
        this.userService = userService;
        this.orderService = orderService;
    }

    public void getGuruSkill(String uid, String code){
        userService.getUserSkill(uid, code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Skill skill = dataSnapshot.getValue(Skill.class);
                if (skill != null){
                    activity.initSkill(skill);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.showLoading(false);
            }
        });
    }

    public void saveOrder(Order order){
        orderService.saveOrder(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                activity.successOrdering();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                activity.showLoading(false);
                Toast.makeText(activity, "Gagal memproses pesanan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
