package com.lesgood.app.ui.book_2;

import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 8/10/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                BookActivityModule.class
        }
)
public interface BookActivityComponent {
    BookActivity inject(BookActivity activity);
}
