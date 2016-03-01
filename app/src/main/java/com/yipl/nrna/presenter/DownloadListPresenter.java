package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.DownloadItem;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.CurrentDownloadsView;
import com.yipl.nrna.ui.interfaces.MvpView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class DownloadListPresenter implements Presenter {

    UseCase mUseCase;
    CurrentDownloadsView mView;

    @Inject
    public DownloadListPresenter(@Named("current_downloads") UseCase pUseCase) {
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
        loadDownloadList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (CurrentDownloadsView) view;
    }


    private void loadDownloadList() {
        mView.hideRetryView();
        mView.showLoadingView();
        mView.hideEmptyView();
        getDownloadList();
    }

    private void getDownloadList() {
        this.mUseCase.execute(new MySubscriber());
    }

    private final class MySubscriber extends DefaultSubscriber<List<DownloadItem>> {

        @Override
        public void onCompleted() {
            DownloadListPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            DownloadListPresenter.this.mView.hideLoadingView();
            DownloadListPresenter.this.mView.showEmptyView();
            e.printStackTrace();
            DownloadListPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            DownloadListPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<DownloadItem> pPosts) {
            if (pPosts.isEmpty()) {
                DownloadListPresenter.this.mView.showEmptyView();
            } else {
                DownloadListPresenter.this.mView.hideEmptyView();
                DownloadListPresenter.this.mView.showCurrentDownloads(pPosts);
            }
            DownloadListPresenter.this.mView.hideLoadingView();
        }
    }

}
