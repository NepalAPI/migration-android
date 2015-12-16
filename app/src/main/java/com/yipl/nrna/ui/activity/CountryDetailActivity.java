package com.yipl.nrna.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.CountryDetailActivityPresenter;
import com.yipl.nrna.ui.adapter.CountryInfoPagerAdapter;
import com.yipl.nrna.ui.interfaces.CountryDetailActivityView;

import javax.inject.Inject;

import butterknife.Bind;

public class CountryDetailActivity extends BaseActivity implements CountryDetailActivityView {

    public Country mCountry;
    Long mId;
    @Inject
    CountryDetailActivityPresenter mPresenter;
    @Bind(R.id.data_container)
    CoordinatorLayout mDataContainer;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.image)
    SimpleDraweeView mImage;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    private CountryInfoPagerAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_country_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            mId = data.getLong(MyConstants.Extras.KEY_ID, Long.MIN_VALUE);
        }
        getToolbar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialize();
        fetchDetail();
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(getActivityModule())
                .dataModule(new DataModule(mId))
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void fetchDetail() {
        mPresenter.initialize();
    }

    @Override
    public void renderCountryDetail(Country pCountry) {
        mCountry = pCountry;
        mImage.setImageURI(Uri.parse(mCountry.getImageUrl()));
        mAdapter = new CountryInfoPagerAdapter(getSupportFragmentManager(), this, mCountry);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetryView() {
    }

    @Override
    public void hideRetryView() {
    }

    @Override
    public void showErrorView(String pErrorMessage) {
        Snackbar.make(mDataContainer, pErrorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
