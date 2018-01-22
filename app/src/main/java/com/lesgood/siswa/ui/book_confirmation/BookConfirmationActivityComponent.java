package com.lesgood.siswa.ui.book_confirmation;

import com.lesgood.siswa.base.annotation.ActivityScope;

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
