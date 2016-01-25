package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.ui.interfaces.MvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class DownloadCompletePresenter implements Presenter {

    UseCase mUseCase;
    MvpView mView;

    @Inject
    public DownloadCompletePresenter(@Named("download_complete") UseCase pUseCase) {
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
    }

    @Override
    public void initialize() {
        mUseCase.execute(new DefaultSubscriber());
    }

    @Override
    public void attachView(MvpView view) {
        mView = view;
    }
}
