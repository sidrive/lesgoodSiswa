package com.lesgood.app.ui.search;

import com.lesgood.app.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/11/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                SearchActivityModule.class
        }
)
public interface SearchActivityComponent {
    SearchActivity inject(SearchActivity activity);
}
