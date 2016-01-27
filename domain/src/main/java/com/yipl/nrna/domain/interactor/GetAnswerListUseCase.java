package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Answer;
import com.yipl.nrna.domain.repository.QRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetAnswerListUseCase extends UseCase<List<Answer>> {

    private final QRepository mRepository;
    private final int mLimit;
    private final Long mQuestionId;

    @Inject
    public GetAnswerListUseCase(int pLimit, Long pQuestionId, QRepository pRepository,
                                ThreadExecutor pThreadExecutor, PostExecutionThread
                                        pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mLimit = pLimit;
        mQuestionId = pQuestionId;
    }

    @Override
    protected Observable<List<Answer>> buildUseCaseObservable() {
        if (mQuestionId != Long.MIN_VALUE) {
            return mRepository.getListByQuestion(mQuestionId, mLimit);
        } else {
            return mRepository.getList(mLimit);
        }
    }

    @Override
    protected Observable<List<Answer>> buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
