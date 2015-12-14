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
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.presenter.ArticleListFragmentPresenter;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.interfaces.ArticleListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class ArticleListFragment extends BaseFragment implements ArticleListView{

    @Inject
    ArticleListFragmentPresenter mPresenter;

    @Bind(R.id.recyclerViewArticleList)
    RecyclerView mRecyclerView;
    @Bind(R.id.tvNoArticle)
    TextView tvNoArticle;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private ListAdapter<Post> mListAdapter;

    public ArticleListFragment(){
        super();
    }

    public static ArticleListFragment newInstance(){
        ArticleListFragment fragment = new ArticleListFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_article_list;
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
        loadArticleList();
    }

    private void loadArticleList() {
        mPresenter.initialize();
    }

    private void setUpAdapter() {
        mListAdapter = new ListAdapter<Post>(getContext(), new ArrayList<Post>());
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(((MainActivity) getActivity()).getActivityModule())
                .applicationComponent(((MainActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
        tvNoArticle.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void renderArticleList(List<Post> pPosts) {
        mListAdapter.setDataCollection(pPosts);
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressBar.setVisibility(View.INVISIBLE);
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
        tvNoArticle.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        tvNoArticle.setVisibility(View.INVISIBLE);
    }
}
