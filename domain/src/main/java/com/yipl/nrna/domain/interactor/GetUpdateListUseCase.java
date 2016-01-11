package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.CountryUpdate;
import com.yipl.nrna.domain.repository.CRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetUpdateListUseCase extends UseCase<List<CountryUpdate>> {

    private final CRepository mRepository;
    private final int mLimit;
    private final Long mCountryId;

    @Inject
    public GetUpdateListUseCase(int pLimit, Long pCountryId, CRepository pRepository,
                                ThreadExecutor pThreadExecutor, PostExecutionThread
                                        pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mLimit = pLimit;
        mCountryId = pCountryId;
    }

    @Override
    protected Observable<List<CountryUpdate>> buildUseCaseObservable() {
        if (mCountryId != Long.MIN_VALUE) {
            return mRepository.getListByCountry(mCountryId, mLimit);
        } else {
            return mRepository.getList(mLimit);
        }
    }
}
