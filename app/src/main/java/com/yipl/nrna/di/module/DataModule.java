package com.yipl.nrna.di.module;

import android.content.Context;

import com.yipl.nrna.data.Database.DatabaseDao;
import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.ArticleRepository;
import com.yipl.nrna.data.repository.AudioRepository;
import com.yipl.nrna.data.repository.CountryRepository;
import com.yipl.nrna.data.repository.LatestContentRepository;
import com.yipl.nrna.data.repository.PostRepository;
import com.yipl.nrna.data.repository.QuestionRepository;
import com.yipl.nrna.data.repository.VideoRepository;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.interactor.GetArticleDetailUseCase;
import com.yipl.nrna.domain.interactor.GetArticleListUseCase;
import com.yipl.nrna.domain.interactor.GetAudioDetailUseCase;
import com.yipl.nrna.domain.interactor.GetAudioListUseCase;
import com.yipl.nrna.domain.interactor.GetCountryDetailUseCase;
import com.yipl.nrna.domain.interactor.GetCountryListUseCase;
import com.yipl.nrna.domain.interactor.GetLatestContentUseCase;
import com.yipl.nrna.domain.interactor.GetPostDetailUseCase;
import com.yipl.nrna.domain.interactor.GetPostListUseCase;
import com.yipl.nrna.domain.interactor.GetQuestionDetailUseCase;
import com.yipl.nrna.domain.interactor.GetQuestionListUseCase;
import com.yipl.nrna.domain.interactor.GetVideoDetailUseCase;
import com.yipl.nrna.domain.interactor.GetVideoListUseCase;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.repository.IRepository;
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
    private int mLimit = -1;

    public DataModule() {
    }

    public DataModule(long pId) {
        mId = pId;
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
    UseCase provideLatestContentUseCase(@Named("latest") IRepository pDataRepository,
                                        ThreadExecutor pThreadExecutor, PostExecutionThread
                                                pPostExecutionThread) {
        return new GetLatestContentUseCase(mLastUpdateStamp, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("latest")
    IRepository provideLatestContentDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new LatestContentRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("postList")
    UseCase providePostListUseCase(@Named("post") IRepository pDataRepository, ThreadExecutor
            pThreadExecutor,
                                   PostExecutionThread pPostExecutionThread) {
        return new GetPostListUseCase(mLimit, mPostType, mStage, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
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
    @Named("countryList")
    UseCase provideCountryListUseCase(@Named("country") IRepository pDataRepository, ThreadExecutor
            pThreadExecutor,
                                      PostExecutionThread pPostExecutionThread) {
        return new GetCountryListUseCase(mLimit, pDataRepository,
                pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("countryDetails")
    UseCase provideCountryDetailUseCase(@Named("country") IRepository pDataRepository,
                                        ThreadExecutor pThreadExecutor, PostExecutionThread
                                                pPostExecutionThread) {
        return new GetCountryDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("country")
    IRepository provideCountryDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new CountryRepository(pDataStoreFactory, pDataMapper);
    }


    @Provides
    @PerActivity
    @Named("questionList")
    UseCase provideQuestionListUseCase(@Named("question") IRepository pDataRepository,
                                       ThreadExecutor
                                               pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetQuestionListUseCase(mLimit, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("questionDetails")
    UseCase provideQuestionDetailUseCase(@Named("question") IRepository pDataRepository,
                                         ThreadExecutor
                                                 pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetQuestionDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("question")
    IRepository provideQuestionDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new QuestionRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("audioList")
    UseCase provideAudioListUseCase(@Named("audio") IRepository pDataRepository,
                                    ThreadExecutor pThreadExecutor, PostExecutionThread
                                            pPostExecutionThread) {
        return new GetAudioListUseCase(mId, mLimit, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("audioDetails")
    UseCase provideAudioDetailUseCase(@Named("audio") IRepository pDataRepository,
                                      ThreadExecutor pThreadExecutor, PostExecutionThread
                                              pPostExecutionThread) {
        return new GetAudioDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("audio")
    IRepository provideAudioDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new AudioRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("videoList")
    UseCase provideVideoListUseCase(@Named("video") IRepository pDataRepository,
                                    ThreadExecutor pThreadExecutor, PostExecutionThread
                                            pPostExecutionThread) {
        return new GetVideoListUseCase(mId, mLimit, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("videoDetails")
    UseCase provideVideoDetailUseCase(@Named("video") IRepository pDataRepository,
                                      ThreadExecutor pThreadExecutor, PostExecutionThread
                                              pPostExecutionThread) {
        return new GetVideoDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("video")
    IRepository provideVideoDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new VideoRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("articleList")
    UseCase provideArticleListUseCase(@Named("article") IRepository pDataRepository,
                                      ThreadExecutor pThreadExecutor, PostExecutionThread
                                              pPostExecutionThread) {
        return new GetArticleListUseCase(mId, mLimit, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("article")
    IRepository provideArticleDataRepository(DataStoreFactory pDataStoreFactory, DataMapper
            pDataMapper) {
        return new ArticleRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    @Named("articleDetails")
    UseCase provideArticleDetailUseCase(@Named("article") IRepository pDataRepository,
                                        ThreadExecutor pThreadExecutor, PostExecutionThread
                                                pPostExecutionThread) {
        return new GetArticleDetailUseCase(mId, pDataRepository, pThreadExecutor,
                pPostExecutionThread);
    }

    @Provides
    @PerActivity
    DataStoreFactory provideDataStoreFactory(Context pContext, DataMapper pDataMapper, DatabaseDao pDatabaseDao) {
        return new DataStoreFactory(pContext, pDataMapper, pDatabaseDao);
    }
}