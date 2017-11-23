package com.lesgood.app.ui.detail_teacher;




import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.UserService;

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
