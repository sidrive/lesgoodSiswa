package com.lesgood.app.ui.order;


import com.lesgood.app.base.BasePresenter;

/**
 * Created by Agus on 4/27/17.
 */

public class OrderPresenter implements BasePresenter {
    OrderFragment fragment;

    public OrderPresenter(OrderFragment fragment){
        this.fragment = fragment;

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
