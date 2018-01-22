package com.lesgood.siswa.ui.complete_order;



import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.base.config.DefaultConfig;
import com.lesgood.siswa.data.model.Order;
import com.lesgood.siswa.data.remote.OrderService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Agus on 5/13/17.
 */

@Module
public class CompleteOrderActivityModule {
    CompleteOrderActivity activity;

    public CompleteOrderActivityModule(CompleteOrderActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    CompleteOrderActivity provideCompleteOrderActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    CompleteOrderPresenter provideCompleteOrderPresenter(OrderService orderService, Order order, Retrofit retrofit){
        return new CompleteOrderPresenter(activity, orderService, order, retrofit);
    }

    @Provides
    @ActivityScope
    DefaultConfig provideDefaultConfig() {
        return new DefaultConfig(activity);
    }
}
