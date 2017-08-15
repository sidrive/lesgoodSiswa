package com.lesgood.app.ui.book_confirmation;

import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 8/10/17.
 */

@Module
public class BookConfirmationActivityModule {
    BookConfirmationActivity activity;

    public BookConfirmationActivityModule(BookConfirmationActivity activity){
        this.activity = activity;

    }

    @ActivityScope
    @Provides
    BookConfirmationActivity provideBookActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    BookConfirmationActivityPresenter provideBookActivityPresenter(UserService userService, Guru user){
        return new BookConfirmationActivityPresenter(activity, userService, user);
    }
}
