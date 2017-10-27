package com.lesgood.app.ui.prestasi;


import com.lesgood.app.base.annotation.ActivityScope;

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
