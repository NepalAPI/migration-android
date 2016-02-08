package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.QuestionDetailActivityView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class QuestionDetailPresenter implements Presenter {

    UseCase mUseCase;
    QuestionDetailActivityView mView;

    @Inject
    public QuestionDetailPresenter(@Named("questionDetails") UseCase pUseCase) {
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
        loadQuestionDetails();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (QuestionDetailActivityView) view;
    }

    private void loadQuestionDetails() {
        this.mUseCase.execute(new QuestionDetailSubscriber());
    }

    private final class QuestionDetailSubscriber extends DefaultSubscriber<Question> {
        @Override
        public void onCompleted() {
            QuestionDetailPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            QuestionDetailPresenter.this.mView.hideLoadingView();
            e.printStackTrace();
            QuestionDetailPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            QuestionDetailPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(Question pQuestion) {
            QuestionDetailPresenter.this.mView.renderQuestionDetail(pQuestion);
            onCompleted();
        }
    }

}
