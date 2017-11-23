package com.lesgood.app.ui.order;


import com.lesgood.app.base.annotation.FragmentScope;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.OrderService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Agus on 3/15/17.
 */

@Module
public class PlaceholderFragmentModule {
    PlaceholderFragment fragment;

    public PlaceholderFragmentModule(PlaceholderFragment fragment){
        this.fragment = fragment;
    }

    @FragmentScope
    @Provides
    PlaceholderFragment providePlaceholderFragment(){
        return fragment;
    }

    @FragmentScope
    @Provides
    PlaceHolderFragmentPresenter providePlaceHolderFragmentPresenter(User user, OrderService orderService, Retrofit retrofit){
        return new PlaceHolderFragmentPresenter(fragment, orderService, user,retrofit);
    }


}
