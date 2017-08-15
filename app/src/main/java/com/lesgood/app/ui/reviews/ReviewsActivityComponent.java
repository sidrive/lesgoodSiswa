package com.lesgood.app.ui.reviews;



import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 6/1/17.
 */
@ActivityScope
@Subcomponent(
        modules = {
                ReviewsActivityModule.class
        }
)
public interface ReviewsActivityComponent {
    ReviewsActivity inject(ReviewsActivity activity);
}
