package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.HomeFragmentView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by julian on 12/9/15.
 */
public class HomeFragmentPresenter implements Presenter {
    HomeFragmentView mView;
    private final UseCase mUseCase;

    @Inject
    public HomeFragmentPresenter(@Named("latest") UseCase pUseCase) {
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
        mView = (HomeFragmentView) pView;
    }

    private void loadLatestContent(){
        mView.hideRetryView();
        mView.showLoadingView();
        getLatestContent();
    }

    private void showContents(){

    }

    private void getLatestContent() {
        this.mUseCase.execute(new LatestContentSubscriber());
    }

    //// TODO: 12/9/15 add Type T for DefaultSubscriber
    private final class LatestContentSubscriber extends DefaultSubscriber {

        @Override public void onCompleted() {
            HomeFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override public void onError(Throwable e) {
            HomeFragmentPresenter.this.mView.hideLoadingView();
            HomeFragmentPresenter.this.mView.showEmptyView();
            Logger.e("LatestContentSubscriber_onError", e.getLocalizedMessage());
            HomeFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception)e).getException()));
            HomeFragmentPresenter.this.mView.showRetryView();
        }

        @Override public void onNext(Object pObject) {
            //// TODO: 12/9/15
            HomeFragmentPresenter.this.showContents();
        }
    }
}
