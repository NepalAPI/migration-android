package com.yipl.nrna;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.yipl.nrna.data.database.DatabaseHelper;
import com.yipl.nrna.di.component.ApplicationComponent;
import com.yipl.nrna.di.component.DaggerApplicationComponent;
import com.yipl.nrna.di.module.ApplicationModule;

public class MyApplication extends Application {
    ApplicationComponent mApplicationComponent;

    DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        Fresco.initialize(this);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        getApplicationComponent().getDatabaseHelper();
    }

    @Override
    public void onTerminate() {
        databaseHelper.close();
        super.onTerminate();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
