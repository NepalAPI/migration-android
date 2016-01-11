package com.yipl.nrna.presenter;

import android.util.Log;

import com.yipl.nrna.domain.repository.IBaseRepository;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentPresenter implements Presenter {

    IBaseRepository mRepository;
    MainActivityView mView;

    @Inject
    public DeletedContentPresenter(@Named("deleted")IBaseRepository pRepository){
        mRepository = pRepository;
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
        deleteContent();
    }

    private void deleteContent() {
        mRepository.getSingle(0L).subscribeOn(Schedulers.newThread()).subscribe(new Observer() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object t) {
                mView.informCurrentFragmentForUpdate();
            }
        });
    }

    @Override
    public void attachView(MvpView view) {
        mView = (MainActivityView) view;
    }
}
