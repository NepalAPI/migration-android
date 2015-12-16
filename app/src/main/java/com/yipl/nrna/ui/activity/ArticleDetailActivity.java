package com.yipl.nrna.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.ArticleDetailActivityPresenter;
import com.yipl.nrna.ui.interfaces.ArticleDetailActivityView;

import javax.inject.Inject;

import butterknife.Bind;

public class ArticleDetailActivity extends BaseActivity implements ArticleDetailActivityView {

    Long mId;
    @Inject
    ArticleDetailActivityPresenter mPresenter;

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.webContent)
    WebView webContent;

    @Override
    public int getLayout() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle data = getIntent().getExtras();
        if(data!= null){
            mId = data.getLong(MyConstants.Extras.KEY_ID, Long.MIN_VALUE);
        }
        initialize();
        fetchDetail();
    }

    private void initialize(){
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
    public void renderArticleDetail(Post post) {
        tvTitle.setText(post.getTitle());
        webContent.loadData(post.getData().getContent(), "text/html","utf-8");
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showRetryView() {

    }

    @Override
    public void hideRetryView() {

    }

    @Override
    public void showErrorView(String pErrorMessage) {

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
