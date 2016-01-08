package com.yipl.nrna.di.module;

import android.content.Context;

import com.yipl.nrna.data.database.DatabaseDao;
import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.AnswerRepository;
import com.yipl.nrna.data.repository.CountryRepository;
import com.yipl.nrna.data.repository.LatestContentRepository;
import com.yipl.nrna.data.repository.PostRepository;
import com.yipl.nrna.data.repository.QuestionRepository;
import com.yipl.nrna.data.repository.TagRepository;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.interactor.GetAnswerListUseCase;
import com.yipl.nrna.domain.interactor.GetCountryDetailUseCase;
import com.yipl.nrna.domain.interactor.GetCountryListUseCase;
import com.yipl.nrna.domain.interactor.GetLatestContentUseCase;
import com.yipl.nrna.domain.interactor.GetPostDetailUseCase;
import com.yipl.nrna.domain.interactor.GetPostListUseCase;
import com.yipl.nrna.domain.interactor.GetQuestionDetailUseCase;
import com.yipl.nrna.domain.interactor.GetQuestionListUseCase;
import com.yipl.nrna.domain.interactor.GetTagListUseCase;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.repository.IBaseRepository;
import com.yipl.nrna.domain.repository.IRepository;
import com.yipl.nrna.domain.repository.QRepository;
import com.yipl.nrna.domain.util.MyConstants;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by julian on 12/7/15.
 */
@Module
public class DataModule {

    private long mLastUpdateStamp = Long.MIN_VALUE;
    private long mId = Long.MIN_VALUE;
    private MyConstants.PostType mPostType = null;
    private MyConstants.Stage mStage = null;
    private MyConstants.DataParent mDataParent = null;
    private int mLimit = -1;

    public DataModule() {
    }

    public DataModule(long pId) {
        mId = pId;
    }

    public DataModule(long pId, MyConstants.DataParent pDataParent, MyConstants.PostType pType) {
        mId = pId;
        mDataParent = pDataParent;
        mPostType = pType;
    }

    public DataModule(long pLastUpdateStamp, int flag) {
        mLastUpdateStamp = pLastUpdateStamp;
    }

    public DataModule(int pLimit) {
        mLimit = pLimit;
    }

    public DataModule(MyConstants.Stage pStage) {
        mStage = pStage;
    }

    public DataModule(MyConstants.PostType pPostType) {
        mPostType = pPostType;
    }

    public DataModule(MyConstants.PostType pPostType, int pLimit) {
        mPostType = pPostType;
        mLimit = pLimit;
    }

    public DataModule(MyConstants.Stage pStage, int pLimit) {
        mStage = pStage;
        mLimit = pLimit;
    }

    public DataModule(MyConstants.Stage pStage, MyConstants.PostType pType, int pLimit) {
        mStage = pStage;
        mPostType = pType;
        mLimit = pLimit;
    }

    @Provides
    @PerActivity
    @Named("latest")
    UseCase provideLatestContentUseCase(@Named("latest") IBaseRepository pDataRepository,
                                        ThreadExecutor pThreadExecutor, PostExecutionThread
                                                pPostExecutionThread) {
        return new GetLatestContentUseCase(mLastUpdateStamp, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("latest")
    IBaseRepository provideLatestContentDataRepository(DataStoreFactory pDataStoreFactory,
                                                       DataMapper
                                                               pDataMapper) {
        return new LatestContentRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("postList")
    UseCase providePostListUseCase(@Named("post") IRepository pDataRepository, ThreadExecutor
            pThreadExecutor,
                                   PostExecutionThread pPostExecutionThread) {
        return new GetPostListUseCase(mLimit, mId, mDataParent, mPostType, mStage, pDataRepository,
                pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("audioList")
    UseCase provideAudioListUseCase(@Named("post") IRepository pDataRepository,
                                    ThreadExecutor pThreadExecutor, PostExecutionThread
                                            pPostExecutionThread) {
        return new GetPostListUseCase(mLimit, mId, mDataParent, MyConstants.PostType.AUDIO,
                mStage, pDataRepository, pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("videoList")
    UseCase provideVideoListUseCase(@Named("post") IRepository pDataRepository,
                                    ThreadExecutor pThreadExecutor, PostExecutionThread
                                            pPostExecutionThread) {
        return new GetPostListUseCase(mLimit, mId, mDataParent, MyConstants.PostType.VIDEO,
                mStage, pDataRepository, pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("articleList")
    UseCase provideArticleListUseCase(@Named("post") IRepository pDataRepository,
                                      ThreadExecutor pThreadExecutor, PostExecutionThread
                                              pPostExecutionThread) {
        return new GetPostListUseCase(mLimit, mId, mDataParent, MyConstants.PostType.TEXT,
                mStage, pDataRepository, pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("postDetails")
    UseCase providePostDetailUseCase(@Named("post") IRepository pDataRepository,
                                     ThreadExecutor pThreadExecutor, PostExecutionThread
                                             pPostExecutionThread) {
        return new GetPostDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("post")
    IRepository providePostDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new PostRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("answer")
    QRepository provideAnswerDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new AnswerRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("answerList")
    UseCase provideAnswerListUseCase(@Named("answer") QRepository pDataRepository,
                                     ThreadExecutor pThreadExecutor, PostExecutionThread
                                             pPostExecutionThread) {
        return new GetAnswerListUseCase(mLimit, mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("countryList")
    UseCase provideCountryListUseCase(@Named("country") IBaseRepository pDataRepository,
                                      ThreadExecutor
                                              pThreadExecutor,
                                      PostExecutionThread pPostExecutionThread) {
        return new GetCountryListUseCase(mLimit, pDataRepository,
                pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("countryDetails")
    UseCase provideCountryDetailUseCase(@Named("country") IBaseRepository pDataRepository,
                                        ThreadExecutor pThreadExecutor, PostExecutionThread
                                                pPostExecutionThread) {
        return new GetCountryDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("country")
    IBaseRepository provideCountryDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new CountryRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("questionList")
    UseCase provideQuestionListUseCase(@Named("question") IBaseRepository pDataRepository,
                                       ThreadExecutor
                                               pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetQuestionListUseCase(mLimit, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("questionDetails")
    UseCase provideQuestionDetailUseCase(@Named("question") IBaseRepository pDataRepository,
                                         ThreadExecutor
                                                 pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetQuestionDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("question")
    IBaseRepository provideQuestionDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new QuestionRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("tag")
    IBaseRepository provideTagDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new TagRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("tagList")
    UseCase provideTagListUseCase(@Named("tag") IBaseRepository pTagRepository,
                                  ThreadExecutor pThreadExecutor,
                                  PostExecutionThread pPostExecutionThread) {
        return new GetTagListUseCase(pTagRepository, pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    DataStoreFactory provideDataStoreFactory(Context pContext, DataMapper pDataMapper, DatabaseDao pDatabaseDao) {
        return new DataStoreFactory(pContext, pDataMapper, pDatabaseDao);
    }
}