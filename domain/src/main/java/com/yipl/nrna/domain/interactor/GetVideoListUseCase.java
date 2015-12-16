package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetVideoListUseCase extends UseCase<List<Post>> {

    private final IRepository mRepository;
    private final Long mQuestionId;

    @Inject
    public GetVideoListUseCase(Long pId, IRepository pRepository, ThreadExecutor
                                     pThreadExecutor, PostExecutionThread
            pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mQuestionId = pId;
    }

    @Override
    protected Observable<List<Post>> buildUseCaseObservable() {
        if(mQuestionId == Long.MIN_VALUE)
            return mRepository.getList(-1);
        else
            return mRepository.getListByQuestionAndType(mQuestionId);
    }
}
