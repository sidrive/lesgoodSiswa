package com.lesgood.app.data.detail_teacher;

import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/11/17.
 */

@Module
public class DetailTeacherModule {

    Guru guru;

    public DetailTeacherModule(Guru guru){
        this.guru = guru;
    }

    @UserScope
    @Provides
    Guru provideGuru(){
        return guru;
    }
}
