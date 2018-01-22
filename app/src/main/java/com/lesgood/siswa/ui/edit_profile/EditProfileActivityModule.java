package com.lesgood.siswa.ui.edit_profile;


import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.remote.FirebaseImageService;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 4/20/17.
 */

@Module
public class EditProfileActivityModule {
    EditProfileActivity activity;

    public EditProfileActivityModule(EditProfileActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    EditProfilePresenter provideEditProfilePresenter(UserService userService, FirebaseImageService firebaseImageService){
        return new EditProfilePresenter(activity, userService, firebaseImageService);
    }
}
