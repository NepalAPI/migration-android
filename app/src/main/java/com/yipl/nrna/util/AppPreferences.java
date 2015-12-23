package com.yipl.nrna.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.yipl.nrna.domain.util.MyConstants.Preferences.LAST_UPDATE_STAMP;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.PREF_NAME;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.STAGE_FILTER_CHOICES;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.TAG_FILTER_CHOICES;

/**
 * Created by julian on 12/16/15.
 */
public class AppPreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public AppPreferences(Context pContext) {
        pref = pContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public long getLastUpdateStamp() {
        return pref.getLong(LAST_UPDATE_STAMP, -1);
    }

    public void setLastUpdateStamp(long pStamp) {
        editor.putLong(LAST_UPDATE_STAMP, pStamp);
        editor.commit();
    }

    public void setFilterTagChoices(List<String> pTagChoices){
        Set<String> set = new HashSet<String>();
        set.addAll(pTagChoices);
        editor.putStringSet(TAG_FILTER_CHOICES, set);
        editor.commit();
    }

    public List<String> getFilterTagChoices(){
        Set<String> set = pref.getStringSet(TAG_FILTER_CHOICES, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public void setFilterStageChoices(List<String> pStageChoices){
        Set<String> set = new HashSet<String>();
        set.addAll(pStageChoices);
        editor.putStringSet(STAGE_FILTER_CHOICES, set);
        editor.commit();
    }

    public List<String> getFilterStageChoices(){
        Set<String> set = pref.getStringSet(STAGE_FILTER_CHOICES, new HashSet<String>());
        return new ArrayList<>(set);
    }

    public void removeFilterChoices(){
        editor.remove(STAGE_FILTER_CHOICES);
        editor.remove(TAG_FILTER_CHOICES);
        editor.commit();
    }
}
