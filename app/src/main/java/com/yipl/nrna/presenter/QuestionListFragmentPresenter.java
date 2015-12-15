package com.yipl.nrna.presenter;

import android.util.Log;

import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.ui.interfaces.QuestionListFragmentView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Nirazan-PC on 12/15/2015.
 */
public class QuestionListFragmentPresenter implements Presenter {

    UseCase mUseCase;
    QuestionListFragmentView mView;

    @Inject
    public QuestionListFragmentPresenter(@Named("questionList") UseCase pQuestionUseCase){
        mUseCase = pQuestionUseCase;
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
    public void attachView(MvpView view) {
        mView = (QuestionListFragmentView) view;
    }

    @Override
    public void initialize() {
        loadQuestionList();
    }

    private void loadQuestionList() {
        mView.showLoadingView();
        mView.hideRetryView();
        getQuestionList();
    }

    private void getQuestionList() {
        mUseCase.execute(new QuestionListSubscirber());
    }

    private final class QuestionListSubscirber extends DefaultSubscriber<List<Question>>{

        @Override
        public void onCompleted() {
            Log.e("oncomplete", "on complete called");
            QuestionListFragmentPresenter.this.mView.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            QuestionListFragmentPresenter.this.mView.hideLoadingView();
            QuestionListFragmentPresenter.this.mView.showRetryView();
            QuestionListFragmentPresenter.this.mView.showEmptyView();
            QuestionListFragmentPresenter.this.mView.showErrorView(e.getLocalizedMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Question> pQuestionList) {
            if(pQuestionList.isEmpty()){
                QuestionListFragmentPresenter.this.mView.showEmptyView();
            }
            else{
                QuestionListFragmentPresenter.this.mView.hideEmptyView();
                QuestionListFragmentPresenter.this.mView.renderQuestionList(pQuestionList);
            }
            QuestionListFragmentPresenter.this.mView.hideLoadingView();
        }
    }

}
