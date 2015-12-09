package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetLatestContentUseCase extends UseCase<LatestContent> {

    private final IRepository mLatestContentRepository;

    @Inject
    public GetLatestContentUseCase(IRepository pRepository, ThreadExecutor
                                     pThreadExecutor, PostExecutionThread
            pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mLatestContentRepository = pRepository;
    }

    @Override
    protected Observable<LatestContent> buildUseCaseObservable() {
        // TODO: 12/8/15 get last update timestamp
        return mLatestContentRepository.getSingle(14354637L);
    }
}
