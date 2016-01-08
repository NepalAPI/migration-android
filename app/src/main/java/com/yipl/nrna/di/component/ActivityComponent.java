package com.yipl.nrna.di.component;

import android.app.Activity;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.di.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {
    Activity getActivity();
}
