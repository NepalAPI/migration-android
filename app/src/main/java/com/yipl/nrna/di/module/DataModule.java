package com.yipl.nrna.di.module;

import dagger.Module;

/**
 * Created by julian on 12/7/15.
 */
@Module
public class DataModule {

    /*@Provides
    @PerActivity
    UseCase provideUseCase(IRepository pDataRepository,ThreadExecutor pThreadExecutor,
                           PostExecutionThread pPostExecutionThread) {
        return new GetLatestContentUseCase(pDataRepository, pThreadExecutor, pPostExecutionThread);
    }

    @Provides
    @PerActivity
    IRepository provideDataRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        return new LatestContentRepository(pDataStoreFactory, pDataMapper);
    }

    @Provides
    @PerActivity
    DataStoreFactory provideDataStoreFactory(Context pContext, DataMapper pDataMapper){
        return new DataStoreFactory(pContext, pDataMapper);
    }*/
}