package com.yipl.nrna.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yipl.nrna.R;
import com.yipl.nrna.ui.fragment.ArticleListFragment;
import com.yipl.nrna.ui.fragment.AudioListFragment;
import com.yipl.nrna.ui.fragment.QuestionDetailFragment;
import com.yipl.nrna.ui.fragment.VideoListFragment;

/**
 * Created by Nirazan-PC on 12/15/2015.
 */
public class QuestionAnswerPagerAdapter extends FragmentPagerAdapter {

    private final int[] imageResId = {
            R.drawable.ic_tab_star,
            R.drawable.ic_tab_headset,
            R.drawable.ic_tab_video,
            R.drawable.ic_tab_article
    };
    private final String[] tabTitles;
    Context mContext;
    Long mQuestionId;

    public QuestionAnswerPagerAdapter(FragmentManager fm, Context pContext, Long pId) {
        super(fm);
        mContext = pContext;
        mQuestionId = pId;
        tabTitles = mContext.getResources().getStringArray(R.array.question_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = QuestionDetailFragment.newInstance(mQuestionId, false);
                break;
            case 1:
                fragment = AudioListFragment.newInstance(mQuestionId, true);
                break;
            case 2:
                fragment = VideoListFragment.newInstance(mQuestionId, true);
                break;
            case 3:
                fragment = ArticleListFragment.newInstance(mQuestionId, true);
                break;
            default:
                throw new IllegalArgumentException("Invalid index...");
        }
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public View getTabView(int position) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView title = (TextView) tabView.findViewById(R.id.tab_title);
        title.setText(getPageTitle(position));
        ImageView icon = (ImageView) tabView.findViewById(R.id.tab_icon);
        icon.setImageResource(imageResId[position]);
        return tabView;
    }
}
