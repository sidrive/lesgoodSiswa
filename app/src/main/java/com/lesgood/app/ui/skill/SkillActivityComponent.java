package com.lesgood.app.ui.skill;


import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/10/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                SkillActivityModule.class
        }
)
public interface SkillActivityComponent {
    SkillActivity inject(SkillActivity activity);
}
