package com.lesgood.siswa.ui.pengalaman;


import com.lesgood.siswa.base.annotation.ActivityScope;

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
