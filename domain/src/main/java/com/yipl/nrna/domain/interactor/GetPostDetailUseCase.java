package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Inject;

import rx.Observable;

public class GetPostDetailUseCase extends UseCase<Post> {

    private final IRepository mRepository;
    private long mId;

    @Inject
    public GetPostDetailUseCase(long pId, IRepository pRepository, ThreadExecutor
                                     pThreadExecutor, PostExecutionThread
            pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        this.mId = pId;
        mRepository = pRepository;
    }

    @Override
    protected Observable<Post> buildUseCaseObservable() {
        return mRepository.getSingle(mId);
    }
}
