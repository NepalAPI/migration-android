package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.ui.interfaces.ArticleDetailActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class ArticleDetailActivityPresenter implements Presenter {

    UseCase mArticleDetailUseCase;
    ArticleDetailActivityView mView;

    @Inject
    public ArticleDetailActivityPresenter(@Named("articleDetails") UseCase pArticleUseCase) {
        mArticleDetailUseCase = pArticleUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mArticleDetailUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadArticle();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (ArticleDetailActivityView) view;
    }

    private void loadArticle() {
        getArticle();
    }

    private void getArticle() {
        this.mArticleDetailUseCase.execute(new ArticleDetailSubscriber());
    }

    private final class ArticleDetailSubscriber extends DefaultSubscriber<Post> {
        @Override
        public void onCompleted() {
            ArticleDetailActivityPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            ArticleDetailActivityPresenter.this.mView.hideLoadingView();
            ArticleDetailActivityPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            ArticleDetailActivityPresenter.this.mView.showRetryView();
            e.printStackTrace();
        }

        @Override
        public void onNext(Post pPost) {
            ArticleDetailActivityPresenter.this.mView.renderArticleDetail(pPost);
        }
    }


}
