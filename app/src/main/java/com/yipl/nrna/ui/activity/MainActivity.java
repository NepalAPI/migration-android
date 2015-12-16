package com.yipl.nrna.ui.activity;

import android.content.Context;
import android.content.Intent;
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

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.LatestContentPresenter;
import com.yipl.nrna.ui.fragment.ArticleListFragment;
import com.yipl.nrna.ui.fragment.AudioListFragment;
import com.yipl.nrna.ui.fragment.CountryListFragment;
import com.yipl.nrna.ui.fragment.HomeFragment;
import com.yipl.nrna.ui.fragment.InfoCenterFragment;
import com.yipl.nrna.ui.fragment.QuestionListFragment;
import com.yipl.nrna.ui.fragment.VideoListFragment;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.ui.interfaces.MainActivityView;
import com.yipl.nrna.util.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.*;

public class MainActivity extends BaseActivity implements
        MainActivityView,
        NavigationView.OnNavigationItemSelectedListener,
        ListClickCallbackInterface{

    @Inject
    LatestContentPresenter mLatestContentPresenter;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.main_content)
    FrameLayout mMainContent;

    Snackbar mSnackbar;

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

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_content, new HomeFragment(), "home_fragment")
                .commit();

        fetchLatestContent();
    }

    private void initialize(){
        DaggerDataComponent.builder()
                .activityModule(getActivityModule())
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
        mLatestContentPresenter.attachView(this);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new HomeFragment(), "home_fragment")
                        .commit();
                break;
            case R.id.nav_audios:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new AudioListFragment(), "audio_fragment")
                        .commit();
                break;
            case R.id.nav_videos:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new VideoListFragment(), "video_fragment")
                        .commit();
                break;
            case R.id.nav_articles:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new ArticleListFragment(), "article_fragment")
                        .commit();
                break;
            case R.id.nav_info_center:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new InfoCenterFragment(),
                                "info_center_fragment")
                        .commit();
                break;
            case R.id.nav_questions:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new QuestionListFragment(), "question_fragment")
			.commit();
		break;
            case R.id.nav_countries:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_content, new CountryListFragment(), "country_fragment")
                        .commit();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLatestContentPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLatestContentPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLatestContentPresenter.destroy();
        ButterKnife.unbind(this);
    }

    private void fetchLatestContent(){
        mLatestContentPresenter.initialize();
    }

    @Override
    public void showLoadingView() {
        mSnackbar = Snackbar.make(mMainContent, getString(R.string
                .message_fetching_latest_content), Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }

    @Override
    public void hideLoadingView() {
        if(mSnackbar != null)
            mSnackbar.dismiss();
    }

    @Override
    public void showErrorView(String pErrorMessage) {
        mSnackbar = Snackbar.make(mMainContent, pErrorMessage, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction(getString(R.string.action_retry), new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                fetchLatestContent();
            }
        });
    }

    @Override
    public void hideErrorView() {
        mSnackbar.dismiss();
    }

    @Override
    public void informCurrentFragmentForUpdate() {
        Logger.d("MainActivity_informCurrentFragmentForUpdate", "send info");
        ((BaseFragment) getSupportFragmentManager().getFragments().get(0)).showNewContentInfo();
    }

    @Override
    public void onListItemSelected(BaseModel pModel) {
        Intent intent;
        switch (pModel.getDataType()){
            case TYPE_TEXT:
                intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                startActivity(intent);
                break;
            case TYPE_COUNTRY:
                Intent countryIntent = new Intent(this, CountryDetailActivity.class);
                countryIntent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                startActivity(countryIntent);
                break;
            case TYPE_QUESTION:
                intent = new Intent(this, QuestionDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                intent.putExtra(MyConstants.Extras.KEY_TITLE, ((Question)pModel).getQuestion());
                startActivity(intent);
        }
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void hideEmptyView() {
    }

    @Override
    public void showRetryView() {
    }

    @Override
    public void hideRetryView() {
    }

    @Override
    public Context getContext() {
        return this;
    }
}
