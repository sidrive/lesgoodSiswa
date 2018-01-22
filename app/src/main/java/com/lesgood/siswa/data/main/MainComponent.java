package com.lesgood.siswa.data.main;

import com.lesgood.siswa.base.annotation.MainScope;
import com.lesgood.siswa.data.order_detail.OrderDetailComponent;
import com.lesgood.siswa.data.order_detail.OrderDetailModule;
import com.lesgood.siswa.ui.home.HomeFragmentComponent;
import com.lesgood.siswa.ui.home.HomeFragmentModule;
import com.lesgood.siswa.ui.order.OrderFragmentComponent;
import com.lesgood.siswa.ui.order.OrderFragmentModule;
import com.lesgood.siswa.ui.order.PlaceholderFragmentComponent;
import com.lesgood.siswa.ui.order.PlaceholderFragmentModule;
import com.lesgood.siswa.ui.profile.ProfileFragmentComponent;
import com.lesgood.siswa.ui.profile.ProfileFragmentModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/20/17.
 */

@MainScope
@Subcomponent(
        modules = {
                MainModule.class
        }
)
public interface MainComponent {

        ProfileFragmentComponent plus(ProfileFragmentModule fragmentModule);

        HomeFragmentComponent plus(HomeFragmentModule fragmentModule);

        OrderFragmentComponent plus(OrderFragmentModule fragmentModule);

        PlaceholderFragmentComponent plus(PlaceholderFragmentModule fragmentModule);

        OrderDetailComponent plus(OrderDetailModule orderDetailModule);
}
