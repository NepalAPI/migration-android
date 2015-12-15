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
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.presenter.QuestionListFragmentPresenter;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.ui.interfaces.QuestionListFragmentView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Nirazan-PC on 12/15/2015.
 */
public class QuestionListFragment extends BaseFragment implements QuestionListFragmentView {

    @Inject
    QuestionListFragmentPresenter mPresenter;
    @Bind(R.id.recyclerViewQuestionList)
    RecyclerView mRecyclerView;
    @Bind(R.id.tvNoQuestion)
    TextView tvNoQuestion;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    ListAdapter<Question> mListAdapter;

    public QuestionListFragment(){
        super();
    }

    public static QuestionListFragment newInstance() {
        QuestionListFragment fragment = new QuestionListFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_question_list;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        setUpAdapter();
        loadQuestionList();
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(((MainActivity) getActivity()).getActivityModule())
                .applicationComponent(((MainActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
        tvNoQuestion.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpAdapter() {
        mListAdapter = new ListAdapter<Question>(getContext(),new ArrayList<Question>(), (MainActivityView) getActivity());
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void loadQuestionList() {
        mPresenter.initialize();
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
    public void renderQuestionList(List<Question> pQuestionList) {
        mListAdapter.setDataCollection(pQuestionList);
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
        tvNoQuestion.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        tvNoQuestion.setVisibility(View.GONE);
    }
}
