package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.repository.IBaseRepository;

import javax.inject.Inject;

import rx.Observable;

public class DeleteContentUseCase extends UseCase<Boolean> {

    private final IBaseRepository mRepository;

    @Inject
    public DeleteContentUseCase(IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(long reference) {
        return mRepository.getSingle(reference);
    }
}
