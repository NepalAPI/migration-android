package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetCountryListUseCase extends UseCase<List<Country>> {

    private final IBaseRepository mRepository;
    private final int mLimit;

    @Inject
    public GetCountryListUseCase(int pLimit, IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread
                                         pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mLimit = pLimit;
    }

    @Override
    protected Observable<List<Country>> buildUseCaseObservable() {
        return mRepository.getList(mLimit);
    }
}
