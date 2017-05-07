package com.lesgood.app.ui.splash;



import com.lesgood.app.base.annotation.ActivityScope;

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
