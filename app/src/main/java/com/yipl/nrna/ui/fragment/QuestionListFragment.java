package com.yipl.nrna.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.base.BaseFragment;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.presenter.QuestionListFragmentPresenter;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.interfaces.FilterDialogCallbackInterface;
import com.yipl.nrna.ui.interfaces.ListClickCallbackInterface;
import com.yipl.nrna.ui.interfaces.QuestionListFragmentView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by Nirazan-PC on 12/15/2015.
 */
public class QuestionListFragment extends BaseFragment implements QuestionListFragmentView, FilterDialogCallbackInterface {

    @Inject
    QuestionListFragmentPresenter mPresenter;
    @Bind(R.id.recyclerViewQuestionList)
    RecyclerView mRecyclerView;
    @Bind(R.id.tvNoQuestion)
    TextView tvNoQuestion;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.data_container)
    RelativeLayout mContainer;

    ListAdapter<Question> mListAdapter;
    List<Question> mQuestions;

    public QuestionListFragment() {
        super();
    }

    public static QuestionListFragment newInstance() {
        QuestionListFragment fragment = new QuestionListFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_question_list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        setUpAdapter();
        loadQuestionList();
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void showNewContentInfo() {
        Snackbar.make(mContainer, getString(R.string.message_content_available), Snackbar
                .LENGTH_INDEFINITE)
                .setAction(getString(R.string.action_refresh), new View.OnClickListener() {
                    @Override
                    public void onClick(View pView) {
                        loadQuestionList();
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_filter, menu);
        menu.findItem(R.id.action_filter).setIcon(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_filter_list)
                .color(Color.WHITE).actionBar());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            FilterDialogFragment newFragment = FilterDialogFragment.newInstance();
            newFragment.setTargetFragment(this, 0);
            newFragment.show(ft, "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
        tvNoQuestion.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpAdapter() {
        mListAdapter = new ListAdapter<Question>(getContext(), new ArrayList<Question>(), (ListClickCallbackInterface) getActivity());
        mRecyclerView.setAdapter(mListAdapter);
    }

    private void loadQuestionList() {
        mPresenter.initialize();
    }

    @Override
    public void renderQuestionList(List<Question> pQuestionList) {
        mQuestions = pQuestionList;
        mListAdapter.setDataCollection(pQuestionList);
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
        Snackbar.make(mRecyclerView, pErrorMessage, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void hideErrorView() {

    }

    @Override
    public void showEmptyView() {
        tvNoQuestion.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        tvNoQuestion.setVisibility(View.GONE);
    }

    @Override
    public void filterContentList(List<String> pStageFilter, List<String> pTagFilter) {
        if (pStageFilter.isEmpty() && pTagFilter.isEmpty()) {
            mListAdapter.setDataCollection(mQuestions);
            return;
        }
        List<Question> filteredQuestion = new ArrayList<>();
        if (pTagFilter.isEmpty()) {
            for (Question question : mQuestions) {
                if (question.getStage() != null && pStageFilter.contains(question.getStage())) {
                    filteredQuestion.add(question);
                }
            }
            mListAdapter.setDataCollection(filteredQuestion);
            return;
        }
        if (pStageFilter.isEmpty()) {
            for (Question question : mQuestions) {
                if (question.getTags() != null && question.getTags().containsAll(pTagFilter)) {
                    filteredQuestion.add(question);
                }
            }
            mListAdapter.setDataCollection(filteredQuestion);
            return;
        }

        for (Question question : mQuestions) {
            if (question.getStage() != null && pStageFilter.contains(question.getStage()) &&
                    question.getTags() != null && question.getTags().containsAll(pTagFilter)) {
                filteredQuestion.add(question);
            }
        }
        mListAdapter.setDataCollection(filteredQuestion);
        return;
    }
}
