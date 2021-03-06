package com.yipl.nrna.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.base.ContentListFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.ui.interfaces.PostListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nirazan-PC on 12/11/2015.
 */
public class PostListFragment extends ContentListFragment implements PostListView {

    @Bind(R.id.post_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tvNoPost)
    TextView tvNoPosts;
    @Bind(R.id.noPost)
    RelativeLayout mEmptyView;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.data_container)
    RelativeLayout mContainer;

    private Long mQuestionId = Long.MIN_VALUE;
    private MyConstants.PostType mType = null;
    private Boolean mDownloadStatus = null;
    private boolean mIncludeChildContents = false;

    public PostListFragment() {
        super();
    }

    public static PostListFragment newInstance(Long pId, MyConstants.PostType pType, Boolean
            pDownloadStatus) {
        PostListFragment fragment = new PostListFragment();
        Bundle data = new Bundle();
        data.putLong(MyConstants.Extras.KEY_ID, pId);
        data.putSerializable(MyConstants.Extras.KEY_TYPE, pType);
        data.putBoolean(MyConstants.Extras.KEY_DOWNLOAD_STATUS, pDownloadStatus);
        data.putBoolean(MyConstants.Extras.KEY_INCLUDE_CHILD_CONTENTS, false);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_post_list;
    }

    @Override
    public void showNewContentInfo() {
        Snackbar.make(mContainer, getString(R.string.message_content_available), Snackbar
                .LENGTH_INDEFINITE)
                .setAction(getString(R.string.action_refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View pView) {
                        loadPostList();
                    }
                })
                .show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        setUpAdapter();
        if (savedInstanceState != null) {
            List<Post> postList = (List<Post>) savedInstanceState.getSerializable(MyConstants.Extras.KEY_FILTERED_LIST);
            mListAdapter.setDataCollection(postList);
            mPosts = (List<Post>) savedInstanceState.getSerializable(MyConstants.Extras.KEY_LIST);
        } else
            loadPostList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            mQuestionId = data.getLong(MyConstants.Extras.KEY_ID);
            mDownloadStatus = data.getBoolean(MyConstants.Extras.KEY_DOWNLOAD_STATUS);
            mIncludeChildContents = data.getBoolean(MyConstants.Extras.KEY_INCLUDE_CHILD_CONTENTS,
                    false);
            mType = (MyConstants.PostType) data.getSerializable(MyConstants.Extras.KEY_TYPE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadPostList() {
        mPresenter.initialize();
    }

    private void setUpAdapter() {
        mListAdapter = new ListAdapter<>(getContext(), new ArrayList<Post>(),
                (ListClickCallbackInterface) getActivity());
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void initialize() {
        if (mQuestionId != Long.MIN_VALUE) {
            tvNoPosts.setText(getString(R.string.sorry_prefix, getString(R.string
                    .question_related_text, getString(R.string.empty_post))));
            DaggerDataComponent.builder()
                    .dataModule(new DataModule(mQuestionId, MyConstants.DataParent.QUESTION,
                            mType, mDownloadStatus, mIncludeChildContents))
                    .activityModule(((BaseActivity) getActivity()).getActivityModule())
                    .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                    .build()
                    .inject(this);
        } else {
            tvNoPosts.setText(getString(R.string.sorry_prefix, getString(R.string.empty_post)));
            DaggerDataComponent.builder()
                    .dataModule(new DataModule(mType, mDownloadStatus))
                    .activityModule(((BaseActivity) getActivity()).getActivityModule())
                    .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                    .build()
                    .inject(this);
        }
        mPresenter.attachView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void renderPostList(List<Post> pAudios) {
        if (pAudios != null) {
            mPosts = pAudios;
            tvNoPosts.setVisibility(View.GONE);
            mListAdapter.setDataCollection(pAudios);
        }
    }
}
