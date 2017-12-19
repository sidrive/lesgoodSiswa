package com.lesgood.app.ui.list;

import com.lesgood.app.base.annotation.ActivityScope;
import dagger.Subcomponent;

/**
 * Created by Agus on 5/11/17.
 */

@ActivityScope
@Subcomponent(
        modules = {
                ListGantiGuruActivityModule.class
        }
)
public interface ListGantiGuruActivityComponent {
  ListGantiGuruActivity inject(ListGantiGuruActivity activity);
}
