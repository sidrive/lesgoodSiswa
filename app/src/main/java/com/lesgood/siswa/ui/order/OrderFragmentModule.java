package com.lesgood.siswa.ui.order;



import com.lesgood.siswa.base.annotation.FragmentScope;
import com.lesgood.siswa.data.model.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 3/2/17.
 */

@Module
public class OrderFragmentModule {
    OrderFragment fragment;

    public OrderFragmentModule(OrderFragment fragment){
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    OrderFragment provideOrderFragment(){
        return fragment;
    }

    @FragmentScope
    @Provides
    OrderPresenter provideOrderPresenter(User user){
        return new OrderPresenter(fragment, user);
    }
}
