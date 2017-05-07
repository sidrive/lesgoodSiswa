package com.lesgood.app.data.user;



import com.lesgood.app.base.annotation.UserScope;
import com.lesgood.app.data.main.MainComponent;
import com.lesgood.app.data.main.MainModule;
import com.lesgood.app.ui.brief.BriefActivityComponent;
import com.lesgood.app.ui.brief.BriefActivityModule;
import com.lesgood.app.ui.edit_profile.EditProfileActivityComponent;
import com.lesgood.app.ui.edit_profile.EditProfileActivityModule;
import com.lesgood.app.ui.main.MainActivityComponent;
import com.lesgood.app.ui.main.MainActivityModule;
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
}
