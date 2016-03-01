package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.DownloadItem;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetCurrentDownloadsUseCase extends UseCase<List<DownloadItem>> {

    private final IRepository mRepository;

    @Inject
    public GetCurrentDownloadsUseCase(IRepository pRepository, ThreadExecutor pThreadExecutor,
                                      PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
    }

    @Override
    protected Observable<List<DownloadItem>> buildUseCaseObservable() {
        return mRepository.getCurrentDownloadList();
    }

    @Override
    protected Observable<List<DownloadItem>> buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
