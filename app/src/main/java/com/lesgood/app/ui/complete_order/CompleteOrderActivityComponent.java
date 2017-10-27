package com.lesgood.app.ui.complete_order;


import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/13/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                CompleteOrderActivityModule.class
        }
)
public interface CompleteOrderActivityComponent {
    CompleteOrderActivity inject(CompleteOrderActivity activity);
}
