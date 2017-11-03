package com.lesgood.app.ui.order_detail;


import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.remote.OrderService;

/**
 * Created by Agus on 5/3/17.
 */

public class OrderDetailPresenter implements BasePresenter {
    OrderDetailActivity activity;
    OrderService orderService;
    Order order;

    public OrderDetailPresenter(OrderDetailActivity activity, OrderService orderService, Order order){
        this.activity = activity;
        this.orderService = orderService;
        this.order = order;
    }

    @Override
    public void subscribe() {
        if (order != null){
        }
    }

    @Override
    public void unsubscribe() {

    }

    public void acceptOrder(final Order order){
        orderService.approveOrder(order.getOid()).addOnCompleteListener(
            task -> activity.successAction(order)).addOnFailureListener(e -> activity.successAction(order));
    }

    public void declineOrder(final Order order){
        orderService.declineOrder(order.getOid()).addOnFailureListener(e -> {

        }).addOnCompleteListener(task -> activity.successAction(order));
    }
}
