package com.lesgood.app.ui.main;


import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 4/16/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                MainActivityModule.class
        }
)
public interface MainActivityComponent {
    MainActivity inject(MainActivity activity);
}
