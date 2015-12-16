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
    private final Long mQuestionId;
    private final int mLimit;

    @Inject
    public GetArticleListUseCase(Long pId, int pLimit, IRepository pRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread
                                         pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mQuestionId = pId;
        mLimit = pLimit;
    }


    @Override
    protected Observable<List<Post>> buildUseCaseObservable() {
        if (mQuestionId != Long.MIN_VALUE)
            return mRepository.getListByQuestionAndType(mQuestionId);
        else
            return mRepository.getList(mLimit);
    }
}
