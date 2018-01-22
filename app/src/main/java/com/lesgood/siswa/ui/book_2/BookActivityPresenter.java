package com.lesgood.siswa.ui.book_2;


import android.util.Log;
import android.widget.Toast;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.model.HistoryOders;
import com.lesgood.siswa.data.model.Invoices;
import com.lesgood.siswa.data.model.Order;
import com.lesgood.siswa.data.model.Skill;
import com.lesgood.siswa.data.model.TimeSchedule;
import com.lesgood.siswa.data.remote.OrderService;
import com.lesgood.siswa.data.remote.UserService;
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
                Log.e("onDataChange", "BookActivityPresenter" + dataSnapshot.toString());
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
    public void getSchedule(String uid){

        userService.getUserSchedule(uid).addChildEventListener(new ChildEventListener() {
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
    public void getOrderById(String oid){
        orderService.getDetailsOrder(oid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    Order order = dataSnapshot.getValue(Order.class);
                    activity.updateUiForCangeTeacher(order);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "BookActivityPresenter" + databaseError.getMessage());
            }
        });
    }

    public void saveInvoice(Invoices invoices) {
        orderService.createInvoice(invoices)
            .addOnCompleteListener(task -> {
                Log.e("saveInvoice", "task.isSuccessful()" + task.isSuccessful());
            })
            .addOnFailureListener(e -> {
                Log.e("saveInvoice", "BookActivityPresenter " + e.getMessage());
            });
    }

    public void createHistoryOrder(HistoryOders historyOders) {
        orderService.createHistoryOrder(historyOders.getOid()).setValue(historyOders)
            .addOnFailureListener(e -> {
                Log.e("createHistoryOrder", "BookActivityPresenter" + e.getMessage());
            })
            .addOnCompleteListener(task -> {
                Log.e("createHistoryOrder", "BookActivityPresenter" + task.isSuccessful());
                if (task.isSuccessful()){
                    activity.successOrdering();
                }
            });
    }
}
