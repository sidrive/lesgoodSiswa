package com.lesgood.app.ui.profile;


import com.lesgood.app.base.annotation.UserScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 2/28/17.
 */

@UserScope
@Subcomponent(
        modules = {
                ProfileFragmentModule.class
        }
)
public interface ProfileFragmentComponent {
    ProfileFragment inject(ProfileFragment fragment);
}
