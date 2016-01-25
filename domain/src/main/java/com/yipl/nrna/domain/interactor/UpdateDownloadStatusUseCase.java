package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Inject;

import rx.Observable;

public class UpdateDownloadStatusUseCase extends UseCase<Boolean> {

    private final IRepository mRepository;
    private long mId;
    private boolean mDownloadStatus;

    @Inject
    public UpdateDownloadStatusUseCase(long pId, boolean pDownloadStatus, IRepository pRepository,
                                       ThreadExecutor
                                               pThreadExecutor, PostExecutionThread
                                               pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        this.mId = pId;
        mDownloadStatus = pDownloadStatus;
        mRepository = pRepository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        return mRepository.updateDownloadStatus(mId, mDownloadStatus);
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
