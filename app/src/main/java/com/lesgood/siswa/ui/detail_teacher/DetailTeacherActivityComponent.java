package com.lesgood.siswa.ui.detail_teacher;



import com.lesgood.siswa.base.annotation.UserScope;

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
