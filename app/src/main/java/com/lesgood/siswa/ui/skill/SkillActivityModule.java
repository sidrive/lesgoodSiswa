package com.lesgood.siswa.ui.skill;


import com.lesgood.siswa.base.annotation.ActivityScope;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Agus on 5/10/17.
 */
@Module
public class SkillActivityModule {
    SkillActivity activity;

    public SkillActivityModule(SkillActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SkillActivity provideSkillActivity(){
        return activity = activity;
    }

    @ActivityScope
    @Provides
    SkillPresenter provideSkillPresenter(UserService userService, Guru user){
        return new SkillPresenter(activity, userService, user);
    }

    @ActivityScope
    @Provides
    SkillAdapter provideSkillAdapter(){
        return new SkillAdapter(activity);
    }
}
