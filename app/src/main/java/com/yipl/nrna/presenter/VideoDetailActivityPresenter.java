package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.VideoDetailActivityView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class VideoDetailActivityPresenter implements Presenter {

    UseCase mmUseCase;
    VideoDetailActivityView mView;

    @Inject
    public VideoDetailActivityPresenter(@Named("videoDetails") UseCase pUseCase){
        mmUseCase = pUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mmUseCase.unSubscribe();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (VideoDetailActivityView) view;
    }

    @Override
    public void initialize() {
        loadVideo();
    }

    private void loadVideo() {
        this.mmUseCase.execute(new VideoDetailSubscriber());
    }

    private final class VideoDetailSubscriber extends DefaultSubscriber<Post>{
        @Override
        public void onCompleted() {
            VideoDetailActivityPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            VideoDetailActivityPresenter.this.mView.hideLoadingView();
            VideoDetailActivityPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            VideoDetailActivityPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(Post pVideo) {
            VideoDetailActivityPresenter.this.mView.renderVideoDetail(pVideo);
        }
    }


}
