package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.FilterDialogFragmentView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/22/2015.
 */
public class FilterDialogFragmentPresenter implements Presenter {

    FilterDialogFragmentView mView;
    UseCase mUseCase;

    @Inject
    FilterDialogFragmentPresenter(@Named("tagList") UseCase pUseCase) {
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
        loadTags();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (FilterDialogFragmentView) view;
    }

    private void loadTags() {
        mView.showLoadingView();
        mView.hideRetryView();
        getTags();
    }

    private void getTags() {
        mUseCase.execute(new TagSubscriber());
    }

    private final class TagSubscriber extends DefaultSubscriber<List<String>> {
        @Override
        public void onCompleted() {
            FilterDialogFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            FilterDialogFragmentPresenter.this.mView.hideLoadingView();
            FilterDialogFragmentPresenter.this.mView.showEmptyView();
            Logger.e("TagSubscriber_onError", e.getLocalizedMessage());
            FilterDialogFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            FilterDialogFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<String> pTags) {
            if (pTags.isEmpty()) {
                FilterDialogFragmentPresenter.this.mView.showEmptyView();
            } else {
                FilterDialogFragmentPresenter.this.mView.hideEmptyView();
                FilterDialogFragmentPresenter.this.mView.renderTags(pTags);
            }
            FilterDialogFragmentPresenter.this.mView.hideLoadingView();
        }
    }
}
