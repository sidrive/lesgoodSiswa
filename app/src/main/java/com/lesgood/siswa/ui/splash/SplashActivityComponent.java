package com.lesgood.siswa.ui.splash;



import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}
