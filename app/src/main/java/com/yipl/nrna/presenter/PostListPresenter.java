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
public class PostListPresenter implements Presenter {

    UseCase mUseCase;
    PostListView mView;

    @Inject
    public PostListPresenter(@Named("postList") UseCase pUseCase) {
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
        loadPostList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (PostListView) view;
    }

    private void loadPostList() {
        mView.hideRetryView();
        mView.showLoadingView();
        mView.hideEmptyView();
        getPostList();
    }

    private void getPostList() {
        this.mUseCase.execute(new PostSubscriber());
    }

    private final class PostSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            PostListPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            PostListPresenter.this.mView.hideLoadingView();
            PostListPresenter.this.mView.showEmptyView();
            e.printStackTrace();
            PostListPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            PostListPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (pPosts.isEmpty()) {
                PostListPresenter.this.mView.showEmptyView();
            } else {
                PostListPresenter.this.mView.hideEmptyView();
                PostListPresenter.this.mView.renderPostList(pPosts);
            }
            PostListPresenter.this.mView.hideLoadingView();
        }
    }

}
