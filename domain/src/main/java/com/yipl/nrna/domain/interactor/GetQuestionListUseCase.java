package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetQuestionListUseCase extends UseCase<List<Question>> {

    private final IBaseRepository mRepository;
    private final int mLimit;

    @Inject
    public GetQuestionListUseCase(int pLimit, IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mLimit = pLimit;
    }

    @Override
    protected Observable<List<Question>> buildUseCaseObservable() {
        return mRepository.getList(mLimit);
    }
}
