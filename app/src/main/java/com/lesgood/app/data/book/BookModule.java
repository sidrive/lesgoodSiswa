package com.lesgood.app.data.book;

import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.model.HistoryOders;
import com.lesgood.app.data.model.Invoices;
import com.lesgood.app.data.model.Order;


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
