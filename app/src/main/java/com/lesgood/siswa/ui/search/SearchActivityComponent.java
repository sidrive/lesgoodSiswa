package com.lesgood.siswa.ui.search;

import com.lesgood.siswa.base.annotation.ActivityScope;

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
