package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.repository.IBaseRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetQuestionDetailUseCase extends UseCase<Question> {

    private final IBaseRepository mRepository;
    private long mId;

    @Inject
    public GetQuestionDetailUseCase(long pId, IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread
                                            pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        this.mId = pId;
        mRepository = pRepository;
    }

    @Override
    protected Observable<Question> buildUseCaseObservable() {
        return mRepository.getSingle(mId);
    }

    @Override
    protected Observable<Question> buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
