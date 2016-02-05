package com.yipl.nrna.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.viewpagerindicator.CirclePageIndicator;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.databinding.ArticleDataBinding;
import com.yipl.nrna.databinding.AudioDataBinding;
import com.yipl.nrna.databinding.VideoDataBinding;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.HomeFragmentPresenter;
import com.yipl.nrna.ui.activity.ArticleDetailActivity;
import com.yipl.nrna.ui.activity.AudioDetailActivity;
import com.yipl.nrna.ui.activity.MainActivity;
import com.yipl.nrna.ui.activity.VideoDetailActivity;
import com.yipl.nrna.ui.adapter.QuestionPagerAdapter;
import com.yipl.nrna.ui.interfaces.HomeFragmentView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFragmentView {

    public static final int LATEST_DATA_SIZE = 5;

    @Inject
    HomeFragmentPresenter mPresenter;

    @Bind(R.id.container)
    View mContainer;
    @Bind(R.id.recent_questions_pager)
    ViewPager mQuestionsPager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    @Bind(R.id.audio_list)
    LinearLayout mAudioList;
    @Bind(R.id.video_list)
    LinearLayout mVideoList;
    @Bind(R.id.article_list)
    LinearLayout mArticleList;
    @Bind(R.id.question_progress_bar)
    ProgressBar mQuestionProgressBar;
    @Bind(R.id.audio_progress_bar)
    ProgressBar mAudioProgressBar;
    @Bind(R.id.video_progress_bar)
    ProgressBar mVideoProgressBar;
    @Bind(R.id.article_progress_bar)
    ProgressBar mArticleProgressBar;
    @Bind(R.id.no_question)
    TextView mNoQuestion;
    @Bind(R.id.no_article)
    TextView mNoArticle;
    @Bind(R.id.no_audio)
    TextView mNoAudio;
    @Bind(R.id.no_video)
    TextView mNoVideo;

    private QuestionPagerAdapter mQuestionPagerAdapter;

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        setUpAdapter();
        loadLatestContent();
        final ViewTreeObserver viewTreeObserver = mQuestionsPager.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mQuestionsPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                showShowCaseView();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void showNewContentInfo() {
        Log.e("cnackbar_show", " called");
        Snackbar.make(mContainer, getString(R.string.message_content_available), Snackbar
                .LENGTH_INDEFINITE)
                .setAction(getString(R.string.action_refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View pView) {
                        loadLatestContent();
                    }
                })
                .show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .dataModule(new DataModule(LATEST_DATA_SIZE))
                .activityModule(((MainActivity) getActivity()).getActivityModule())
                .applicationComponent(((MainActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void setUpAdapter() {
        //for question
        mQuestionPagerAdapter = new QuestionPagerAdapter(getChildFragmentManager());
        mQuestionsPager.setOffscreenPageLimit(0);
        mQuestionsPager.setAdapter(mQuestionPagerAdapter);
        indicator.setViewPager(mQuestionsPager);
    }

    private void loadLatestContent() {
        mPresenter.initialize();
    }

    @Override
    public void renderLatestQuestions(List<Question> pQuestions) {
        if (pQuestions != null)
            mQuestionPagerAdapter.setQuestions(pQuestions);
    }

    @Override
    public void renderLatestAudios(final List<Post> pAudios) {
        if (pAudios != null) {
            mAudioList.removeAllViews();
            for (int i = 0; i < pAudios.size(); i++) {
                AudioDataBinding audioDataBinding = DataBindingUtil.inflate
                        (LayoutInflater.from(getContext()), R.layout.list_item_audio, mAudioList,
                                false);
                audioDataBinding.setAudio(pAudios.get(i));
                View view = audioDataBinding.getRoot();

                final int selectionIndex = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AudioDetailActivity.class);
                        intent.putExtra(MyConstants.Extras.KEY_AUDIO_LIST, (Serializable) pAudios);
                        intent.putExtra(MyConstants.Extras.KEY_AUDIO_SELECTION, selectionIndex);
                        startActivity(intent);
                    }
                });
                mAudioList.addView(view);
            }
        }
    }

    @Override
    public void renderLatestVideos(List<Post> pVideos) {
        if (pVideos != null) {
            mVideoList.removeAllViews();
            for (final Post video : pVideos) {
                VideoDataBinding videoDataBinding = DataBindingUtil.inflate
                        (LayoutInflater.from(getContext()), R.layout.list_item_video, mVideoList,
                                false);
                videoDataBinding.setVideo(video);
                View view = videoDataBinding.getRoot();

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                        intent.putExtra(MyConstants.Extras.KEY_ID, video.getId());
                        intent.putExtra(MyConstants.Extras.KEY_TITLE, video.getTitle());
                        startActivity(intent);
                    }
                });

                mVideoList.addView(view);
            }
        }
    }

    @Override
    public void renderLatestArticles(List<Post> pArticles) {
        if (pArticles != null) {
            mArticleList.removeAllViews();
            for (final Post article : pArticles) {
                ArticleDataBinding articleDataBinding = DataBindingUtil.inflate
                        (LayoutInflater.from(getContext()), R.layout.list_item_article, mArticleList,
                                false);
                articleDataBinding.setArticle(article);
                View view = articleDataBinding.getRoot();

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                        intent.putExtra(MyConstants.Extras.KEY_ID, article.getId());
                        startActivity(intent);
                    }
                });

                mArticleList.addView(view);
            }
        }
    }

    @Override
    public void showLoadingView(int flag) {
        switch (flag) {
            case HomeFragmentPresenter.FLAG_AUDIO:
                mAudioProgressBar.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                mVideoProgressBar.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                mQuestionProgressBar.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_ARTICLE:
                mArticleProgressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void hideLoadingView(int flag) {
        switch (flag) {
            case HomeFragmentPresenter.FLAG_AUDIO:
                mAudioProgressBar.setVisibility(View.GONE);
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                mVideoProgressBar.setVisibility(View.GONE);
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                mQuestionProgressBar.setVisibility(View.GONE);
                break;
            case HomeFragmentPresenter.FLAG_ARTICLE:
                mArticleProgressBar.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void showRetryView(int flag) {
    }

    @Override
    public void hideRetryView(int flag) {

    }

    @Override
    public void showErrorView(String pErrorMessage) {
        Snackbar.make(mContainer, pErrorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorView() {
    }

    @Override
    public void showEmptyView(int flag) {
        switch (flag) {
            case HomeFragmentPresenter.FLAG_AUDIO:
                mNoAudio.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                mNoVideo.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                //mNoQuestion.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_ARTICLE:
                mNoArticle.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void hideEmptyView(int flag) {
        switch (flag) {
            case HomeFragmentPresenter.FLAG_AUDIO:
                mNoAudio.setVisibility(View.GONE);
                break;
            case HomeFragmentPresenter.FLAG_VIDEO:
                mNoVideo.setVisibility(View.GONE);
                break;
            case HomeFragmentPresenter.FLAG_QUESTION:
                //mNoQuestion.setVisibility(View.VISIBLE);
                break;
            case HomeFragmentPresenter.FLAG_ARTICLE:
                mNoArticle.setVisibility(View.GONE);
                break;
        }
    }

    private void showShowCaseView() {
        final View v = ((LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.swipe_hand, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mQuestionsPager.getMeasuredHeight());
        int marginTop = ((AppCompatActivity) getActivity()).getSupportActionBar().getHeight();
        params.setMargins(0, marginTop, 0, 0);
        ShowcaseView showcaseView = new ShowcaseView.Builder(getActivity())
                .setTarget(new ViewTarget(mQuestionsPager))
                .setContentText(getString(R.string.showcase_question_detail))
                .setContentTitle(getString(R.string.showcase_question_title))
                .setStyle(R.style.showcase)
                .withNewStyleShowcase()
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {
                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        v.clearAnimation();
                        ((MainActivity) getActivity()).showCaseView();
                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {
                    }
                })
                .singleShot(MyConstants.ShowCase.MainActivity)
                .build();
        showcaseView.addView(v, params);
        Animation animation = new TranslateAnimation(v.getX() + 300, v.getX() - 50, v.getY(), v.getY());
        animation.setDuration(1500);
        animation.setStartOffset(300);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        v.startAnimation(animation);

    }
}
