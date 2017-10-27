package com.lesgood.app.data.book;

import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.detail_teacher.DetailTeacherModule;
import com.lesgood.app.ui.book_2.BookActivityComponent;
import com.lesgood.app.ui.book_2.BookActivityModule;

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
