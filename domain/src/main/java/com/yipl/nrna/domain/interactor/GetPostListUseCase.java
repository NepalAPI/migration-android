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
        if (mId != Long.MIN_VALUE) {
            if (mDataParent == MyConstants.DataParent.QUESTION) {
                return mRepository.getListByQuestion(mId, MyConstants.Stage.toString
                        (mStage), MyConstants.PostType.toString(mType), mLimit);
            } else if (mDataParent == MyConstants.DataParent.ANSWER) {
                return mRepository.getListByAnswer(mId, MyConstants.Stage.toString
                        (mStage), MyConstants.PostType.toString(mType), mLimit);
            } else {
                return mRepository.getListByCountry(mId, MyConstants.Stage.toString
                        (mStage), MyConstants.PostType.toString(mType), mLimit);
            }
        } else {
            if (mType == null && mStage == null) {
                return mRepository.getList(mLimit);
            } else if (mType != null) {
                return mRepository.getListByType(MyConstants.PostType.toString(mType), mLimit);
            } else if (mStage != null) {
                return mRepository.getListByStage(MyConstants.Stage.toString(mStage), mLimit);
            } else {
                return mRepository.getListByStageAndType(MyConstants.Stage.toString(mStage),
                        MyConstants.PostType.toString(mType), mLimit);
            }
        }
    }
}
