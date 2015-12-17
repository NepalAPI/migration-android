package com.yipl.nrna.di.component;

import android.content.Context;

import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.data.Database.DatabaseHelper;
import com.yipl.nrna.di.module.ApplicationModule;
import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;
import com.yipl.nrna.media.MediaService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity pBaseActivity);

    void inject(MediaService pMediaService);

    Context getContext();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    DatabaseHelper getDatabaseHelper();
}
