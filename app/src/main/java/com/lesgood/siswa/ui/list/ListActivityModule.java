package com.lesgood.siswa.ui.list;

import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.FirebaseImageService;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/11/17.
 */

@Module
public class ListActivityModule {
    ListActivity activity;
    public ListActivityModule(ListActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    ListActivity provideListActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    ListPresenter provideListPresenter(UserService userService, User user ){
        return new ListPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    ListAdapter provideListAdapter(UserService userService, FirebaseImageService firebaseImageService){
        return new ListAdapter(activity, userService, firebaseImageService);
    }
}
