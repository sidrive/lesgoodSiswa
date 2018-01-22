package com.lesgood.siswa.ui.order;


import com.lesgood.siswa.base.annotation.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Agus on 3/15/17.
 */

@FragmentScope
@Subcomponent(
        modules = {
                PlaceholderFragmentModule.class
        }
)
public interface PlaceholderFragmentComponent {
    PlaceholderFragment inject(PlaceholderFragment fragment);
}
