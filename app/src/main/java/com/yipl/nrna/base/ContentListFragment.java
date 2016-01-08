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
import com.yipl.nrna.ui.interfaces.FilterDialogCallbackInterface;
import com.yipl.nrna.util.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Nirazan-PC on 12/23/2015.
 */
public abstract class ContentListFragment extends BaseFragment implements FilterDialogCallbackInterface {

    @Inject
    public PostListPresenter mPresenter;

    protected ListAdapter<Post> mListAdapter;
    protected List<Post> mPosts;


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
        Logger.e("broadcast registered: " + this.toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_filter, menu);
        menu.findItem(R.id.action_filter).setIcon(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_filter_list)
                .color(Color.WHITE).actionBar());
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

    public void filterContentList(List<String> pStageFilter, List<String> pTagFilter) {
        if(mPosts == null)
            return;
        if (pStageFilter.isEmpty() && pTagFilter.isEmpty()) {
            mListAdapter.setDataCollection(mPosts);
            return;
        }
        List<Post> filteredPost = new ArrayList<>();
        if (pTagFilter.isEmpty()) {
            for (Post post : mPosts) {
                if (post.getStage() != null && post.getStage().containsAll(pStageFilter)) {
                    filteredPost.add(post);
                }
            }
            mListAdapter.setDataCollection(filteredPost);
            return;
        }
        if (pStageFilter.isEmpty()) {
            for (Post post : mPosts) {
                if (post.getTags() != null && post.getTags().containsAll(pTagFilter)) {
                    filteredPost.add(post);
                }
            }
            mListAdapter.setDataCollection(filteredPost);
            return;
        }

        for (Post post : mPosts) {
            if (post.getStage() != null && post.getStage().containsAll(pStageFilter) &&
                    post.getTags() != null && post.getTags().containsAll(pTagFilter)) {
                filteredPost.add(post);
            }
        }
        mListAdapter.setDataCollection(filteredPost);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(MyConstants.Extras.KEY_FILTERED_LIST, (Serializable)mListAdapter.getDataCollection());
        outState.putSerializable(MyConstants.Extras.KEY_LIST, (Serializable) mPosts);
        Logger.e("tab_saved", this.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mBroadcastReceiver);
        if(mPresenter!=null)
            mPresenter.destroy();
    }
}
