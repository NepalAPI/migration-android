package com.yipl.nrna.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.presenter.FilterDialogFragmentPresenter;
import com.yipl.nrna.ui.interfaces.FilterDialogFragmentView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nirazan-PC on 12/22/2015.
 */
public class FilterDialogFragment extends DialogFragment implements FilterDialogFragmentView {

    @Inject
    private FilterDialogFragmentPresenter mPresenter;
//
//    @Bind({R.id.checkBoxStage0, R.id.checkBoxStage1, R.id.checkBoxStage2, R.id.checkBoxStage3})
//    List<CheckBox> stageCheckbox;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.tagsContainer)
    LinearLayout tagsContainer;
    List<CheckBox> tagCheckbox;

    public FilterDialogFragment(){

    }

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.dialog_frgment_filter, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        loadTags();
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .dataModule(new DataModule())
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
    }

    private void loadTags() {
        getTags();
    }

    private void getTags() {
        mPresenter.initialize();
    }

    @Override
    public void renderTags(List<String> tagsList) {
        /*if(tagsList != null){
            for(String tag:tagsList){
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText(tag);
                tagCheckbox.add(checkBox);
                tagsContainer.addView(checkBox);
            }
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
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
        Snackbar.make(tagsContainer, pErrorMessage, Snackbar.LENGTH_SHORT).show();
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
}
