package com.lesgood.siswa.ui.order;


import com.lesgood.siswa.base.annotation.FragmentScope;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.OrderService;

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
