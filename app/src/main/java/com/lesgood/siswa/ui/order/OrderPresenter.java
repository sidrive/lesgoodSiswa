package com.lesgood.siswa.ui.order;


import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.User;

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
