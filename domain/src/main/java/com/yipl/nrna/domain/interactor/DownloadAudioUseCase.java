package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Inject;

import rx.Observable;

public class DownloadAudioUseCase extends UseCase<Boolean> {

    private final IRepository mRepository;
    private long mId;

    @Inject
    public DownloadAudioUseCase(long pId, IRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread
                                        pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        this.mId = pId;
        mRepository = pRepository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(long reference) {
        return mRepository.setDownloadReference(mId, reference);
    }
}
