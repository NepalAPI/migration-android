package com.yipl.nrna.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.ui.fragment.country.AboutFragment;
import com.yipl.nrna.ui.fragment.country.RelatedContentFragment;

/**
 * Created by julian on 12/14/15.
 */
public class CountryInfoPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String mTitles[] = new String[PAGE_COUNT];
    private Context context;
    private Country mCountry;

    public CountryInfoPagerAdapter(FragmentManager pFragmentManager, Context context, Country
            pCountry) {
        super(pFragmentManager);
        this.context = context;
        mTitles = context.getResources().getStringArray(R.array.country_info);
        mCountry = pCountry;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return AboutFragment.newInstance(mCountry);
            case 1:
                return RelatedContentFragment.newInstance(mCountry.getId());
        }
    }
}
