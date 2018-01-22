package com.lesgood.siswa.ui.setting;


import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/21/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                SettingActivityModule.class
        }
)
public interface SettingActivityComponent {
    SettingActivity inject(SettingActivity activity);
}
