package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.RelatedContentFragmentView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/21/2015.
 */
public class RelatedContentFragmentPresenter implements Presenter {

    private final UseCase mUseCase;
    RelatedContentFragmentView mView;

    @Inject
    public RelatedContentFragmentPresenter(@Named("postList") UseCase pUseCase) {
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
        loadAllPost();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (RelatedContentFragmentView) view;
    }

    private void loadAllPost() {
        mView.hideRetryView();
        mView.showLoadingView();
        getAllPost();
    }

    private void getAllPost() {
        mUseCase.execute(new PostSubscriber());
    }

    private final class PostSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            RelatedContentFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            RelatedContentFragmentPresenter.this.mView.hideLoadingView();
            RelatedContentFragmentPresenter.this.mView.showEmptyView();
            RelatedContentFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            RelatedContentFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (pPosts.isEmpty()) {
                RelatedContentFragmentPresenter.this.mView.showEmptyView();
            } else {
                RelatedContentFragmentPresenter.this.mView.renderRelatedContent(pPosts);
            }
            RelatedContentFragmentPresenter.this.mView.hideLoadingView();
        }
    }
}
