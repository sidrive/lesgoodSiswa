package com.lesgood.app.ui.detail_teacher;



import com.lesgood.app.base.annotation.UserScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 2/28/17.
 */

@UserScope
@Subcomponent(
        modules = {
                DetailTeacherActivityModule.class
        }
)
public interface DetailTeacherActivityComponent {
    DetailTeacherActivity inject(DetailTeacherActivity activity);
}
