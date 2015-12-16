package com.yipl.nrna.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.presenter.CountryListFragmentPresenter;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.interfaces.CountryListView;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.ui.interfaces.MainActivityView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nirazan-PC on 12/11/2015.
 */
public class CountryListFragment extends BaseFragment implements CountryListView {

    @Inject
    CountryListFragmentPresenter mPresenter;

    @Bind(R.id.countryList)
    RecyclerView mRecyclerView;
    @Bind(R.id.tvNoCountry)
    TextView tvNoCountry;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private ListAdapter<Country> mListAdapter;

    public CountryListFragment() {
        super();
    }

    public static CountryListFragment newInstance() {
        CountryListFragment fragment = new CountryListFragment();
        return fragment;
    }
    @Override
    public int getLayout() {
        return R.layout.fragment_country_list;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        setUpAdapter();
        loadCountryList();
    }

    private void loadCountryList() {
        mPresenter.initialize();
    }

    private void setUpAdapter() {
        mListAdapter = new ListAdapter<>(getContext(), (ListClickCallbackInterface) getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(((MainActivity) getActivity()).getActivityModule())
                .applicationComponent(((MainActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
        Snackbar.make(mRecyclerView, pErrorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void showEmptyView() {
        tvNoCountry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        tvNoCountry.setVisibility(View.INVISIBLE);
    }

    @Override
    public void renderCountryList(List<Country> pCountries) {
        if (pCountries != null) {
            mListAdapter.setDataCollection(pCountries);
        }
    }
}
