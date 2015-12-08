package com.yipl.nrna;

import android.app.Application;

import com.yipl.nrna.di.component.ApplicationComponent;
import com.yipl.nrna.di.component.DaggerApplicationComponent;
import com.yipl.nrna.di.module.ApplicationModule;

public class MyApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
