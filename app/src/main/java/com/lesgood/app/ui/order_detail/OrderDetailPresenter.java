package com.lesgood.app.ui.order_detail;


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
}
