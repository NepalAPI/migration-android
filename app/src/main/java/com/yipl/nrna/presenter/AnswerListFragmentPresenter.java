package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Answer;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.AnswerListView;
import com.yipl.nrna.ui.interfaces.MvpView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class AnswerListFragmentPresenter implements Presenter {

    final UseCase mAnswerUseCase;
    AnswerListView mView;

    @Inject
    public AnswerListFragmentPresenter(@Named("answerList") UseCase pAnswerUseCase) {
        mAnswerUseCase = pAnswerUseCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mAnswerUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadAnswerList();
    }

    @Override
    public void attachView(MvpView view) {
        mView = (AnswerListView) view;
    }

    private void loadAnswerList() {
        mView.hideRetryView();
        mView.showLoadingView();
        mView.hideEmptyView();
        getArticleList();
    }

    private void getArticleList() {
        this.mAnswerUseCase.execute(new AnswerSubscriber());
    }

    private final class AnswerSubscriber extends DefaultSubscriber<List<Answer>> {

        @Override
        public void onCompleted() {
            AnswerListFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            AnswerListFragmentPresenter.this.mView.hideLoadingView();
            AnswerListFragmentPresenter.this.mView.showEmptyView();
            e.printStackTrace();
            AnswerListFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            AnswerListFragmentPresenter.this.mView.showRetryView();
        }

        @Override
        public void onNext(List<Answer> pAnswers) {
            if (pAnswers.isEmpty()) {
                AnswerListFragmentPresenter.this.mView.showEmptyView();
            } else {
                AnswerListFragmentPresenter.this.mView.hideEmptyView();
                AnswerListFragmentPresenter.this.mView.renderAnswerList(pAnswers);
            }
            AnswerListFragmentPresenter.this.mView.hideLoadingView();
        }
    }

}
