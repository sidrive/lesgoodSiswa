package com.lesgood.app.ui.order_detail;


import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/3/17.
 */

@Module
public class OrderDetailActivityModule {
    OrderDetailActivity activity;

    public OrderDetailActivityModule(OrderDetailActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    OrderDetailActivity provideOrderDetailActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    OrderDetailPresenter provideOrderDetailPresenter(OrderService orderService, Order order, UserService userService, User user){
        return new OrderDetailPresenter(activity, orderService, order, userService, user);
    }

}
