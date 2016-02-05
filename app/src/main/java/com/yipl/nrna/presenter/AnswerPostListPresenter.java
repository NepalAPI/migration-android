package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.PostListView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class AnswerPostListPresenter implements Presenter {

    UseCase mUseCase;
    PostListView mView;

    @Inject
    public AnswerPostListPresenter(@Named("postList") UseCase pUseCase) {
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
        loadArticleList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (PostListView) view;
    }

    private void loadArticleList() {
        mView.hideRetryView();
        mView.showLoadingView();
        mView.hideEmptyView();
        getArticleList();
    }

    private void getArticleList() {
        this.mUseCase.execute(new ArticleSubscriber());
    }

    private final class ArticleSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            AnswerPostListPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            AnswerPostListPresenter.this.mView.hideLoadingView();
            AnswerPostListPresenter.this.mView.showEmptyView();
            e.printStackTrace();
            AnswerPostListPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            AnswerPostListPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (pPosts.isEmpty()) {
                AnswerPostListPresenter.this.mView.showEmptyView();
            } else {
                AnswerPostListPresenter.this.mView.hideEmptyView();
                AnswerPostListPresenter.this.mView.renderPostList(pPosts);
            }
            AnswerPostListPresenter.this.mView.hideLoadingView();
        }
    }

}
