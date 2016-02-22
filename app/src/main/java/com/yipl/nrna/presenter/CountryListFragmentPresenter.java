package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.activity.PersonalizationActivity;
import com.yipl.nrna.ui.interfaces.CountryListView;
import com.yipl.nrna.ui.interfaces.MvpView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/11/2015.
 */
public class CountryListFragmentPresenter implements Presenter {

    UseCase mUseCase;
    private CountryListView mView;

    @Inject
    public CountryListFragmentPresenter(@Named("countryList") UseCase pUseCase) {
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
        loadCountryList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (CountryListView) view;
    }

    private void loadCountryList() {
        if (mView != null) {
            mView.hideRetryView();
            mView.showLoadingView();
            getCountryList();
        }
    }

    public void getCountryList() {
        this.mUseCase.execute(new CountrySubscriber());
    }

    private final class CountrySubscriber extends DefaultSubscriber<List<Country>> {

        @Override
        public void onCompleted() {
            CountryListFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            CountryListFragmentPresenter.this.mView.hideLoadingView();
            CountryListFragmentPresenter.this.mView.showEmptyView();
            e.printStackTrace();
            CountryListFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            CountryListFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Country> pCountries) {
            if (!(mView instanceof PersonalizationActivity)) {
                if (pCountries == null || pCountries.isEmpty()) {
                    CountryListFragmentPresenter.this.mView.showEmptyView();
                } else {
                    CountryListFragmentPresenter.this.mView.hideEmptyView();
                    CountryListFragmentPresenter.this.mView.renderCountryList(pCountries);
                }
            }
            if (mView instanceof PersonalizationActivity)
                CountryListFragmentPresenter.this.mView.renderCountryList(pCountries);

            CountryListFragmentPresenter.this.mView.hideLoadingView();
        }
    }

}
