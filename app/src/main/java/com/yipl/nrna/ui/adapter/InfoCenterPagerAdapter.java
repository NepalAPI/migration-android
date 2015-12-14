package com.yipl.nrna.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yipl.nrna.R;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.ui.fragment.InfoCenterContentFragment;

/**
 * Created by julian on 12/14/15.
 */
public class InfoCenterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String mTitles[] = new String[PAGE_COUNT];
    private Context context;

    public InfoCenterPagerAdapter(FragmentManager pFragmentManager, Context context) {
        super(pFragmentManager);
        this.context = context;
        mTitles = context.getResources().getStringArray(R.array.stages);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return InfoCenterContentFragment.newInstance(MyConstants.Stage.values()[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
