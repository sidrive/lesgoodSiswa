package com.lesgood.app.ui.book;

import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.FirebaseImageService;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 6/14/17.
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
    BookActivityPresenter provideBookActivityPresenter(UserService userService, FirebaseImageService firebaseImageService, Guru user){
        return new BookActivityPresenter(activity, userService, firebaseImageService, user);
    }

    @ActivityScope
    @Provides
    PertemuanAdapter providePertemuanAdapter(){
        return new PertemuanAdapter(activity);
    }

    @ActivityScope
    @Provides
    SkillAdapter provideSkillAdapter(){
        return new SkillAdapter(activity);
    }
}
