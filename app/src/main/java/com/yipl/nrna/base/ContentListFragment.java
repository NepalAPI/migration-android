package com.yipl.nrna.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.util.MyConstants;
import com.yipl.nrna.presenter.PostListPresenter;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.fragment.FilterDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Nirazan-PC on 12/23/2015.
 */
public abstract class ContentListFragment extends BaseFragment {

    @Inject
    public PostListPresenter mPresenter;

    protected ListAdapter<Post> mListAdapter;
    protected List<Post> mPosts;
    MenuItem mMenuFilter;
    Boolean mFiltered;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<String> tagChoices = ((BaseActivity) getActivity()).getPreferences().getFilterTagChoices();
            List<String> stageChoices = ((BaseActivity) getActivity()).getPreferences().getFilterStageChoices();
            filterContentList(stageChoices, tagChoices);
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mBroadcastReceiver,
                new IntentFilter(MyConstants.Extras.INTENT_FILTER));
        mFiltered = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        if (mPresenter != null)
            mPresenter.destroy();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(mFiltered)
            menu.findItem(R.id.action_filter).setIcon(R.drawable.ic_filter_applied);
        else
            menu.findItem(R.id.action_filter).setIcon(R.drawable.ic_filter);
    }

    public void filterContentList(List<String> pStageFilter, List<String> pTagFilter) {
        if (mPosts == null)
            return;
        if (pStageFilter.isEmpty() && pTagFilter.isEmpty()) {
            mFiltered = false;
            getActivity().invalidateOptionsMenu();
            mListAdapter.setDataCollection(mPosts);
            return;
        }
        mFiltered = true;
        //mMenuFilter.setIcon(R.drawable.ic_filter_applied);
        getActivity().invalidateOptionsMenu();
        List<Post> filteredPost = new ArrayList<>();
        for (Post post : mPosts) {
            if((post.getStage()!=null && !Collections.disjoint(post.getStage(), pStageFilter) ||
                    (post.getTags()!= null && !Collections.disjoint(post.getTags(), pTagFilter))))
            {
                filteredPost.add(post);
            }
        }
        mListAdapter.setDataCollection(filteredPost);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(MyConstants.Extras.KEY_FILTERED_LIST, (Serializable) mListAdapter.getDataCollection());
        outState.putSerializable(MyConstants.Extras.KEY_LIST, (Serializable) mPosts);
        outState.putBoolean(MyConstants.Extras.KEY_IS_FILTERED, mFiltered);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_filter, menu);
        menu.findItem(R.id.action_filter).setIcon(R.drawable.ic_filter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            FilterDialogFragment newFragment = FilterDialogFragment.newInstance();
            newFragment.setTargetFragment(this, 0);
            newFragment.show(ft, "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
