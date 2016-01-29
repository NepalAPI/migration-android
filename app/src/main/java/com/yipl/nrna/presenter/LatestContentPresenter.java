package com.yipl.nrna.presenter;

import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.activity.PersonalizationActivity;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class LatestContentPresenter implements Presenter {

    private final UseCase mUseCase;
    MainActivityView mView;

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
    public void initialize() {
        loadLatestContent();
    }

    @Override
    public void attachView(MvpView pView) {
        mView = (MainActivityView) pView;
    }

    private void loadLatestContent() {
        mView.hideRetryView();
        mView.showLoadingView();
        getLatestContent();
    }

    private void getLatestContent() {
        this.mUseCase.execute(new LatestContentSubscriber());
    }

    private final class LatestContentSubscriber extends DefaultSubscriber<LatestContent> {

        @Override
        public void onCompleted() {
            if (mView instanceof PersonalizationActivity)
                LatestContentPresenter.this.mView.informCurrentFragmentForUpdate();
            LatestContentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            LatestContentPresenter.this.mView.hideLoadingView();
            LatestContentPresenter.this.mView.showEmptyView();
            Logger.e("LatestContentSubscriber_onError", "local_message: " + e.getLocalizedMessage());
            Logger.e("LatestContentSubscriber_onError", "factory_message: " + ErrorMessageFactory
                    .create(mView.getContext(), new DefaultErrorBundle((Exception) e)
                            .getException()));
            e.printStackTrace();
            LatestContentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            LatestContentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(LatestContent pLatestContent) {
            if (pLatestContent != null) {
                long timestamp = Calendar.getInstance().getTimeInMillis();
                Logger.d("LatestContentSubscriber_onNext", "timestamp: " + (timestamp / 1000l));
                ((BaseActivity) mView).getPreferences().setLastUpdateStamp((timestamp / 1000L) -
                        2000);
                if (!pLatestContent.getPosts().isEmpty() && !pLatestContent.getQuestions()
                        .isEmpty() && !(mView instanceof PersonalizationActivity))
                    LatestContentPresenter.this.mView.informCurrentFragmentForUpdate();

            }
            LatestContentPresenter.this.mView.hideLoadingView();
        }
    }
}
