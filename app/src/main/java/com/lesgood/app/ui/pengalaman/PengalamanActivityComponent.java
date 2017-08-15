package com.lesgood.app.ui.pengalaman;


import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 6/1/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                PengalamanActivityModule.class
        }
)
public interface PengalamanActivityComponent {
    PengalamanActivity inject(PengalamanActivity activity);
}
