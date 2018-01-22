package com.lesgood.siswa.ui.book_2;

import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.OrderService;
import com.lesgood.siswa.data.remote.UserService;

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
    @ActivityScope
    @Provides
    ScheduleAdapter provideSchduleAdapter (){
        return new ScheduleAdapter(activity);
    }
}
