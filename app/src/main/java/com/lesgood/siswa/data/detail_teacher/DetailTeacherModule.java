package com.lesgood.siswa.data.detail_teacher;

import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.model.Guru;

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
