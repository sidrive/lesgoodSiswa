package com.lesgood.siswa.ui.complete_order;


import com.lesgood.siswa.base.annotation.ActivityScope;

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
