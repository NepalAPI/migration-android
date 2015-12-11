package com.yipl.nrna.presenter;

import com.yipl.nrna.domain.exception.DefaultErrorBundle;
import com.yipl.nrna.domain.interactor.DefaultSubscriber;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.exception.ErrorMessageFactory;
import com.yipl.nrna.ui.interfaces.HomeFragmentView;
import com.yipl.nrna.ui.interfaces.MvpView;
import com.yipl.nrna.util.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by julian on 12/9/15.
 */
public class HomeFragmentPresenter implements Presenter {
    public static final int FLAG_QUESTION = 0;
    public static final int FLAG_AUDIO = 1;
    public static final int FLAG_VIDEO = 2;

    HomeFragmentView mView;
    private final UseCase mQuestionUseCase;
    private final UseCase mAudioUseCase;
    private final UseCase mVideoUseCase;

    @Inject
    public HomeFragmentPresenter(@Named("questionList") UseCase pQuestionUseCase,
                                 @Named("audioList") UseCase pAudioUseCase,
                                 @Named("videoList") UseCase pVideoUseCase) {
        mQuestionUseCase = pQuestionUseCase;
        mAudioUseCase = pAudioUseCase;
        mVideoUseCase = pVideoUseCase;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        mQuestionUseCase.unSubscribe();
    }

    @Override
    public void initialize() {
        loadLatestContent();
    }

    @Override
    public void attachView(MvpView pView) {
        mView = (HomeFragmentView) pView;
    }

    private void loadLatestContent() {
        getLatestQuestions();
        getLatestAudio();
        getLatestVideo();
    }

    private void getLatestQuestions() {
        mView.hideRetryView(FLAG_QUESTION);
        mView.showLoadingView(FLAG_QUESTION);
        this.mQuestionUseCase.execute(new QuestionSubscriber());
    }

    private void getLatestAudio() {
        mView.hideRetryView(FLAG_AUDIO);
        mView.showLoadingView(FLAG_AUDIO);
        this.mAudioUseCase.execute(new AudioSubscriber());
    }

    private void getLatestVideo() {
        mView.hideRetryView(FLAG_VIDEO);
        mView.showLoadingView(FLAG_VIDEO);
        this.mVideoUseCase.execute(new VideoSubscriber());
    }

    private final class QuestionSubscriber extends DefaultSubscriber<List<Question>> {

        @Override
        public void onCompleted() {
            HomeFragmentPresenter.this.mView.hideLoadingView(FLAG_AUDIO);
        }

        @Override
        public void onError(Throwable e) {
            HomeFragmentPresenter.this.mView.hideLoadingView(FLAG_QUESTION);
            HomeFragmentPresenter.this.mView.showEmptyView(FLAG_QUESTION);
            Logger.e("QuestionSubscriber_onError", e.getLocalizedMessage());
            HomeFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            HomeFragmentPresenter.this.mView.showRetryView(FLAG_QUESTION);
        }

        @Override
        public void onNext(List<Question> pQuestions) {
            if (pQuestions.isEmpty()) {
                HomeFragmentPresenter.this.mView.showEmptyView(MyConstants.Adapter.TYPE_QUESTION);
            } else {
                HomeFragmentPresenter.this.mView.renderLatestQuestions(pQuestions);
            }
        }
    }

    private final class AudioSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            HomeFragmentPresenter.this.mView.hideLoadingView(FLAG_AUDIO);
        }

        @Override
        public void onError(Throwable e) {
            HomeFragmentPresenter.this.mView.hideLoadingView(FLAG_AUDIO);
            HomeFragmentPresenter.this.mView.showEmptyView(FLAG_AUDIO);
            Logger.e("AudioSubscriber_onError", e.getLocalizedMessage());
            HomeFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            HomeFragmentPresenter.this.mView.showRetryView(FLAG_AUDIO);
        }

        @Override
        public void onNext(List<Post> pPosts) {
            if (!pPosts.isEmpty()) {
                HomeFragmentPresenter.this.mView.renderLatestAudios(pPosts);
            } else {
                HomeFragmentPresenter.this.mView.showEmptyView(FLAG_AUDIO);
            }
        }
    }

    private final class VideoSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {
            HomeFragmentPresenter.this.mView.hideLoadingView(FLAG_VIDEO);
        }

        @Override
        public void onError(Throwable e) {
            HomeFragmentPresenter.this.mView.hideLoadingView(FLAG_VIDEO);
            HomeFragmentPresenter.this.mView.showEmptyView(FLAG_VIDEO);
            Logger.e("VideoSubscriber_onError", e.getLocalizedMessage());
            HomeFragmentPresenter.this.mView.showErrorView(ErrorMessageFactory.create(mView
                    .getContext(), new DefaultErrorBundle((Exception) e).getException()));
            HomeFragmentPresenter.this.mView.showRetryView(FLAG_VIDEO);
        }

        @Override
        public void onNext(List<Post> pVideos) {
            if (!pVideos.isEmpty()) {
                HomeFragmentPresenter.this.mView.renderLatestVideos(pVideos);
            } else {
                HomeFragmentPresenter.this.mView.showEmptyView(FLAG_AUDIO);
            }
        }
    }
}
