package com.yipl.nrna.di.module;

import android.content.Context;

import com.yipl.nrna.MyApplication;
import com.yipl.nrna.data.database.DatabaseDao;
import com.yipl.nrna.data.database.DatabaseHelper;
import com.yipl.nrna.data.executor.JobExecutor;
import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.util.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final MyApplication mApplication;

    public ApplicationModule(MyApplication pApplication) {
        this.mApplication = pApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }


    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    DatabaseDao provideDatabaseDao(DatabaseHelper databaseHelper) {
        return new DatabaseDao(databaseHelper);
    }
}
