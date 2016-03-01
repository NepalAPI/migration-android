package com.yipl.nrna.ui.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.data.utils.Logger;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.DownloadItem;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.presenter.DownloadListPresenter;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.interfaces.CurrentDownloadsView;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class CurrentDownloadsActivity extends BaseActivity implements
        CurrentDownloadsView,
        ListClickCallbackInterface {

    @Inject
    DownloadListPresenter mPresenter;

    @Bind(R.id.download_list)
    RecyclerView mDownloadList;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    DownloadManager mDownloadManager;
    ListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_current_downloads;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        initialize();
        setUpListAdapter();
        fetchList();
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(getActivityModule())
                .dataModule(new DataModule())
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void setUpListAdapter() {
        mAdapter = new ListAdapter(getContext(), this);
        mDownloadList.setLayoutManager(new LinearLayoutManager(getContext()));
        mDownloadList.setAdapter(mAdapter);
    }

    private void fetchList() {
        mPresenter.initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected(BaseModel pModel) {
        mDownloadManager.remove(((DownloadItem) pModel).getDownloadReference());
    }

    @Override
    public void onAudioItemSelected(List<Post> pAudios, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showCurrentDownloads(List<DownloadItem> items) {
        mAdapter.setDataCollection(items);
        Logger.d("CurrentDownloadsActivity_showCurrentDownloads", "test:" + mAdapter.getItemCount());
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
        Snackbar.make(mDownloadList, pErrorMessage, Snackbar.LENGTH_SHORT).show();
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
