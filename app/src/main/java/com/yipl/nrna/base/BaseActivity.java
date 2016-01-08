package com.yipl.nrna.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yipl.nrna.MyApplication;
import com.yipl.nrna.R;
import com.yipl.nrna.di.component.ApplicationComponent;
import com.yipl.nrna.di.module.ActivityModule;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.util.AppPreferences;
import com.yipl.nrna.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    AppPreferences mPreferences;

    public abstract int getLayout();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initLanguage();
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initializeToolbar();

        mPreferences = new AppPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLanguage();
    }

    private void initLanguage() {
        Utils.setLanguage(MyConstants.Language.NEPALI, getApplicationContext());
    }

    private void initializeToolbar() {
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("Layout is required to include a Toolbar with id " +
                    "'toolbar'");
        }
        setUpToolbarMenu();
        setSupportActionBar(mToolbar);
    }

    public void setUpToolbarMenu() {
        //mToolbar.inflateMenu(R.menu.main);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    public AppPreferences getPreferences() {
        return mPreferences;
    }

    public ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
