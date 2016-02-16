package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.repository.IBaseRepository;
import com.yipl.nrna.domain.util.MyConstants;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetQuestionListUseCase extends UseCase<List<Question>> {

    private final IBaseRepository mRepository;
    private final int mLimit;
    private final MyConstants.Stage mStage;

    @Inject
    public GetQuestionListUseCase(int pLimit,MyConstants.Stage pStage, IBaseRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mLimit = pLimit;
        mStage = pStage;
    }

    @Override
    protected Observable<List<Question>> buildUseCaseObservable() {
        if(mStage!= null)
            return mRepository.getListByStage(MyConstants.Stage.toString(mStage), mLimit);
        return mRepository.getList(mLimit);
    }

    @Override
    protected Observable buildUseCaseObservable(long reference) {
        throw new UnsupportedOperationException();
    }
}
