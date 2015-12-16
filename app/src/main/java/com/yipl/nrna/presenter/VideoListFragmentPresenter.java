package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.VideoListView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class VideoListFragmentPresenter implements Presenter {

    UseCase mVideoUseCase;
    VideoListView mView;

    @Inject
    public VideoListFragmentPresenter(@Named("videoList") UseCase pVideoUseCase) {
        mVideoUseCase = pVideoUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mVideoUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadVideoList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (VideoListView) view;
    }

    private void loadVideoList() {
        mView.hideRetryView();
        mView.showLoadingView();
        getVideoList();
    }

    private void getVideoList() {
        this.mVideoUseCase.execute(new VideoSubscriber());
    }

    private class VideoSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            VideoListFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            VideoListFragmentPresenter.this.mView.hideLoadingView();
            VideoListFragmentPresenter.this.mView.showEmptyView();
            Logger.e("VideoSubscriber_onError", e.getLocalizedMessage());
            e.printStackTrace();
            VideoListFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            VideoListFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (pPosts.isEmpty()) {
                VideoListFragmentPresenter.this.mView.showEmptyView();
            } else {
                VideoListFragmentPresenter.this.mView.hideEmptyView();
                VideoListFragmentPresenter.this.mView.renderVideoList(pPosts);
            }
            VideoListFragmentPresenter.this.mView.hideLoadingView();
        }
    }

}
