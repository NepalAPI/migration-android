package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.CountryUpdate;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.UpdateListView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class UpdatesPresenter implements Presenter {

    final UseCase mUseCase;
    UpdateListView mView;

    @Inject
    public UpdatesPresenter(@Named("updateList") UseCase pUseCase) {
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
        loadAnswerList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (UpdateListView) view;
    }

    private void loadAnswerList() {
        mView.hideRetryView();
        mView.showLoadingView();
        mView.hideEmptyView();
        getArticleList();
    }

    private void getArticleList() {
        this.mUseCase.execute(new UpdatesSubscriber());
    }

    private final class UpdatesSubscriber extends DefaultSubscriber<List<CountryUpdate>> {

        @Override
        public void onCompleted() {
            UpdatesPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            UpdatesPresenter.this.mView.hideLoadingView();
            UpdatesPresenter.this.mView.showEmptyView();
            e.printStackTrace();
            UpdatesPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            UpdatesPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<CountryUpdate> pUpdates) {
            if (pUpdates.isEmpty()) {
                UpdatesPresenter.this.mView.showEmptyView();
            } else {
                UpdatesPresenter.this.mView.hideEmptyView();
                UpdatesPresenter.this.mView.renderUpdates(pUpdates);
            }
            UpdatesPresenter.this.mView.hideLoadingView();
        }
    }

}
