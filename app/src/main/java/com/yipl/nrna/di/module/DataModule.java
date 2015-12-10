package com.yipl.nrna.di.module;

import android.content.Context;

import com.yipl.nrna.data.Database.DatabaseDao;
import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.AudioRepository;
import com.yipl.nrna.data.repository.LatestContentRepository;
import com.yipl.nrna.data.repository.QuestionRepository;
import com.yipl.nrna.data.repository.VideoRepository;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.interactor.GetAudioDetailUseCase;
import com.yipl.nrna.domain.interactor.GetAudioListUseCase;
import com.yipl.nrna.domain.interactor.GetLatestContentUseCase;
import com.yipl.nrna.domain.interactor.GetQuestionDetailUseCase;
import com.yipl.nrna.domain.interactor.GetQuestionListUseCase;
import com.yipl.nrna.domain.interactor.GetVideoDetailUseCase;
import com.yipl.nrna.domain.interactor.GetVideoListUseCase;
import com.yipl.nrna.domain.interactor.UseCase;
import com.yipl.nrna.domain.repository.IRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by julian on 12/7/15.
 */
@Module
public class DataModule {

    private long mId = Long.MIN_VALUE;

    @Provides
    @PerActivity
    @Named("latest")
    UseCase provideLatestContentUseCase(@Named("latest") IRepository pDataRepository,
                                        ThreadExecutor
                                                pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetLatestContentUseCase(pDataRepository, pThreadExecutor, pPostExecutionThread);
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
    @Named("questionList")
    UseCase provideQuestionListUseCase(@Named("question") IRepository pDataRepository,
                                       ThreadExecutor
                                               pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetQuestionListUseCase(pDataRepository, pThreadExecutor, pPostExecutionThread);
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
        return new GetAudioListUseCase(pDataRepository, pThreadExecutor, pPostExecutionThread);
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
        return new GetVideoListUseCase(pDataRepository, pThreadExecutor, pPostExecutionThread);
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
    DataStoreFactory provideDataStoreFactory(Context pContext, DataMapper pDataMapper, DatabaseDao pDatabaseDao) {
        return new DataStoreFactory(pContext, pDataMapper, pDatabaseDao);
    }
}