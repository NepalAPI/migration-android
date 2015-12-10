package com.yipl.nrna.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.viewpagerindicator.CirclePageIndicator;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.presenter.HomeFragmentPresenter;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.adapter.QuestionPagerAdapter;
import com.yipl.nrna.ui.interfaces.HomeFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    @Inject
    HomeFragmentPresenter mPresenter;
    @Bind(R.id.container)
    View mContainer;
    @Bind(R.id.recent_questions_pager)
    ViewPager mQuestionsPager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.audio_list)
    RecyclerView mAudioList;
    @Bind(R.id.video_list)
    RecyclerView mVideoList;
    @Bind(R.id.question_progress_bar)
    ProgressBar mQuestionProgressBar;
    @Bind(R.id.audio_progress_bar)
    ProgressBar mAudioProgressBar;
    @Bind(R.id.video_progress_bar)
    ProgressBar mVideoProgressBar;

    private QuestionPagerAdapter mQuestionPagerAdapter;
    private ListAdapter<Post> mAudioListAdapter;
    private ListAdapter<Post> mVideoListAdapter;

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

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(((MainActivity) getActivity()).getActivityModule())
                .applicationComponent(((MainActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void setUpAdapter() {
        //for question
        mQuestionPagerAdapter = new QuestionPagerAdapter(getChildFragmentManager());
        mQuestionsPager.setAdapter(mQuestionPagerAdapter);
        indicator.setViewPager(mQuestionsPager);
        //for audio
        mAudioList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAudioListAdapter = new ListAdapter<>(getActivity());
        mAudioList.setAdapter(mAudioListAdapter);
        //for video
        mVideoList.setLayoutManager(new LinearLayoutManager(getContext()));
        mVideoListAdapter = new ListAdapter<>(getActivity());
        mVideoList.setAdapter(mVideoListAdapter);
    }

    private void loadLatestContent() {
        mPresenter.initialize();
    }

    @Override
    public void showNewContentInfo() {
        Snackbar.make(mContainer, "", Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.action_refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View pView) {
                        loadLatestContent();
                    }
                })
                .show();
    }

    @Override
    public void renderLatestQuestions(List<Question> pQuestions) {
        if (pQuestions != null)
            mQuestionPagerAdapter.setQuestions(pQuestions);
    }

    @Override
    public void renderLatestAudios(List<Post> pAudios) {
        if (pAudios != null)
            mAudioListAdapter.setDataCollection(pAudios);
    }

    @Override
    public void renderLatestVideos(List<Post> pVideos) {
        if (pVideos != null)
            mVideoListAdapter.setDataCollection(pVideos);
    }

    @Override
    public void showLoadingView(int flag) {
        switch (flag){
            case HomeFragmentPresenter.FLAG_AUDIO:
                mAudioProgressBar.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                mVideoProgressBar.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                mQuestionProgressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void hideLoadingView(int flag) {
        switch (flag){
            case HomeFragmentPresenter.FLAG_AUDIO:
                mAudioProgressBar.setVisibility(View.GONE);
            case HomeFragmentPresenter.FLAG_VIDEO:
                mVideoProgressBar.setVisibility(View.GONE);
            case HomeFragmentPresenter.FLAG_QUESTION:
                mQuestionProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorView(String pErrorMessage) {
        Snackbar.make(mContainer, pErrorMessage,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorView() {
    }

    @Override
    public void showRetryView(int flag) {
    }

    @Override
    public void hideRetryView(int flag) {

    }

    @Override
    public void showEmptyView(int flag) {
        switch (flag){
            case HomeFragmentPresenter.FLAG_AUDIO:
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                break;
        }
    }

    @Override
    public void hideEmptyView(int flag) {
        switch (flag){
            case HomeFragmentPresenter.FLAG_AUDIO:
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                break;
        }
    }
}
