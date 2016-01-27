package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.repository.IBaseRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nirazan-PC on 1/26/2016.
 */
public class GetUserPreferenceUseCase extends UseCase {

    private IBaseRepository mIBaseRepository;

    @Inject
    public GetUserPreferenceUseCase(IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mIBaseRepository = pRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mIBaseRepository.getSingle(-1L);
    }

    @Override
    protected Observable buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
