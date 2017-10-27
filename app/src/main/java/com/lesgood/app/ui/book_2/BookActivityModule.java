package com.lesgood.app.ui.book_2;

import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 8/10/17.
 */

@Module
public class BookActivityModule {
    BookActivity activity;

    public BookActivityModule(BookActivity activity){
        this.activity = activity;

    }

    @ActivityScope
    @Provides
    BookActivity provideBookActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    BookActivityPresenter provideBookActivityPresenter(UserService userService, OrderService orderService, Guru user){
        return new BookActivityPresenter(activity, userService, orderService, user);
    }
}
