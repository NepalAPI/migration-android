package com.yipl.nrna.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.base.FacebookActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.FileData;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.PostDetailPresenter;
import com.yipl.nrna.ui.interfaces.PostDetailView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ArticleDetailActivity extends FacebookActivity implements PostDetailView {

    Long mId;
    @Inject
    PostDetailPresenter mPresenter;

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.webContent)
    WebView webContent;
    @Bind(R.id.container_document)
    LinearLayout mDocumentContainer;
    private Post mPost;

    @Override
    public int getLayout() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            mId = data.getLong(MyConstants.Extras.KEY_ID, Long.MIN_VALUE);
        }
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
    public void renderPostDetail(Post post) {
        mPost = post;
        tvTitle.setText(post.getTitle());
        StringBuilder sb = new StringBuilder();
        sb.append("<HTML><HEAD><LINK href=\"styles.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
        sb.append(post.getData().getContent());
        sb.append("</body></HTML>");
        webContent.loadDataWithBaseURL("file:///android_asset/", sb.toString(), "text/html",
                "utf-8", null);
        /*webContent.loadDataWithBaseURL(null, post.getData().getContent(), "text/html", "utf-8",
                null);*/
        showDocumentView(post.getData().getFileData());

    }

    @Override
    public void onDownloadStarted(String message) {
        Snackbar.make(mDocumentContainer, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            showShareDialog(mPost);
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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

    public void showDocumentView(List<FileData> pFiles) {
        for (final FileData file : pFiles) {
            final String documentUrl = file.getFileUrl();
            final String type = documentUrl.substring(documentUrl.lastIndexOf(".") + 1);
            View v = getLayoutInflater().inflate(R.layout.layout_document_list, mDocumentContainer, false);
            TextView tvAction = (TextView) v.findViewById(R.id.documentAction);
            TextView tvTitle = (TextView) v.findViewById(R.id.documentTitle);
            if (type.equals("pdf"))
                tvAction.setText(getString(R.string.view_pdf));
            else {
                tvAction.setText(getString(R.string.download));
            }
            tvTitle.setText(file.getDescription());
            tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("pdf")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(documentUrl), "application/pdf");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Snackbar.make(mDocumentContainer, "", Snackbar
                                    .LENGTH_SHORT).show();
                        }
                    } else {
                        mPresenter.downloadDocument(file);
                    }
                }
            });
            mDocumentContainer.addView(v);
        }
    }
}
