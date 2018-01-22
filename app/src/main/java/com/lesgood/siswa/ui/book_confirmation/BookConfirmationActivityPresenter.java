package com.lesgood.siswa.ui.book_confirmation;

import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.remote.UserService;


/**
 * Created by Agus on 8/10/17.
 */

public class BookConfirmationActivityPresenter implements BasePresenter {
    BookConfirmationActivity activity;
    UserService userService;
    Guru user;


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public BookConfirmationActivityPresenter(BookConfirmationActivity activity, UserService userService, Guru user){
        this.activity = activity;
        this.user = user;
        this.userService = userService;
    }
}
