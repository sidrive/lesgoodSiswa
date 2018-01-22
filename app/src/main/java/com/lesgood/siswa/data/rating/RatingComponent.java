package com.lesgood.siswa.data.rating;

import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.ui.rating.RatingActivityComponent;
import com.lesgood.siswa.ui.rating.RatingActivityModule;

import dagger.Subcomponent;
/**
 * Created by ikun on 20/11/17.
 */

@UserScope
@Subcomponent(
        modules = {
                RatingModule.class
        }
)

public interface RatingComponent {
    RatingActivityComponent plus(RatingActivityModule activityModule);
}
