package com.yipl.nrna.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.databinding.RecentQuestionDataBinding;
import com.yipl.nrna.domain.model.Question;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecentQuestionFragment extends BaseFragment {

    private RecentQuestionDataBinding mDataBinding;
    private Question mQuestion;
    @Bind(R.id.data_container)
    RelativeLayout mDataContainer;
    @Bind(R.id.see_more)
    RelativeLayout mSeeMore;

    public RecentQuestionFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recent_question;
    }

    public static RecentQuestionFragment newInstance(Question pQuestion){
        RecentQuestionFragment fragment = new RecentQuestionFragment();
        if(pQuestion!= null){
            Bundle args = new Bundle();
            args.putSerializable(MyConstants.Extras.KEY_QUESTION, pQuestion);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mQuestion = (Question) args.getSerializable(MyConstants.Extras.KEY_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mQuestion != null) {
            mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_question,
                    container, false);
            mDataBinding.setQuestion(mQuestion);
            View view = mDataBinding.getRoot();
            ButterKnife.bind(this, view);
            return view;
        }else{
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mQuestion == null){
            mSeeMore.setVisibility(View.VISIBLE);
            mDataContainer.setVisibility(View.GONE);
        }else{
            mSeeMore.setVisibility(View.GONE);
            mDataContainer.setVisibility(View.VISIBLE);
        }
    }
}
