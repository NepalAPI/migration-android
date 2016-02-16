package com.yipl.nrna.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.ui.adapter.InfoCenterPagerAdapter;
import com.yipl.nrna.ui.adapter.QuestionListContainerAdapter;

import butterknife.Bind;

/**
 * Created by Nirazan-PC on 2/16/2016.
 */
public class QuestionListContainerFragment extends BaseFragment {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.data_container)
    LinearLayout mContainer;
    QuestionListContainerAdapter mPagerAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_question_list_container;
    }

    public QuestionListContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPagerAdapter = new QuestionListContainerAdapter(getChildFragmentManager(), getContext());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void showNewContentInfo() {
        Snackbar.make(mContainer, getString(R.string.message_content_available), Snackbar
                .LENGTH_INDEFINITE)
                .setAction(getString(R.string.action_refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View pView) {
                        mPagerAdapter.notifyDataSetChanged();
                    }
                })
                .show();
    }

}
