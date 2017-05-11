package com.lesgood.app.ui.search;

import com.lesgood.app.base.annotation.ActivityScope;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.CategoryService;
import com.lesgood.app.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/11/17.
 */

@Module
public class SearchActivityModule {
    SearchActivity activity;

    public SearchActivityModule(SearchActivity activity){
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    SearchActivity provideSearchActivity(){
        return activity;
    }

    @Provides
    @ActivityScope
    SearchPresenter provideSearchPresenter(UserService userService, CategoryService categoryService, User user){
        return new SearchPresenter(activity, userService, categoryService, user);
    }

}
