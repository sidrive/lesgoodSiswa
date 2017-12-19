package com.lesgood.app.data.user;



import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.detail_teacher.DetailTeacherComponent;
import com.lesgood.app.data.detail_teacher.DetailTeacherModule;
import com.lesgood.app.data.main.MainComponent;
import com.lesgood.app.data.main.MainModule;
import com.lesgood.app.ui.brief.BriefActivityComponent;
import com.lesgood.app.ui.brief.BriefActivityModule;
import com.lesgood.app.ui.edit_profile.EditProfileActivityComponent;
import com.lesgood.app.ui.edit_profile.EditProfileActivityModule;
import com.lesgood.app.ui.list.ListActivityComponent;
import com.lesgood.app.ui.list.ListActivityModule;
import com.lesgood.app.ui.list.ListGantiGuruActivityComponent;
import com.lesgood.app.ui.list.ListGantiGuruActivityModule;
import com.lesgood.app.ui.main.MainActivityComponent;
import com.lesgood.app.ui.main.MainActivityModule;
import com.lesgood.app.ui.pengalaman.PengalamanActivityComponent;
import com.lesgood.app.ui.pengalaman.PengalamanActivityModule;
import com.lesgood.app.ui.prestasi.PrestasiActivityComponent;
import com.lesgood.app.ui.prestasi.PrestasiActivityModule;
import com.lesgood.app.ui.rating.RatingActivity;
import com.lesgood.app.ui.rating.RatingActivityComponent;
import com.lesgood.app.ui.rating.RatingActivityModule;
import com.lesgood.app.ui.search.SearchActivityComponent;
import com.lesgood.app.ui.search.SearchActivityModule;
import com.lesgood.app.ui.setting.SettingActivityComponent;
import com.lesgood.app.ui.setting.SettingActivityModule;

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
