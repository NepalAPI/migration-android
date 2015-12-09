package com.yipl.nrna.presenter;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class LatestContentPresenter implements Presenter {

    MainActivityView mView;
    private final UseCase mUseCase;

    @Inject
    public LatestContentPresenter(@Named("latest") UseCase pUseCase) {
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
    public void initialize(){
        loadLatestContent();
    }

    @Override
    public void attachView(MvpView pView) {
        mView = (MainActivityView) pView;
    }

    private void loadLatestContent(){
        mView.hideRetryView();
        mView.showLoadingView();
        getLatestContent();
    }

    private void getLatestContent() {
        this.mUseCase.execute(new LatestContentSubscriber());
    }

    private final class LatestContentSubscriber extends DefaultSubscriber<LatestContent> {

        @Override public void onCompleted() {
            LatestContentPresenter.this.mView.hideLoadingView();
        }

        @Override public void onError(Throwable e) {
            LatestContentPresenter.this.mView.hideLoadingView();
            LatestContentPresenter.this.mView.showEmptyView();
            Logger.e("LatestContentSubscriber_onError", e.getLocalizedMessage());
            LatestContentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception)e).getException()));
            LatestContentPresenter.this.mView.showRetryView();
        }

        @Override public void onNext(LatestContent pLatestContent) {
            LatestContentPresenter.this.mView.informCurrentFragmentForUpdate();

        }
    }
}
