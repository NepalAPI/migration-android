package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.PostDetailView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class PostDetailPresenter implements Presenter {

    UseCase mUseCase;
    PostDetailView mView;

    @Inject
    public PostDetailPresenter(@Named("postDetails") UseCase pUseCase) {
        mUseCase = pUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadArticle();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (PostDetailView) view;
    }

    private void loadArticle() {
        getArticle();
    }

    private void getArticle() {
        this.mUseCase.execute(new ArticleDetailSubscriber());
    }

    private final class ArticleDetailSubscriber extends DefaultSubscriber<Post> {
        @Override
        public void onCompleted() {
            PostDetailPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            PostDetailPresenter.this.mView.hideLoadingView();
            PostDetailPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            PostDetailPresenter.this.mView.showRetryView();
            e.printStackTrace();
        }

        @Override
        public void onNext(Post pPost) {
            PostDetailPresenter.this.mView.renderPostDetail(pPost);
        }
    }


}
