package com.yipl.nrna.ui.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.presenter.HomeFragmentPresenter;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.interfaces.HomeFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    @Inject
    HomeFragmentPresenter mPresenter;

    ProgressDialog mProgressDialog;

    public HomeFragment() {
        super();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
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

        mProgressDialog = new ProgressDialog(getActivity(), ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

        loadLatestContent();
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

    private void initialize(){
        DaggerDataComponent.builder()
                .activityModule(((MainActivity)getActivity()).getActivityModule())
                .applicationComponent(((MainActivity)getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void setUpAdapter(){
    }

    private void loadLatestContent(){
        mPresenter.initialize();
    }

    @Override
    public void renderLatestQuestions(List<Question> pQuestions) {

    }

    @Override
    public void renderLatestAudios(List<Post> pAudios) {
    }

    @Override
    public void renderLatestVideos(List<Post> pVideos) {

    }

    @Override
    public void showLoadingView() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingView() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showErrorView(String pErrorMessage) {

    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void showRetryView() {
    }

    @Override
    public void hideRetryView() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }
}
