package com.lesgood.app.ui.book_2;


import android.util.Log;
import android.widget.Toast;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.TimeSchedule;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.UserService;
import java.util.ArrayList;
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
                if (dataSnapshot.getValue()!=null){
                    TimeSchedule schedule = dataSnapshot.getValue(TimeSchedule.class);
                    activity.setEvent(schedule);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               if (dataSnapshot.getValue()!=null){
                   TimeSchedule schedule = dataSnapshot.getValue(TimeSchedule.class);
                   activity.setEvent(schedule);
               }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    TimeSchedule schedule = dataSnapshot.getValue(TimeSchedule.class);
                    activity.setEvent(schedule);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue()!=null){
                    TimeSchedule schedule = dataSnapshot.getValue(TimeSchedule.class);
                    activity.setEvent(schedule);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "BookActivityPresenter" + databaseError);
            }
        });
    }

}
