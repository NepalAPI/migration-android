package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.ArticleListView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class ArticleListFragmentPresenter implements Presenter {

    UseCase mArticleUseCase;
    ArticleListView mView;

    @Inject
    public ArticleListFragmentPresenter(@Named("articleList") UseCase pArticleUseCase){
        mArticleUseCase = pArticleUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mArticleUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadArticleList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (ArticleListView) view;
    }


    private void loadArticleList() {
        mView.hideRetryView();
        mView.showLoadingView();
        mView.hideEmptyView();
        getArticleList();
    }

    private void getArticleList() {
        this.mArticleUseCase.execute(new ArticleSubscriber());
    }

    private final class ArticleSubscriber extends DefaultSubscriber<List<Post>>{

        @Override
        public void onCompleted() {
            ArticleListFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            ArticleListFragmentPresenter.this.mView.hideLoadingView();
            ArticleListFragmentPresenter.this.mView.showEmptyView();
            Logger.e("ArticleSubscriber_onError", e.getLocalizedMessage());
            e.printStackTrace();
            ArticleListFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            ArticleListFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if(pPosts.isEmpty()) {
                ArticleListFragmentPresenter.this.mView.showEmptyView();
            }else{
                ArticleListFragmentPresenter.this.mView.hideEmptyView();
                ArticleListFragmentPresenter.this.mView.renderArticleList(pPosts);
            }
            ArticleListFragmentPresenter.this.mView.hideLoadingView();
        }
    }

}
