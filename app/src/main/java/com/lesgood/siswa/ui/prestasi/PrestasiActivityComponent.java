package com.lesgood.siswa.ui.prestasi;


import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 6/1/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                PrestasiActivityModule.class
        }
)
public interface PrestasiActivityComponent {
    PrestasiActivity inject(PrestasiActivity activity);
}
