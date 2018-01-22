package com.lesgood.siswa.data.book;

import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.ui.book_2.BookActivityComponent;
import com.lesgood.siswa.ui.book_2.BookActivityModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 8/13/17.
 */
@UserScope
@Subcomponent(
        modules = {
                BookModule.class
        }
)
public interface BookComponent {
    BookActivityComponent plus(BookActivityModule activityModule);
}
