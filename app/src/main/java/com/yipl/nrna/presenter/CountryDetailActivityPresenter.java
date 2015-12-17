package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.ui.interfaces.CountryDetailActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class CountryDetailActivityPresenter implements Presenter {

    UseCase mmUseCase;
    CountryDetailActivityView mView;

    @Inject
    public CountryDetailActivityPresenter(@Named("countryDetails") UseCase pUseCase) {
        mmUseCase = pUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mmUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadCountry();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (CountryDetailActivityView) view;
    }

    private void loadCountry() {
        this.mmUseCase.execute(new CountryDetailSubscriber());
    }

    private final class CountryDetailSubscriber extends DefaultSubscriber<Country> {
        @Override
        public void onCompleted() {
            CountryDetailActivityPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            CountryDetailActivityPresenter.this.mView.hideLoadingView();
            CountryDetailActivityPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            CountryDetailActivityPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(Country pCountry) {
            CountryDetailActivityPresenter.this.mView.renderCountryDetail(pCountry);
        }
    }


}
