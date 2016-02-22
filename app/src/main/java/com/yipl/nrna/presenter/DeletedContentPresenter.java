package com.yipl.nrna.presenter;

import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.data.utils.Logger;
import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.AppPreferences;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentPresenter implements Presenter {

    private final UseCase mUseCase;
    private final AppPreferences mPref;
    MainActivityView mView;

    @Inject
    public DeletedContentPresenter(@Named("deleted") UseCase pUseCase, AppPreferences pref) {
        mUseCase = pUseCase;
        mPref = pref;
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

    @Override
    public void attachView(MvpView view) {
        mView = (MainActivityView) view;
    }

    private void deleteContent() {
        Logger.d("DeletedContentPresenter_deleteContent", "timestamp: " + mPref.getLastUpdateStamp());
        this.mUseCase.execute(new DeleteContentSubscriber(), mPref.getLastUpdateStamp());
    }

    private final class DeleteContentSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Logger.e("DeleteContentSubscriber_onError", "factory_message: " + ErrorMessageFactory
                    .create(mView.getContext(), new DefaultErrorBundle((Exception) e)
                            .getException()));
            e.printStackTrace();
            mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
        }

        @Override
        public void onNext(Boolean pFlag) {
            if (pFlag != null) {
                long timestamp = Calendar.getInstance().getTimeInMillis();
                Logger.d("DeleteContentSubscriber_onNext", "timestamp: " + (timestamp / 1000l));
                ((BaseActivity) mView).getPreferences().setLastUpdateStamp((timestamp / 1000L));
                Logger.d("DeleteContentSubscriber_onNext", "flag:" + pFlag);
                if (pFlag) mView.informCurrentFragmentForUpdate();
            }
        }
    }
}
