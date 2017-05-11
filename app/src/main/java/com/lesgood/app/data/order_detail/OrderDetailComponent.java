package com.lesgood.app.data.order_detail;


import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.ui.order_detail.OrderDetailActivityComponent;
import com.lesgood.app.ui.order_detail.OrderDetailActivityModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/3/17.
 */

@UserScope
@Subcomponent(
        modules = {
                OrderDetailModule.class
        }
)
public interface OrderDetailComponent {
    OrderDetailActivityComponent plus(OrderDetailActivityModule activityModule);
}
