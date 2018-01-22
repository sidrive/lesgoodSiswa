package com.lesgood.siswa.ui.order;




import com.lesgood.siswa.base.annotation.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/2/17.
 */

@FragmentScope
@Subcomponent(
        modules = {
                OrderFragmentModule.class
        }
)
public interface OrderFragmentComponent {
    OrderFragment inject(OrderFragment fragment);
}
