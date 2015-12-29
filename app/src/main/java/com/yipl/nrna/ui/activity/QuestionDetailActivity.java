package com.yipl.nrna.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.ui.adapter.QuestionAnswerPagerAdapter;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.util.Logger;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_TEXT;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_VIDEO;

public class QuestionDetailActivity extends BaseActivity implements ListClickCallbackInterface {

    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.title)
    TextView mTitle;

    Long mId;
    QuestionAnswerPagerAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_question_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Logger.d(bundle.getLong(MyConstants.Extras.KEY_ID) + "/" + bundle.getString(MyConstants.Extras.KEY_TITLE));
            mId = bundle.getLong(MyConstants.Extras.KEY_ID);
            mTitle.setText(bundle.getString(MyConstants.Extras.KEY_TITLE));
        }
        mAdapter = new QuestionAnswerPagerAdapter(getSupportFragmentManager(), this, mId);
        mViewPager.setAdapter(mAdapter);

        mTabs.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabs.getTabCount(); i++) {
            TabLayout.Tab tab = mTabs.getTabAt(i);
            tab.setCustomView(mAdapter.getTabView(i));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(getActivityModule())
                .dataModule(new DataModule())
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onListItemSelected(BaseModel pModel) {
        Intent intent;
        switch (pModel.getDataType()) {
            case TYPE_TEXT:
            default:
                intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                startActivity(intent);
                break;
            case TYPE_VIDEO:
                intent = new Intent(this, VideoDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                intent.putExtra(MyConstants.Extras.KEY_TITLE, ((Post) pModel).getTitle());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onAudioItemSelected(List<Post> pAudios, int index) {
        Intent intent = new Intent(this, AudioDetailActivity.class);
        intent.putExtra(MyConstants.Extras.KEY_AUDIO_LIST, (Serializable) pAudios);
        intent.putExtra(MyConstants.Extras.KEY_AUDIO_SELECTION, index);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
