package com.lesgood.app.data.user;



import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.model.User;

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

}
