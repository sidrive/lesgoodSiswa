package com.lesgood.app.data.detail_teacher;

import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.book.BookComponent;
import com.lesgood.app.data.book.BookModule;
import com.lesgood.app.ui.book.BookActivityComponent;
import com.lesgood.app.ui.book.BookActivityModule;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivity;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivityComponent;
import com.lesgood.app.ui.detail_teacher.DetailTeacherActivityModule;
import com.lesgood.app.ui.pengalaman.PengalamanActivityComponent;
import com.lesgood.app.ui.pengalaman.PengalamanActivityModule;
import com.lesgood.app.ui.prestasi.PrestasiActivityComponent;
import com.lesgood.app.ui.prestasi.PrestasiActivityModule;
import com.lesgood.app.ui.reviews.ReviewsActivityComponent;
import com.lesgood.app.ui.reviews.ReviewsActivityModule;
import com.lesgood.app.ui.skill.SkillActivityComponent;
import com.lesgood.app.ui.skill.SkillActivityModule;

import dagger.Subcomponent;

/**
 * Created by Agus on 5/11/17.
 */

@UserScope
@Subcomponent(
        modules = {
                DetailTeacherModule.class
        }
)
public interface DetailTeacherComponent {
    DetailTeacherActivityComponent plus(DetailTeacherActivityModule activityModule);

    SkillActivityComponent plus(SkillActivityModule activityModule);

    PrestasiActivityComponent plus(PrestasiActivityModule activityModule);

    PengalamanActivityComponent plus(PengalamanActivityModule activityModule);

    ReviewsActivityComponent plus(ReviewsActivityModule activityModule);

    BookActivityComponent plus(BookActivityModule activityModule);

    BookComponent plus(BookModule bookModule);
}
