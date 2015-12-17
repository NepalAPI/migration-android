package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.AudioListView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/11/2015.
 */
public class AudioListFragmentPresenter implements Presenter {

    UseCase mAudioUseCase;
    private AudioListView mView;

    @Inject
    public AudioListFragmentPresenter(@Named("audioList") UseCase pAudioUseCase) {
        mAudioUseCase = pAudioUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mAudioUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadAudioList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (AudioListView) view;
    }

    private void loadAudioList() {
        mView.hideRetryView();
        mView.showLoadingView();
        getAudioList();
    }

    public void getAudioList() {
        this.mAudioUseCase.execute(new AudioSubscriber());
    }

    private final class AudioSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            AudioListFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            AudioListFragmentPresenter.this.mView.hideLoadingView();
            AudioListFragmentPresenter.this.mView.showEmptyView();
            Logger.e("AudioSubscriber_onError", e.getLocalizedMessage());
            e.printStackTrace();
            AudioListFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            AudioListFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (pPosts.isEmpty()) {
                AudioListFragmentPresenter.this.mView.showEmptyView();
            } else {
                AudioListFragmentPresenter.this.mView.hideEmptyView();
                AudioListFragmentPresenter.this.mView.renderAudiolist(pPosts);
            }
            AudioListFragmentPresenter.this.mView.hideLoadingView();
        }
    }

}
