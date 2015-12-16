package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.InfoCenterContentFragmentView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by julian on 12/9/15.
 */
public class InfoCenterFragmentPresenter implements Presenter {

    private final UseCase mPostUseCase;
    InfoCenterContentFragmentView mView;

    @Inject
    public InfoCenterFragmentPresenter(@Named("postList") UseCase pQuestionUseCase) {
        mPostUseCase = pQuestionUseCase;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mPostUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadAllPostsByStage();
    }

    @Override
    public void attachView(MvpView pView) {
        mView = (InfoCenterContentFragmentView) pView;
    }

    private void loadAllPostsByStage() {
        mView.hideRetryView();
        mView.showLoadingView();
        this.mPostUseCase.execute(new PostSubscriber());
    }

    private final class PostSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            InfoCenterFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            InfoCenterFragmentPresenter.this.mView.hideLoadingView();
            InfoCenterFragmentPresenter.this.mView.showEmptyView();
            Logger.e("PostSubscriber_onError", e.getLocalizedMessage());
            InfoCenterFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            InfoCenterFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (pPosts.isEmpty()) {
                InfoCenterFragmentPresenter.this.mView.showEmptyView();
            } else {
                InfoCenterFragmentPresenter.this.mView.renderPosts(pPosts);
            }
            InfoCenterFragmentPresenter.this.mView.hideLoadingView();
        }
    }
}
