package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetLatestContentUseCase extends UseCase<LatestContent> {

    private final IRepository mLatestContentRepository;
    private final long mLastUpdatedStamp;

    @Inject
    public GetLatestContentUseCase(Long pLastUpdatedStamp, IRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread
                                           pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mLatestContentRepository = pRepository;
        mLastUpdatedStamp = pLastUpdatedStamp;
    }

    @Override
    protected Observable<LatestContent> buildUseCaseObservable() {
        return mLatestContentRepository.getSingle(mLastUpdatedStamp);
    }
}
