package com.yipl.nrna.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.FilterDialogFragmentPresenter;
import com.yipl.nrna.ui.interfaces.FilterDialogCallbackInterface;
import com.yipl.nrna.ui.interfaces.FilterDialogFragmentView;
import com.yipl.nrna.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nirazan-PC on 12/22/2015.
 */
public class FilterDialogFragment extends DialogFragment implements FilterDialogFragmentView, View.OnClickListener {

    @Inject
    FilterDialogFragmentPresenter mPresenter;

    @Bind({R.id.checkBoxStage0, R.id.checkBoxStage1, R.id.checkBoxStage2, R.id.checkBoxStage3})
    List<CheckBox> stageCheckbox;
    @Bind(R.id.tagProgressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.tagsContainer)
    LinearLayout tagsContainer;
    @Bind(R.id.btnFilter)
    Button btnFilter;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    List<CheckBox> tagCheckbox;

    FilterDialogCallbackInterface mCallbackInterface;

    public FilterDialogFragment() {

    }

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_frgment_filter, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initialize();
        loadTags();
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .dataModule(new DataModule())
                .activityModule(((BaseActivity) getActivity()).getActivityModule())
                .applicationComponent(((BaseActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
        mPresenter.attachView(this);
        tagCheckbox = new ArrayList<>();
        btnFilter.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        mCallbackInterface = (FilterDialogCallbackInterface) getTargetFragment();
    }

    private void loadTags() {
        getTags();
    }

    private void getTags() {
        mPresenter.initialize();
    }

    @Override
    public void renderTags(List<String> tagsList) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (tagsList != null) {
            for (String tag : tagsList) {
                CheckBox checkBox;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    checkBox = new AppCompatCheckBox(getContext());
                else{
                    checkBox = new CheckBox(getContext());
                    int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
                    checkBox.setButtonDrawable(id);
                }
                checkBox.setText(tag);
                tagCheckbox.add(checkBox);
                tagsContainer.addView(checkBox, params);
            }
        }
        restoreLastChoices();
    }

    private void restoreLastChoices() {
        List<String> lastTagChoices = ((BaseActivity) getActivity()).getPreferences().getFilterTagChoices();
        List<String> lastStageChoices = ((BaseActivity) getActivity()).getPreferences().getFilterStageChoices();
        if (lastStageChoices != null) {
            for (String choice : lastTagChoices) {
                for (CheckBox checkBox : tagCheckbox) {
                    if (choice.equals(checkBox.getText().toString()))
                        checkBox.setChecked(true);
                }
            }
        }
        if (lastTagChoices != null) {
            for (String choice : lastStageChoices) {
                for (CheckBox checkBox : stageCheckbox) {
                    if (choice.equals(checkBox.getText().toString()))
                        checkBox.setChecked(true);
                }
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFilter:
                List<String> tagFilter = new ArrayList<>();
                List<String> stageFilter = new ArrayList<>();
                for (CheckBox checkBox : stageCheckbox) {
                    if (checkBox.isChecked()) {
                        stageFilter.add(checkBox.getText().toString());
                    }
                }
                for (CheckBox checkBox : tagCheckbox) {
                    if (checkBox.isChecked()) {
                        tagFilter.add(checkBox.getText().toString());
                    }
                }
                ((BaseActivity) getActivity()).getPreferences().setFilterTagChoices(tagFilter);
                ((BaseActivity) getActivity()).getPreferences().setFilterStageChoices(stageFilter);
                Intent intent = new Intent(MyConstants.Extras.INTENT_FILTER);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                this.dismiss();
                break;
            case R.id.btnCancel:
                this.dismiss();
                break;
        }
    }

    public static ColorStateList getCheckboxColorList() {
        int[][] states = new int[][]{new int[]{-android.R.attr.state_checked},
        new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{
        Color.WHITE,
        Color.WHITE
        };
        ColorStateList myColorList = new ColorStateList(states, colors);
        return myColorList;
        }
}
