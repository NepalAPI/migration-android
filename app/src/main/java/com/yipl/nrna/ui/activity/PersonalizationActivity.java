package com.yipl.nrna.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yipl.nrna.R;
import com.yipl.nrna.base.BaseActivity;
import com.yipl.nrna.di.component.DaggerDataComponent;
import com.yipl.nrna.di.module.DataModule;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.CountryListFragmentPresenter;
import com.yipl.nrna.presenter.DeletedContentPresenter;
import com.yipl.nrna.presenter.LatestContentPresenter;
import com.yipl.nrna.ui.interfaces.CountryListView;
import com.yipl.nrna.ui.interfaces.MainActivityView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalizationActivity extends BaseActivity implements MainActivityView,CountryListView, View.OnClickListener {

    @Inject
    LatestContentPresenter mLatestContentPresenter;

    @Inject
    DeletedContentPresenter mDeletedContentPresenter;

    @Inject
    CountryListFragmentPresenter mCountryListPresenter;

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.languageRadioGroup)
    RadioGroup mLanguageRadioGroup;
    @Bind(R.id.genderRadioGroup)
    RadioGroup mGenderRadioGroup;
    @Bind(R.id.userTypeRadioGroup)
    RadioGroup mUserTypeRadioGroup;
    @Bind(R.id.countryContainer)
    LinearLayout countryContainer;
    @Bind(R.id.btnDone)
    Button btnDone;
    List<CheckBox> countryCheckBoxes = new ArrayList<>();


    @Override
    public int getLayout() {
        return R.layout.activity_personalization;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getBooleanExtra(MyConstants.Extras.KEY_PERSONALIZATION_LAUNCH, true)) {
            if (!getPreferences().getFirstTime()) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
        fetchLatestContent();
        btnDone.setOnClickListener(this);
        populate();
    }

    private void populate() {
        populateLanguage();
        populateGender();
        populateUserType();

    }

    private void populateUserType() {
        String usertype = getPreferences().getUserType();
        if(usertype.isEmpty()) {
            ((RadioButton) mUserTypeRadioGroup.getChildAt(0)).setChecked(true);
            return;
        }
        for(int i= 0; i<mUserTypeRadioGroup.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) mUserTypeRadioGroup.getChildAt(i);
            if(radioButton.getText().equals(usertype)){
                radioButton.setChecked(true);
                break;
            }
        }
    }

    private void populateGender() {
        String gender = getPreferences().getGender();
        if(gender.isEmpty()) {
            ((RadioButton) mGenderRadioGroup.getChildAt(0)).setChecked(true);
            return;
        }
        for(int i= 0; i<mGenderRadioGroup.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) mGenderRadioGroup.getChildAt(i);
            if(radioButton.getText().toString().equalsIgnoreCase(gender)){
                radioButton.setChecked(true);
                break;
            }
        }
    }

    private void populateLanguage() {
        String language = getPreferences().getLanguage();
        if(language.isEmpty()) {
            ((RadioButton) mLanguageRadioGroup.getChildAt(0)).setChecked(true);
            return;
        }
        for(int i= 0; i<mLanguageRadioGroup.getChildCount(); i++){
            RadioButton radioButton = (RadioButton) mLanguageRadioGroup.getChildAt(i);
            if(radioButton.getText().toString().equalsIgnoreCase(language)){
                radioButton.setChecked(true);
                break;
            }
        }
    }

    private void initialize() {
        DaggerDataComponent.builder()
                .dataModule(new DataModule(getPreferences().getLastUpdateStamp(), 0))
                .activityModule(getActivityModule())
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
        mLatestContentPresenter.attachView(this);
        mCountryListPresenter.attachView(this);
    }

    private void fetchLatestContent() {
        mLatestContentPresenter.initialize();
        mDeletedContentPresenter.initialize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLatestContentPresenter.destroy();
        mDeletedContentPresenter.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void informCurrentFragmentForUpdate() {
        showCountryList();
    }

    private void showCountryList() {
        loadCountryList();
    }

    private void loadCountryList() {
        mCountryListPresenter.initialize();
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
        Snackbar.make(countryContainer, pErrorMessage, Snackbar.LENGTH_SHORT);
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
    public void renderCountryList(List<Country> pCountries) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if(pCountries!= null) {
            for (Country country : pCountries) {
                CheckBox checkBox;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    checkBox = new AppCompatCheckBox(getContext());
                else {
                    checkBox = new CheckBox(getContext());
                    int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
                    checkBox.setButtonDrawable(id);
                }
                checkBox.setText(country.getName());
                countryCheckBoxes.add(checkBox);
                countryContainer.addView(checkBox, params);

            }
            populateCountry();
        }
    }

    private void populateCountry() {
        boolean isFirst = getPreferences().getFirstTime();
        List<String> countries = getPreferences().getCountries();
            for (CheckBox checkBox : countryCheckBoxes) {
                if(countries.isEmpty() && isFirst) {
                    checkBox.setChecked(true);
                    continue;
                }
                for (String country : countries) {
                if(country.equalsIgnoreCase(checkBox.getText().toString())){
                    checkBox.setChecked(true);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDone:
                savePreferences();
                getPreferences().setFirstTime(false);
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            default:
                break;

        }
    }

    private void savePreferences() {
        if(mLanguageRadioGroup.getCheckedRadioButtonId() != -1){
            String language =((RadioButton) mLanguageRadioGroup.findViewById(
                    mLanguageRadioGroup.getCheckedRadioButtonId())).getText().toString();
            getPreferences().setLanguage(language);
        }
        if(mGenderRadioGroup.getCheckedRadioButtonId() != -1){
            String gender = ((RadioButton) mGenderRadioGroup.findViewById(
                    mGenderRadioGroup.getCheckedRadioButtonId())).getText().toString();
            getPreferences().setGender(gender);
        }

        List<String> countries = new ArrayList<>();
        for (CheckBox countryCheckBox : countryCheckBoxes) {
            if(countryCheckBox.isChecked())
                countries.add(countryCheckBox.getText().toString());
        }
        getPreferences().setCountries(countries);

        if(mUserTypeRadioGroup.getCheckedRadioButtonId() != -1){
            String userType = ((RadioButton) mUserTypeRadioGroup.findViewById(
                    mUserTypeRadioGroup.getCheckedRadioButtonId())).getText().toString();
            getPreferences().setUserType(userType);
        }
    }
}
