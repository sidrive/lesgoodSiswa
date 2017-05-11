package com.lesgood.app.data.detail_teacher;

import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivity;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivityComponent;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivityModule;
import com.lesgood.app.ui.skill.SkillActivityComponent;
import com.lesgood.app.ui.skill.SkillActivityModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/11/17.
 */

@UserScope
@Subcomponent(
        modules = {
                DetailTeacherModule.class
        }
)
public interface DetailTeacherComponent {
    DetailTeacherActivityComponent plus(DetailTeacherActivityModule activityModule);

    SkillActivityComponent plus(SkillActivityModule activityModule);
}
