package com.lesgood.app.ui.book_2;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.TimeSchedule;
import com.lesgood.app.data.remote.FirebaseImageService;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.UserService;
import com.lesgood.app.ui.book.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Agus on 8/10/17.
 */

public class BookActivityPresenter implements BasePresenter {
    BookActivity activity;
    UserService userService;
    OrderService orderService;
    Guru user;
    List<Event> eventList;
    @Override
    public void subscribe() {
        getSchedule();
    }

    @Override
    public void unsubscribe() {

    }

    public BookActivityPresenter(BookActivity activity, UserService userService, OrderService orderService, Guru user){
        this.activity = activity;
        this.user = user;
        this.userService = userService;
        this.orderService = orderService;
        this.eventList = new ArrayList<>();
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
        orderService.saveOrder(order).addOnCompleteListener(task -> activity.successOrdering()).addOnFailureListener(e -> {
            activity.showLoading(false);
            Toast.makeText(activity, "Gagal memproses pesanan", Toast.LENGTH_SHORT).show();
        });
    }
    public void getSchedule(){

        userService.getUserSchedule(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("onChildAdded", "BookActivityPresenter" + dataSnapshot.toString());
                TimeSchedule schedule = dataSnapshot.getValue(TimeSchedule.class);
                activity.setEvent(schedule);
                /*String date = dataSnapshot.getKey();
                activity.setEvent(date);*/
                /*List<Event> eventList = new ArrayList<>();
                eventList.add(new Event(Color.argb(252, 200, 64, 1),Long.parseLong(date),"Aviable"+new Date(Long.parseLong(date))));*/

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               /* String date = dataSnapshot.getKey();
                activity.setEvent(date);*/
                /*List<Event> eventList = new ArrayList<>();
                eventList.add(new Event(Color.argb(252, 200, 64, 1),Long.parseLong(date),"Aviable"+new Date(Long.parseLong(date))));*/

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "BookActivityPresenter" + databaseError);
            }
        });
    }

}
