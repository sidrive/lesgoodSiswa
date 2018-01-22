package com.lesgood.siswa.data.detail_teacher;

import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.book.BookComponent;
import com.lesgood.siswa.data.book.BookModule;
import com.lesgood.siswa.ui.book.BookActivityComponent;
import com.lesgood.siswa.ui.book.BookActivityModule;
import com.lesgood.siswa.ui.detail_teacher.DetailTeacherActivityComponent;
import com.lesgood.siswa.ui.detail_teacher.DetailTeacherActivityModule;
import com.lesgood.siswa.ui.pengalaman.PengalamanActivityComponent;
import com.lesgood.siswa.ui.pengalaman.PengalamanActivityModule;
import com.lesgood.siswa.ui.prestasi.PrestasiActivityComponent;
import com.lesgood.siswa.ui.prestasi.PrestasiActivityModule;
import com.lesgood.siswa.ui.reviews.ReviewsActivityComponent;
import com.lesgood.siswa.ui.reviews.ReviewsActivityModule;
import com.lesgood.siswa.ui.skill.SkillActivityComponent;
import com.lesgood.siswa.ui.skill.SkillActivityModule;

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
