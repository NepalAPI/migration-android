package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;
import com.yipl.nrna.domain.util.MyConstants;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class GetPostListUseCase extends UseCase<List<Post>> {

    private final IRepository mRepository;
    private final int mLimit;
    private final MyConstants.PostType mType;
    private final MyConstants.Stage mStage;
    private final Long mId;
    private final MyConstants.DataParent mDataParent;

    @Inject
    public GetPostListUseCase(int pLimit, Long pId, MyConstants.DataParent pDataParent,
                              MyConstants.PostType pType, MyConstants.Stage pStage,
                              IRepository pRepository, ThreadExecutor pThreadExecutor,
                              PostExecutionThread pPostExecutionThread) {
        super(pThreadExecutor, pPostExecutionThread);
        mRepository = pRepository;
        mLimit = pLimit;
        mType = pType;
        mStage = pStage;
        mId = pId;
        mDataParent = pDataParent;
    }

    @Override
    protected Observable<List<Post>> buildUseCaseObservable() {
        if (mId != null) {
            if (mDataParent == MyConstants.DataParent.QUESTION) {
                return mRepository.getListByQuestionAndType(mId);
            } else if (mDataParent == MyConstants.DataParent.ANSWER) {
                return mRepository.getListByAnswer(mId, mLimit);
            } else {
                return mRepository.getListByCountry(mId);
            }
        }
        if (mType == null && mStage == null) {
            return mRepository.getList(mLimit);
        } else if (mType != null) {
            return mRepository.getListByType(mLimit, MyConstants.PostType.toString(mType));
        } else if (mStage != null) {
            return mRepository.getListByStage(mLimit, MyConstants.Stage.toString(mStage));
        } else {
            return mRepository.getListByStageAndType(mLimit, MyConstants.PostType.toString(mType),
                    MyConstants.Stage.toString(mStage));
        }
    }
}
