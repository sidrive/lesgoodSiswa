package com.lesgood.app.data.rating;

import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.ui.rating.RatingActivity;
import com.lesgood.app.ui.rating.RatingActivityComponent;
import com.lesgood.app.ui.rating.RatingActivityModule;

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
