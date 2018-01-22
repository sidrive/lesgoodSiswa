package com.lesgood.siswa.ui.login;



import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 2/27/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)

public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}
