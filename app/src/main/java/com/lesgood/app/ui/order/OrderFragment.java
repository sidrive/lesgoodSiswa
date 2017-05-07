package com.lesgood.app.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lesgood.app.R;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.base.BaseFragment;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Agus on 4/27/17.
 */

public class OrderFragment extends BaseFragment {
    @Inject
    OrderPresenter presenter;

    @Inject
    User user;

    @Inject
    MainActivity activity;


    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupFragmentComponent() {
        BaseApplication.get(getActivity())
                .getMainComponent()
                .plus(new OrderFragmentModule(this))
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle("Pesanan");

        return view;
    }
}
