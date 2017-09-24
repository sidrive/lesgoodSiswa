package com.lesgood.app.ui.order;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.OrderService;


/**
 * Created by Agus on 5/2/17.
 */

public class PlaceHolderFragmentPresenter implements BasePresenter {
    PlaceholderFragment fragment;
    OrderService orderService;
    DatabaseReference databaseRef;
    User user;
    ChildEventListener orderEventListener;

    public PlaceHolderFragmentPresenter(PlaceholderFragment fragment, OrderService orderService, User user){
        this.fragment = fragment;
        this.orderService = orderService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (orderEventListener != null) databaseRef.removeEventListener(orderEventListener);
    }

    public void getOrders(final String status){
        orderEventListener = orderService.getOrders().orderByChild("uid").equalTo(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order order =dataSnapshot.getValue(Order.class);
                if (order != null) {
                    if (status.equalsIgnoreCase("waiting")){
                        if (order.getStatus().equalsIgnoreCase("pending_guru") || order.getStatus().equalsIgnoreCase("pending_murid") || order.getStatus().equalsIgnoreCase("pending")){
                            fragment.showAddedOrder(order);
                        }
                    }

                    if (status.equalsIgnoreCase("complete")){
                        if (order.getStatus().equalsIgnoreCase("success")){
                            fragment.showAddedOrder(order);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Order order =dataSnapshot.getValue(Order.class);
                if (order != null) if (order.getStatus().equals(status)) fragment.showChangedOrder(order);
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
}
