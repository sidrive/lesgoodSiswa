package com.lesgood.app.ui.order;


import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.User;

/**
 * Created by Agus on 3/2/17.
 */

public class OrderPresenter implements BasePresenter {
    OrderFragment fragment;
    User user;

    public OrderPresenter(OrderFragment fragment, User user){
        this.fragment = fragment;
        this.user = user;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }



}
