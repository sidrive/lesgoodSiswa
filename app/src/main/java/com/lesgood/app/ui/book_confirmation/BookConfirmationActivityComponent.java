package com.lesgood.app.ui.book_confirmation;

import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 8/10/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                BookConfirmationActivityModule.class
        }
)
public interface BookConfirmationActivityComponent {
    BookConfirmationActivity inject(BookConfirmationActivity activity);
}
