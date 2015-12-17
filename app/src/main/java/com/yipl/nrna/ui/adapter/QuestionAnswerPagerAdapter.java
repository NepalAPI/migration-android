package com.yipl.nrna.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yipl.nrna.R;
import com.yipl.nrna.ui.fragment.ArticleListFragment;
import com.yipl.nrna.ui.fragment.AudioListFragment;
import com.yipl.nrna.ui.fragment.VideoListFragment;

/**
 * Created by Nirazan-PC on 12/15/2015.
 */
public class QuestionAnswerPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    Long mQuestionId;
    private int[] imageResId = {
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_send
    };

    public QuestionAnswerPagerAdapter(FragmentManager fm, Context pContext, Long pId) {
        super(fm);
        mContext = pContext;
        mQuestionId = pId;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
            default:
                fragment = AudioListFragment.newInstance(mQuestionId);
                break;
            case 1:
                fragment = VideoListFragment.newInstance(mQuestionId);
                break;
            case 2:
                fragment = ArticleListFragment.newInstance(mQuestionId);
                break;
        }
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        // return tabTitles[position];

        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
        // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
//         Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
//         Drawable image = context.getResources().getDrawable(imageResId[position]);

//        Drawable image = ContextCompat.getDrawable(mContext, imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        SpannableString sb = new SpannableString("\n");
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        return sb;

        switch (position) {
            case 0:
            default:
                return "Audio";
            case 1:
                return "Video";
            case 2:
                return "Article";
        }

    }
}
