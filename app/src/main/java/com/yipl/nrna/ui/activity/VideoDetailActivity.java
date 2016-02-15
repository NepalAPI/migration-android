package com.yipl.nrna.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.yipl.nrna.R;
import com.yipl.nrna.base.FacebookActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.PostDetailPresenter;
import com.yipl.nrna.ui.interfaces.PostDetailView;
import com.yipl.nrna.data.utils.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;

public class VideoDetailActivity extends FacebookActivity implements PostDetailView, YouTubePlayer.OnInitializedListener {

    public Post mVideo;
    Long mId;
    @Inject
    PostDetailPresenter mPresenter;

    @Bind(R.id.data_container)
    CoordinatorLayout mDataContainer;
    //    @Bind(R.id.image)
//    SimpleDraweeView mImage;
    @Bind(R.id.description)
    TextView mDescription;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    private YouTubePlayerSupportFragment mYouTubePlayerFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            mId = data.getLong(MyConstants.Extras.KEY_ID, Long.MIN_VALUE);
            getSupportActionBar().setTitle(data.getString(MyConstants.Extras.KEY_TITLE));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mYouTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        initialize();
        fetchDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            showShareDialog(mVideo);
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
    public void renderPostDetail(Post pVideo) {
        mVideo = pVideo;
//        getSupportActionBar().setTitle(mVideo.getTitle());
//        mImage.setImageURI(Uri.parse(mVideo.getData().getThumbnail()));
//        mImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(mVideo.getData().getMediaUrl()));
//                startActivity(intent);
//            }
//        });
        mDescription.setText(mVideo.getDescription());
        mYouTubePlayerFragment.initialize(MyConstants.YOUTUBE_API_KEY, this);
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider pProvider, YouTubePlayer pYouTubePlayer, boolean isRestored) {

        pYouTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        pYouTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        pYouTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        String videoId = getYoutubeIdFromUrl(mVideo.getData().getMediaUrl());
        if (!isRestored) {
            if (!videoId.isEmpty())
                pYouTubePlayer.loadVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider pProvider, YouTubeInitializationResult pYouTubeInitializationResult) {
        Logger.e(pYouTubeInitializationResult.toString());
    }

    public String getYoutubeIdFromUrl(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    @Override
    public void onDownloadStarted(String message) {
        throw new UnsupportedOperationException();
    }
}
