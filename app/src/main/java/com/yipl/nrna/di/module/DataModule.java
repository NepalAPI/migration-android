package com.yipl.nrna.di.module;

import android.content.Context;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.LatestContentRepository;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.domain.interactor.GetLatestContentUseCase;
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

    @Provides
    @PerActivity
    @Named("latest")
    UseCase provideUseCase(@Named("latest") IRepository pDataRepository, ThreadExecutor
            pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        return new GetLatestContentUseCase(pDataRepository, pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("latest")
    IRepository provideDataRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        return new LatestContentRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    DataStoreFactory provideDataStoreFactory(Context pContext, DataMapper pDataMapper){
        return new DataStoreFactory(pContext, pDataMapper);
    }
}