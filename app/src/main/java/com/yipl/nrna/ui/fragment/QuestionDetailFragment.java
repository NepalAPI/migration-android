package com.yipl.nrna.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.data.utils.Logger;
import com.yipl.nrna.databinding.ArticleDataBinding;
import com.yipl.nrna.databinding.AudioDataBinding;
import com.yipl.nrna.databinding.VideoDataBinding;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.PostListPresenter;
import com.yipl.nrna.presenter.QuestionDetailPresenter;
import com.yipl.nrna.ui.activity.ArticleDetailActivity;
import com.yipl.nrna.ui.activity.AudioDetailActivity;
import com.yipl.nrna.ui.activity.VideoDetailActivity;
import com.yipl.nrna.ui.interfaces.QuestionDetailActivityView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionDetailFragment extends BaseFragment implements QuestionDetailActivityView {

    @Inject
    QuestionDetailPresenter mPresenter;
    @Inject
    PostListPresenter mPostPresenter;

    @Bind(R.id.answer)
    WebView mAnswer;
    @Bind(R.id.post_container)
    LinearLayout mPostContainer;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.no_content)
    TextView tvNoPosts;
    @Bind(R.id.sub_question_section)
    LinearLayout mSubQuestionSection;
    @Bind(R.id.sub_question_container)
    LinearLayout mSubQuestionContainer;
    @Bind(R.id.collapsible_header)
    LinearLayout mCollapsibleHeader;
    @Bind(R.id.title)
    TextView mCollapsibleHeaderTitle;
    @Bind(R.id.toggle_icon)
    ImageView mToggleIcon;
    @Bind(R.id.content)
    LinearLayout mContent;
    @Bind(R.id.divider)
    View mDivider;


    private List<Post> mPosts;
    private int selectionIndex = 0;

    private Long mQuestionId = Long.MIN_VALUE;
    private Boolean mIsSubQuestion = false;

    public static QuestionDetailFragment newInstance(Long pId, boolean isSubQuestion) {
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        Bundle data = new Bundle();
        data.putLong(MyConstants.Extras.KEY_ID, pId);
        data.putBoolean(MyConstants.Extras.KEY_IS_SUB_QUESTION, isSubQuestion);
        fragment.setArguments(data);
        return fragment;
    }

    public QuestionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_question_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mQuestionId = bundle.getLong(MyConstants.Extras.KEY_ID);
            mIsSubQuestion = bundle.getBoolean(MyConstants.Extras.KEY_IS_SUB_QUESTION, false);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCollapsibleHeader.setVisibility(mIsSubQuestion ? View.VISIBLE : View.GONE);
        if (mIsSubQuestion) {
            mContent.setVisibility(View.GONE);
            mDivider.setVisibility(View.VISIBLE);
            mToggleIcon.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon
                    .gmd_add).color(getResources().getColor(R.color.text_color_primary))
                    .actionBar());
        }else{
            mDivider.setVisibility(View.GONE);
        }
        initialize();
    }

    @OnClick(R.id.collapsible_header)
    public void toggleContentVisibility() {
        if (mContent.getVisibility() == View.VISIBLE) {
            mContent.setVisibility(View.GONE);
            mToggleIcon.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon
                    .gmd_add).color(getResources().getColor(R.color.text_color_primary))
                    .actionBar());
        } else {
            mContent.setVisibility(View.VISIBLE);
            mToggleIcon.setImageDrawable(new IconicsDrawable(getContext(), GoogleMaterial.Icon
                    .gmd_remove).color(getResources().getColor(R.color.text_color_primary))
                    .actionBar());
        }
    }

    private void initialize() {
        if (mQuestionId != Long.MIN_VALUE) {
            DaggerDataComponent.builder()
                    .dataModule(new DataModule(mQuestionId, MyConstants.DataParent.QUESTION,
                            null, false, false))
                    .activityModule(((BaseActivity) getActivity()).getActivityModule())
                    .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                    .build()
                    .inject(this);
            mPresenter.attachView(this);
            mPresenter.initialize();

            mPostPresenter.attachView(this);
            mPostPresenter.initialize();
        } else {
            throw new IllegalStateException("Question Id is required");
        }
    }

    @Override
    public void renderQuestionDetail(Question pQuestion) {
        Logger.d("QuestionDetailFragment_renderQuestionDetail", "answer: " + pQuestion.getAnswer());
        Logger.d("QuestionDetailFragment_renderQuestionDetail", "title:" + pQuestion.getTitle());

        mAnswer.loadDataWithBaseURL(null, pQuestion.getAnswer() != null ? pQuestion
                .getAnswer() : "Content is null", "text/html", "utf-8", null);
        if (mIsSubQuestion) mCollapsibleHeaderTitle.setText(pQuestion.getTitle());

        String[] childIds = pQuestion.getChildIds() != null
                ? pQuestion.getChildIds().split(",")
                : "".split(",");
        for (String childId : childIds) {
            Logger.d("QuestionDetailFragment_renderQuestionDetail", "child_id: " + childId);
        }
        mSubQuestionSection.setVisibility(childIds.length > 1 ? View.VISIBLE : View.GONE);
        if (childIds.length > 1) renderSubQuestions(childIds);
    }

    @Override
    public void renderSubQuestions(String[] pChildIds) {
        if (pChildIds != null) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            for (String id : pChildIds) {
                mSubQuestionContainer.addView(inflateSubQuestionView(inflater, Long.valueOf(id)));
            }
        }
    }

    private View inflateSubQuestionView(LayoutInflater inflater, Long pQuestionId) {
        View view = inflater.inflate(R.layout.item_fragment_holder, mSubQuestionContainer,
                false);
        view.setId(pQuestionId.intValue());
        getChildFragmentManager().beginTransaction()
                .replace(view.getId(), QuestionDetailFragment.newInstance(pQuestionId, true),
                        "question_" + pQuestionId)
                .commit();
        return view;
    }

    @Override
    public void renderPostList(List<Post> pPosts) {
        Logger.d("QuestionDetailFragment_renderPostList", "test:" + pPosts.size());
        if (pPosts != null) {
            mPosts = pPosts;
            for (Post post : mPosts) {
                if (post.getDataType() == MyConstants.Adapter.TYPE_AUDIO) {
                    renderAudio(post);
                } else if (post.getDataType() == MyConstants.Adapter.TYPE_VIDEO) {
                    renderVideo(post);
                } else if (post.getDataType() == MyConstants.Adapter.TYPE_TEXT) {
                    renderArticle(post);
                }
            }
        }
    }

    public void renderAudio(final Post pAudio) {
        if (pAudio != null) {
            AudioDataBinding audioDataBinding = DataBindingUtil.inflate
                    (LayoutInflater.from(getContext()), R.layout.list_item_audio, mPostContainer,
                            false);
            audioDataBinding.setAudio(pAudio);
            View view = audioDataBinding.getRoot();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(getContext(), AudioDetailActivity.class);
                        intent.putExtra(MyConstants.Extras.KEY_AUDIO_LIST, (Serializable)
                                getAudioList(pAudio.getId()));
                        intent.putExtra(MyConstants.Extras.KEY_AUDIO_SELECTION, selectionIndex);
                        startActivity(intent);
                }
            });
            mPostContainer.addView(view);
        }
    }

    private List<Post> getAudioList(long id){
        List<Post> list = new ArrayList<>();
        for (int i = 0; i < mPosts.size(); i++) {
            if(mPosts.get(i).getDataType() == MyConstants.Adapter.TYPE_AUDIO) {
                list.add(mPosts.get(i));
                selectionIndex = i;
            }
        }
        return list;
    }

    public void renderVideo(final Post pVideo) {
        if (pVideo != null) {
            VideoDataBinding videoDataBinding = DataBindingUtil.inflate
                    (LayoutInflater.from(getContext()), R.layout.list_item_video, mPostContainer,
                            false);
            videoDataBinding.setVideo(pVideo);
            View view = videoDataBinding.getRoot();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                /*params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.spacing_small));*/
            view.setLayoutParams(params);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                    intent.putExtra(MyConstants.Extras.KEY_ID, pVideo.getId());
                    intent.putExtra(MyConstants.Extras.KEY_TITLE, pVideo.getTitle());
                    startActivity(intent);
                }
            });

            mPostContainer.addView(view);
        }
    }

    public void renderArticle(final Post pArticle) {
        if (pArticle != null) {
            ArticleDataBinding articleDataBinding = DataBindingUtil.inflate
                    (LayoutInflater.from(getContext()), R.layout.list_item_article, mPostContainer,
                            false);
            articleDataBinding.setArticle(pArticle);
            View view = articleDataBinding.getRoot();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                /*params.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.spacing_small));*/
            view.setLayoutParams(params);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                    intent.putExtra(MyConstants.Extras.KEY_ID, pArticle.getId());
                    startActivity(intent);
                }
            });

            mPostContainer.addView(view);
        }
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetryView() {
    }

    @Override
    public void hideRetryView() {
    }

    @Override
    public void showErrorView(String pErrorMessage) {
        Snackbar.make(mPostContainer, pErrorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideErrorView() {
    }

    @Override
    public void showEmptyView() {
        tvNoPosts.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        tvNoPosts.setVisibility(View.INVISIBLE);
    }
}
