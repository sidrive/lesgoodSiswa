package com.lesgood.siswa.data.user;



import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.CategoryService;
import com.lesgood.siswa.data.remote.OrderService;

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
