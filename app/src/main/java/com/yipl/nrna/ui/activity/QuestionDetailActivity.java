package com.yipl.nrna.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.ui.adapter.QuestionAnswerPagerAdapter;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.util.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_QUESTION;
import static com.yipl.nrna.domain.util.MyConstants.Adapter.TYPE_TEXT;

public class QuestionDetailActivity extends BaseActivity implements ListClickCallbackInterface {

    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabs;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Logger.d(bundle.getLong(MyConstants.Extras.KEY_ID) + "/" + bundle.getString(MyConstants.Extras.KEY_TITLE));
            mId = bundle.getLong(MyConstants.Extras.KEY_ID);
            getSupportActionBar().setTitle(bundle.getString(MyConstants.Extras.KEY_TITLE));
        }
        mAdapter = new QuestionAnswerPagerAdapter(getSupportFragmentManager(), this, mId);
        mViewPager.setAdapter(mAdapter);

        mTabs.setupWithViewPager(mViewPager);
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
                intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                startActivity(intent);
                break;
            case TYPE_QUESTION:
                intent = new Intent(this, QuestionDetailActivity.class);
                intent.putExtra(MyConstants.Extras.KEY_ID, pModel.getId());
                intent.putExtra(MyConstants.Extras.KEY_TITLE, ((Question) pModel).getTitle());
                startActivity(intent);

        }
    }

    @Override
    public void onAudioItemSelected(List<Post> pAudios) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
