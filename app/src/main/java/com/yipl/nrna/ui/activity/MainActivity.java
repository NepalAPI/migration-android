package com.yipl.nrna.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.LatestContentPresenter;
import com.yipl.nrna.ui.fragment.ArticleListFragment;
import com.yipl.nrna.ui.fragment.AudioListFragment;
import com.yipl.nrna.ui.fragment.CountryListFragment;
import com.yipl.nrna.ui.fragment.HomeFragment;
import com.yipl.nrna.ui.fragment.InfoCenterFragment;
import com.yipl.nrna.ui.fragment.PostListFragment;
import com.yipl.nrna.ui.fragment.QuestionListContainerFragment;
import com.yipl.nrna.ui.fragment.VideoListFragment;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.util.Utils;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_COUNTRY;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_FOOTER;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_QUESTION;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_TEXT;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_VIDEO;

public class MainActivity extends BaseActivity implements
        MainActivityView,
        NavigationView.OnNavigationItemSelectedListener,
        ListClickCallbackInterface {

    @Inject
    LatestContentPresenter mLatestContentPresenter;

    /*@Inject
    DeletedContentPresenter mDeletedContentPresenter;*/

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.main_content)
    FrameLayout mMainContent;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, getToolbar(), R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(Utils.getIconColorTint(getResources().getColor(R.color
                .white_alpha_70), Color.WHITE));
        if (savedInstanceState == null) {
            getSupportActionBar().setTitle(getString(R.string.title_home));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_content, new HomeFragment(), "home_fragment")
                    .commit();
            mMainContent.invalidate();
            mNavigationView.getMenu().getItem(0).setChecked(true);
            fetchLatestContent();
        } else {
            getSupportActionBar().setTitle(savedInstanceState.getCharSequence(MyConstants.Extras.KEY_TITLE));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLatestContentPresenter.resume();
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .dataModule(new DataModule())
                .activityModule(getActivityModule())
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
        mLatestContentPresenter.attachView(this);
        //mDeletedContentPresenter.attachView(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLatestContentPresenter.pause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(MyConstants.Extras.KEY_TITLE, getSupportActionBar().getTitle());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sync) {
            fetchLatestContent();
        }
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        showFragment(item.getItemId());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFragment(int id) {
        switch (id) {
            case R.id.nav_home:
                getSupportActionBar().setTitle(getString(R.string.title_home));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new HomeFragment(), "home_fragment")
                        .commit();
                break;
            case R.id.nav_audios:
                getSupportActionBar().setTitle(getString(R.string.title_audio));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new AudioListFragment(), "audio_fragment")
                        .commit();
                break;
            case R.id.nav_videos:
                getSupportActionBar().setTitle(getString(R.string.title_video));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new VideoListFragment(), "video_fragment")
                        .commit();
                break;
            case R.id.nav_articles:
                getSupportActionBar().setTitle(getString(R.string.title_article));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new ArticleListFragment(), "article_fragment")
                        .commit();
                break;
            case R.id.nav_info_center:
                getSupportActionBar().setTitle(getString(R.string.title_info_center));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new InfoCenterFragment(),
                                "info_center_fragment")
                        .commit();
                break;
            case R.id.nav_questions:
                getSupportActionBar().setTitle(getString(R.string.title_questions));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new QuestionListContainerFragment(), "question_list_fragment")
                        .commit();
                break;
            case R.id.nav_countries:
                getSupportActionBar().setTitle(getString(R.string.title_countries));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new CountryListFragment(), "country_fragment")
                        .commit();
                break;
            case R.id.nav_downloads:
                getSupportActionBar().setTitle(getString(R.string.title_downloads));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, PostListFragment.newInstance(Long.MIN_VALUE,
                                null, true), "downloads_fragment")
                        .commit();
                break;
        }
        mMainContent.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLatestContentPresenter.destroy();
        //mDeletedContentPresenter.destroy();
        ButterKnife.unbind(this);
    }

    private void fetchLatestContent() {
        mLatestContentPresenter.initialize();
        //mDeletedContentPresenter.initialize();
    }

    @Override
    public void showLoadingView() {
        Snackbar.make(mMainContent, getString(R.string.message_fetching_latest_content), Snackbar
                .LENGTH_LONG)
                .show();
    }

    @Override
    public void hideLoadingView() {
    }

    @Override
    public void showRetryView() {
    }

    @Override
    public void hideRetryView() {
    }

    @Override
    public void showErrorView(String pErrorMessage) {
        Snackbar.make(mMainContent, pErrorMessage, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action_retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View pView) {
                        fetchLatestContent();
                    }
                }).show();
    }

    @Override
    public void hideErrorView() {
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void hideEmptyView() {
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void informCurrentFragmentForUpdate() {
        ((BaseFragment) getSupportFragmentManager().getFragments().get(0)).showNewContentInfo();
    }

    @Override
    public void onListItemSelected(BaseModel pModel) {
        Intent intent = null;
        switch (pModel.getDataType()) {
            case TYPE_TEXT:
            default:
                intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                break;
            case TYPE_COUNTRY:
                intent = new Intent(this, CountryDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                break;
            case TYPE_QUESTION:
                intent = new Intent(this, QuestionDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                intent.putExtra(MyConstants.Extras.KEY_TITLE, ((Question) pModel).getTitle());
                break;
            case TYPE_VIDEO:
                intent = new Intent(this, VideoDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                intent.putExtra(MyConstants.Extras.KEY_TITLE, ((Post) pModel).getTitle());
                break;
            case TYPE_FOOTER:
                CountryListFragment fragment = ((CountryListFragment) getSupportFragmentManager().getFragments().get(0));
                fragment.showAllCountries();
                return;
        }
        startActivity(intent);
    }

    @Override
    public void onAudioItemSelected(List<Post> pAudios, int index) {
        Intent intent = new Intent(this, AudioDetailActivity.class);
        intent.putExtra(MyConstants.Extras.KEY_AUDIO_LIST, (Serializable) pAudios);
        intent.putExtra(MyConstants.Extras.KEY_AUDIO_SELECTION, index);
        startActivity(intent);
    }

    public void showCaseView() {
        ShowcaseView menuShowcaseView = new ShowcaseView.Builder(this)
                .setStyle(R.style.showcase)
                .setContentTitle(getString(R.string.showcase_setting_title))
                .setContentText(getString(R.string.showcase_setting_detail))
                .withMaterialShowcase()
                .setTarget(new Target() {
                    @Override
                    public Point getPoint() {
                        return new ViewTarget(getToolbar().getChildAt(getToolbar().getChildCount() - 1)).getPoint();
                    }
                })
                .build();
    }
}
