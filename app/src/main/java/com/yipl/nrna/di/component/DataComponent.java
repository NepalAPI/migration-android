package com.yipl.nrna.di.component;

import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.di.module.ActivityModule;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by julian on 12/7/15.
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, DataModule.class}
)
public interface DataComponent extends ActivityComponent {
    void inject(HomeFragment pFragment);
}
