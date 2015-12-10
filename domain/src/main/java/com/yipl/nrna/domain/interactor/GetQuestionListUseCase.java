package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetQuestionListUseCase extends UseCase<List<Question>> {

    private final IRepository mRepository;

    @Inject
    public GetQuestionListUseCase(IRepository pRepository, ThreadExecutor
                                     pThreadExecutor, PostExecutionThread
            pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
    }

    @Override
    protected Observable<List<Question>> buildUseCaseObservable() {
        return mRepository.getList();
    }
}
