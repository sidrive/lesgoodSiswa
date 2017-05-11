package com.lesgood.app.ui.list;

import com.lesgood.app.base.annotation.ActivityScope;

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
