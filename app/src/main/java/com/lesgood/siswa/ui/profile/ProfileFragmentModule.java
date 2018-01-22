package com.lesgood.siswa.ui.profile;



import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 2/28/17.
 */

@Module
public class ProfileFragmentModule {
    ProfileFragment fragment;

    public ProfileFragmentModule(ProfileFragment fragment){
        this.fragment = fragment;
    }

    @UserScope
    @Provides
    ProfileFragment provideProfileFragment(){
        return fragment;
    }

    @UserScope
    @Provides
    ProfilePresenter provideProfilePresenter(User user, UserService userService){
        return new ProfilePresenter(fragment, user, userService);
    }
}
