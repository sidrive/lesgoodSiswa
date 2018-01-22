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
public class ListGantiGuruActivityModule {
    ListGantiGuruActivity activity;
    public ListGantiGuruActivityModule(ListGantiGuruActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    ListGantiGuruActivity provideListActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    ListGantiGuruActivityPresenter provideListPresenter(UserService userService, User user ){
        return new ListGantiGuruActivityPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    ListGantiGuruAdapter provideListAdapter(UserService userService, FirebaseImageService firebaseImageService){
        return new ListGantiGuruAdapter(activity, userService, firebaseImageService);
    }
}
