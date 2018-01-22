package com.lesgood.siswa.ui.detail_teacher;




import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 2/28/17.
 */

@Module
public class DetailTeacherActivityModule {
    DetailTeacherActivity fragment;

    public DetailTeacherActivityModule(DetailTeacherActivity fragment){
        this.fragment = fragment;
    }

    @UserScope
    @Provides
    DetailTeacherActivity provideProfileFragment(){
        return fragment;
    }

    @UserScope
    @Provides
    DetailTeacherPresenter provideProfilePresenter(Guru user, UserService userService){
        return new DetailTeacherPresenter(fragment, user, userService);
    }
}
