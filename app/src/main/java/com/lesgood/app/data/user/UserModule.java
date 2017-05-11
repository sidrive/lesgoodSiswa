package com.lesgood.app.data.user;



import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.CategoryService;
import com.lesgood.app.data.remote.OrderService;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    User user;

    public UserModule(User user) {
        this.user = user;
    }

    @Provides
    @UserScope
    User provideUser() {
        return user;
    }

    @Provides
    @UserScope
    CategoryService provideCategoryService(){
        return new CategoryService();
    }

    @Provides
    @UserScope
    OrderService provideOrderService(){
        return new OrderService();
    }

}
