package com.yipl.nrna.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.util.MyConstants;

public class SettingActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.setting_personalization))) {
            Intent i = new Intent(this, PersonalizationActivity.class);
            i.putExtra(MyConstants.Extras.KEY_PERSONALIZATION_LAUNCH, true);
            startActivity(i);
            return true;
        }
        return false;
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
            findPreference(getString(R.string.setting_personalization)).setOnPreferenceClickListener((SettingActivity) getActivity());
        }

    }

}
