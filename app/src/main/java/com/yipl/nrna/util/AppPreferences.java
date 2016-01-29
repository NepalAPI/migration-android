package com.yipl.nrna.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yipl.nrna.domain.util.MyConstants.Preferences.COUNTRY;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.DOWNLOAD_REFERENCES;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.FIRST_TIME;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.GENDER;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.LANGUAGE;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.LAST_UPDATE_STAMP;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.PREF_NAME;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.STAGE_FILTER_CHOICES;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.TAG_FILTER_CHOICES;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.USERTYPE;

/**
 * Created by julian on 12/16/15.
 */
public class AppPreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;

    public AppPreferences(Context pContext) {
        pref = pContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        mContext = pContext;
    }

    public long getLastUpdateStamp() {
        return pref.getLong(LAST_UPDATE_STAMP, -1);
    }

    public void setLastUpdateStamp(long pStamp) {
        editor.putLong(LAST_UPDATE_STAMP, pStamp);
        editor.commit();
    }

    public List<String> getFilterTagChoices() {
        Set<String> set = pref.getStringSet(TAG_FILTER_CHOICES, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public void setFilterTagChoices(List<String> pTagChoices) {
        Set<String> set = new HashSet<String>();
        set.addAll(pTagChoices);
        editor.putStringSet(TAG_FILTER_CHOICES, set);
        editor.commit();
    }

    public List<String> getFilterStageChoices() {
        Set<String> set = pref.getStringSet(STAGE_FILTER_CHOICES, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public void setFilterStageChoices(List<String> pStageChoices) {
        Set<String> set = new HashSet<String>();
        set.addAll(pStageChoices);
        editor.putStringSet(STAGE_FILTER_CHOICES, set);
        editor.commit();
    }

    public void removeFilterChoices() {
        editor.remove(STAGE_FILTER_CHOICES);
        editor.remove(TAG_FILTER_CHOICES);
        editor.commit();
    }

    public String getLanguage() {
        return pref.getString(LANGUAGE, "");
    }

    public void setLanguage(String pLanguage) {
        editor.putString(LANGUAGE, pLanguage);
        editor.commit();
    }

    public String getGender() {
        return pref.getString(GENDER, "");
    }

    public void setGender(String pGender) {
        editor.putString(GENDER, pGender);
        editor.commit();
    }

    public List<String> getCountries() {
        Set<String> set = pref.getStringSet(COUNTRY, null);
        return set == null ? null : new ArrayList<>(set);
    }

    public void setCountries(List<String> pCountry) {
        Set<String> set = new HashSet<String>();
        set.addAll(pCountry);
        editor.putStringSet(COUNTRY, set);
        editor.commit();
    }

    public String getUserType() {
        return pref.getString(USERTYPE, "");
    }

    public void setUserType(String pUserType) {
        editor.putString(USERTYPE, pUserType);
        editor.commit();
    }

    public Boolean getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean pFirstTime) {
        editor.putBoolean(FIRST_TIME, pFirstTime);
        editor.commit();
    }

    public Set<String> getDownloadReferences() {
        return pref.getStringSet(DOWNLOAD_REFERENCES, new HashSet<String>());
    }

    public void addDownloadReference(Long pReference) {
        Set<String> references = getDownloadReferences();
        references.add(String.valueOf(pReference));
        editor.putStringSet(DOWNLOAD_REFERENCES, references);
        editor.commit();
    }

    public void removeDownloadReference(Long pReference) {
        Set<String> references = getDownloadReferences();
        references.remove(String.valueOf(pReference));
        editor.putStringSet(DOWNLOAD_REFERENCES, references);
        editor.commit();
    }

    public boolean hasDownloadReference(Long pReference) {
        Set<String> references = getDownloadReferences();
        return references.contains(String.valueOf(pReference));
    }

}
