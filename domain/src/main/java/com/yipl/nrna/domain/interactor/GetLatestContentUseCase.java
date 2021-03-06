package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.repository.IBaseRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetLatestContentUseCase extends UseCase<LatestContent> {

    private final IBaseRepository mLatestContentRepository;

    @Inject
    public GetLatestContentUseCase(IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mLatestContentRepository = pRepository;
    }

    @Override
    protected Observable<LatestContent> buildUseCaseObservable() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Observable buildUseCaseObservable(long reference) {
        return mLatestContentRepository.getSingle(reference);
    }
}
