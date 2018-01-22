package com.lesgood.siswa.data.user;



import com.lesgood.siswa.base.annotation.UserScope;
import com.lesgood.siswa.data.detail_teacher.DetailTeacherComponent;
import com.lesgood.siswa.data.detail_teacher.DetailTeacherModule;
import com.lesgood.siswa.data.main.MainComponent;
import com.lesgood.siswa.data.main.MainModule;
import com.lesgood.siswa.ui.brief.BriefActivityComponent;
import com.lesgood.siswa.ui.brief.BriefActivityModule;
import com.lesgood.siswa.ui.edit_profile.EditProfileActivityComponent;
import com.lesgood.siswa.ui.edit_profile.EditProfileActivityModule;
import com.lesgood.siswa.ui.list.ListActivityComponent;
import com.lesgood.siswa.ui.list.ListActivityModule;
import com.lesgood.siswa.ui.list.ListGantiGuruActivityComponent;
import com.lesgood.siswa.ui.list.ListGantiGuruActivityModule;
import com.lesgood.siswa.ui.main.MainActivityComponent;
import com.lesgood.siswa.ui.main.MainActivityModule;
import com.lesgood.siswa.ui.rating.RatingActivityComponent;
import com.lesgood.siswa.ui.rating.RatingActivityModule;
import com.lesgood.siswa.ui.search.SearchActivityComponent;
import com.lesgood.siswa.ui.search.SearchActivityModule;
import com.lesgood.siswa.ui.setting.SettingActivityComponent;
import com.lesgood.siswa.ui.setting.SettingActivityModule;

import dagger.Subcomponent;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {

        MainActivityComponent plus(MainActivityModule activityModule);

        MainComponent plus(MainModule mainModule);

        EditProfileActivityComponent plus(EditProfileActivityModule activityModule);

        SettingActivityComponent plus(SettingActivityModule activityModule);

        BriefActivityComponent plus(BriefActivityModule activityModule);

        SearchActivityComponent plus(SearchActivityModule activityModule);

        DetailTeacherComponent plus(DetailTeacherModule module);

        ListActivityComponent plus(ListActivityModule module);

        RatingActivityComponent plus(RatingActivityModule module);

        ListGantiGuruActivityComponent plus(ListGantiGuruActivityModule listGantiGuruActivityModule);
}
