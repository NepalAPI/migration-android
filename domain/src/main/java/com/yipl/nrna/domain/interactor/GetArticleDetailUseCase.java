package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class GetArticleDetailUseCase extends UseCase<Post> {

    private final IRepository mRepository;
    Long mId;

    @Inject
    public GetArticleDetailUseCase(Long pId, IRepository pRepository, ThreadExecutor pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mId = pId;
    }

    @Override
    protected Observable<Post> buildUseCaseObservable() {
        return mRepository.getSingle(mId);
    }
}
