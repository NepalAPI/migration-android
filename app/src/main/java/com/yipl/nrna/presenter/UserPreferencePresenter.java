package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.PersonalizationView;
import com.yipl.nrna.util.Logger;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 1/26/2016.
 */
public class UserPreferencePresenter implements Presenter {

    private final UseCase mUseCase;
    private PersonalizationView mView;

    @Inject
    public UserPreferencePresenter(@Named("UserPreference") UseCase pUseCase) {
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
        sendUserPreference();
    }

    private void sendUserPreference() {
        mView.showLoadingView();
        mUseCase.execute(new UserPreferenceSubscriber());
    }

    @Override
    public void attachView(MvpView view) {
        mView = (PersonalizationView) view;
    }

    private final class UserPreferenceSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
            UserPreferencePresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            UserPreferencePresenter.this.mView.hideLoadingView();
            UserPreferencePresenter.this.mView.showRetryView();
            Logger.e("UserreferencePresenter_onError" + ErrorMessageFactory
                    .create(mView.getContext(), new DefaultErrorBundle((Exception) e)
                            .getException()));
            UserPreferencePresenter.this.mView.showError(ErrorMessageFactory
                    .create(mView.getContext(), new DefaultErrorBundle((Exception) e)
                            .getException()));
//            UserPreferencePresenter.this.mView.dataSent();

        }

        @Override
        public void onNext(Boolean pBoolean) {
            if (pBoolean) {
                UserPreferencePresenter.this.mView.hideLoadingView();
                UserPreferencePresenter.this.mView.dataSent();
            }
        }
    }
}