package com.lesgood.app.ui.book;

import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 6/14/17.
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
