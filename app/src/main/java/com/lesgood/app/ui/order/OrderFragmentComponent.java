package com.lesgood.app.ui.order;


import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/27/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                OrderFragmentModule.class
        }
)
public interface OrderFragmentComponent {
    OrderFragment inject(OrderFragment fragment);
}
