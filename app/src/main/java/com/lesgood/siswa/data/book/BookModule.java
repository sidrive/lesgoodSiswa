package com.lesgood.siswa.data.book;

import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.model.Order;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 8/13/17.
 */
@Module
public class BookModule {
    Order order;
    public BookModule(Order order){
        this.order = order;

    }

    @Provides
    @UserScope
    Order provideOrder(){
        return order;
    }
}
