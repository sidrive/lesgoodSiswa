package com.lesgood.siswa.ui.order_detail;



import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/3/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                OrderDetailActivityModule.class
        }
)
public interface OrderDetailActivityComponent {
    OrderDetailActivity inject(OrderDetailActivity activity);
}
