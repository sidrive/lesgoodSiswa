package com.lesgood.siswa.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lesgood.siswa.R;
import com.lesgood.siswa.base.BaseApplication;
import com.lesgood.siswa.base.BaseFragment;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by Agus on 2/22/17.
 */

public class OrderFragment extends BaseFragment {

    @BindColor(R.color.colorShadow2)
    int colorAccentDark;

    @BindColor(R.color.colorBlack)
    int colorBlack;
    ;

    @Bind(R.id.container)
    ViewPager mViewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Inject
    User user;


    @Inject
    OrderPresenter presenter;

    @Inject
    MainActivity activity;

    private SectionsPagerAdapter mSectionsPagerAdapter;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Orders");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

    }


}
