package com.lesgood.siswa.ui.order_detail;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Invoices;
import com.lesgood.siswa.data.model.Order;
import com.lesgood.siswa.data.model.Pustaka;
import com.lesgood.siswa.data.model.Reviews;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.OrderService;
import com.lesgood.siswa.data.remote.UserService;

/**
 * Created by Agus on 5/3/17.
 */

public class OrderDetailPresenter implements BasePresenter {
    OrderDetailActivity activity;
    OrderService orderService;
    Order order;
    UserService userService;
    User user;

    public OrderDetailPresenter(OrderDetailActivity activity, OrderService orderService, Order order, UserService userService, User user){
        this.activity = activity;
        this.orderService = orderService;
        this.order = order;
        this.userService = userService;
        this.user   = user;

    }

    @Override
    public void subscribe() {
        if (order!=null){

        }

    }

    @Override
    public void unsubscribe() {

    }
    public void getDetailOrder(String orderId){
        orderService.getDetailsOrder(orderId).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    order = dataSnapshot.getValue(Order.class);
                    if (dataSnapshot!=null){
                        activity.init(order);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public void getDetailOrderWithOldOid(String orderId){
        orderService.getDetailsOrder(orderId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        order = dataSnapshot.getValue(Order.class);
                        if (dataSnapshot!=null){
                            activity.initwitholdid(order);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    public void updateSaldoGuru(String uid, int saldo){
        userService.getUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null){
                    User user = dataSnapshot.getValue(User.class);
                    setSaldoUser(uid,user.getSaldo(),saldo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setSaldoUser(String uid, int saldo, int saldo1) {

        int newSaldo = saldo+saldo1;
        userService.setSaldoUser(uid,newSaldo).addOnCompleteListener(task -> {
            Log.e("setSaldoUser", "OrderDetailPresenter" +task.isComplete());
        });
    }

    public void acceptOrder(final Order order){
        orderService.approveOrder(order.getOid()).addOnCompleteListener(
            task -> activity.successAction(order)).addOnFailureListener(e -> activity.successAction(order));
    }

    public void declineOrder(final Order order){
        orderService.declineOrder(order.getOid()).addOnCompleteListener(task -> {
            order.setStatus("cancel_murid");
            activity.successAction(order);
        }).addOnFailureListener(e -> activity.successAction(order));
    }

    public void updateReview(Reviews reviews){
        userService.updateReviews(order.getGid(), user.getUid(), reviews).addOnCompleteListener(
                task -> activity.showLoading(false));
    }
    public void absenLes(){
        if (order.getTotalPertemuan()>0){
            int pertemuan = order.getTotalPertemuan() -1 ;
            orderService.updateTotalPertemuan(order.getOid(),pertemuan)
                .addOnFailureListener(e -> {
                    Log.e("absenLes", "OrderDetailPresenter" + e.getMessage());
                }).addOnCompleteListener(task -> {
                    if (task.isComplete()){
                        getDetailOrder(order.getOid());
                        updateSaldoGuru(order.getGid(),order.getTarif());
                    }
            });
        }else if (order.getTotalPertemuan()==0){
            activity.showAddReviews();
        }
    }
    public void getPustaka(String code){
        orderService.getPusataka(code).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot!=null){
                    Pustaka pustaka = dataSnapshot.getValue(Pustaka.class);
                    activity.showPustakaLesgood(pustaka);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Pustaka pustaka = dataSnapshot.getValue(Pustaka.class);
                if (dataSnapshot!=null){
                    //activity.showOnChangePustakaLesgood(pustaka);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getDetailSiswa(String uid) {
        userService.getUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    User user = dataSnapshot.getValue(User.class);
                    activity.initDetailSiswa(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.showDialogError(databaseError.getMessage());
            }
        });
    }

    public void getDetailGuru(String gid) {
        userService.getUser(gid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    User user = dataSnapshot.getValue(User.class);
                    activity.initDetailGuru(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.showDialogError(databaseError.getMessage());
            }
        });
    }

    public void getDetailInvoice(String iid) {
        orderService.getInvoice(iid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    Invoices invoices = dataSnapshot.getValue(Invoices.class);
                    activity.initDetailInvoice(invoices);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                activity.showDialogError(databaseError.getMessage());
            }
        });
    }
}