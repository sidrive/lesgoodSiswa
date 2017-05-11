package com.lesgood.app.data.main;

import com.lesgood.app.base.annotation.MainScope;
import com.lesgood.app.data.order_detail.OrderDetailComponent;
import com.lesgood.app.data.order_detail.OrderDetailModule;
import com.lesgood.app.ui.home.HomeFragmentComponent;
import com.lesgood.app.ui.home.HomeFragmentModule;
import com.lesgood.app.ui.order.OrderFragmentComponent;
import com.lesgood.app.ui.order.OrderFragmentModule;
import com.lesgood.app.ui.order.PlaceholderFragmentComponent;
import com.lesgood.app.ui.order.PlaceholderFragmentModule;
import com.lesgood.app.ui.profile.ProfileFragmentComponent;
import com.lesgood.app.ui.profile.ProfileFragmentModule;

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
