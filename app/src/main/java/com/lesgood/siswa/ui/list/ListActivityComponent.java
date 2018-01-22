package com.lesgood.siswa.ui.list;

import com.lesgood.siswa.base.annotation.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/11/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                ListActivityModule.class
        }
)
public interface ListActivityComponent {
    ListActivity inject(ListActivity activity);
}
