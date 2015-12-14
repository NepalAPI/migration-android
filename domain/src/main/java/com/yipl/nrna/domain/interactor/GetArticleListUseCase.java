package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class GetArticleListUseCase extends UseCase<List<Post>> {

    private final IRepository mRepository;

    @Inject
    public GetArticleListUseCase(IRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread
                                       pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
    }


    @Override
    protected Observable<List<Post>> buildUseCaseObservable() {
        return mRepository.getList(-1);
    }
}
