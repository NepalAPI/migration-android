package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.repository.IBaseRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/22/2015.
 */
public class GetTagListUseCase extends UseCase<String> {

    private final IBaseRepository mIRepository;

    @Inject
    public GetTagListUseCase(IBaseRepository pIRepository, ThreadExecutor pThreadExecutor,
                             PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mIRepository = pIRepository;
    }

    @Override
    protected Observable<String> buildUseCaseObservable() {
        return mIRepository.getList(-1);
    }

    @Override
    protected Observable buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
