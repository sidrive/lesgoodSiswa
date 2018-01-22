package com.lesgood.siswa.data.order_detail;


import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.ui.complete_order.CompleteOrderActivityComponent;
import com.lesgood.siswa.ui.complete_order.CompleteOrderActivityModule;
import com.lesgood.siswa.ui.order_detail.OrderDetailActivityComponent;
import com.lesgood.siswa.ui.order_detail.OrderDetailActivityModule;

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
    CompleteOrderActivityComponent plus(CompleteOrderActivityModule activityModule);
}
