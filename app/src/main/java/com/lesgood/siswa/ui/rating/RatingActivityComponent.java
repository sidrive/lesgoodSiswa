package com.lesgood.siswa.ui.rating;

import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ikun on 17/11/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                RatingActivityModule.class
        }
)

public interface RatingActivityComponent {
    /*RatingActivity inject(RatingActivity activity);*/
}
