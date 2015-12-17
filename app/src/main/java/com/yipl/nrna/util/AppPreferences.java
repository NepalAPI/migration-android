package com.yipl.nrna.util;

import android.content.Context;
import android.content.SharedPreferences;

import static com.yipl.nrna.domain.util.MyConstants.Preferences.LAST_UPDATE_STAMP;
import static com.yipl.nrna.domain.util.MyConstants.Preferences.PREF_NAME;

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
}
