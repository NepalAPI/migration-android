package com.yipl.nrna.base;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.yipl.nrna.R;
import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.ui.adapter.ListAdapter;
import com.yipl.nrna.ui.fragment.FilterDialogFragment;
import com.yipl.nrna.ui.interfaces.FilterDialogCallbackInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirazan-PC on 12/23/2015.
 */
public abstract class ContentListFragment extends BaseFragment implements FilterDialogCallbackInterface {

    protected ListAdapter<Post> mListAdapter;
    protected List<Post> mPosts;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_filter, menu);
        menu.findItem(R.id.action_filter).setIcon(new IconicsDrawable(getContext(), GoogleMaterial.Icon.gmd_filter_list)
                .color(Color.WHITE).actionBar());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_filter){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            FilterDialogFragment newFragment = FilterDialogFragment.newInstance();
            newFragment.setTargetFragment(this,0);
            newFragment.show(ft, "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void filterContentList(List<String> pStageFilter, List<String> pTagFilter) {
        if(pStageFilter.isEmpty() && pTagFilter.isEmpty()){
            mListAdapter.setDataCollection(mPosts);
            return;
        }
        List<Post> filteredPost = new ArrayList<>();
        if(pTagFilter.isEmpty()) {
            for(Post post:mPosts){
                if (post.getStage() != null && post.getStage().containsAll(pStageFilter)) {
                    filteredPost.add(post);
                }
            }
            mListAdapter.setDataCollection(filteredPost);
            return;
        }
        if(pStageFilter.isEmpty()){
            for(Post post:mPosts){
                if (post.getTags() != null && post.getTags().containsAll(pTagFilter)) {
                    filteredPost.add(post);
                }
            }
            mListAdapter.setDataCollection(filteredPost);
            return;
        }

        for(Post post:mPosts){
            if (post.getStage() != null && post.getStage().containsAll(pStageFilter) &&
                    post.getTags() != null && post.getTags().containsAll(pTagFilter)) {
                filteredPost.add(post);
            }
        }
        mListAdapter.setDataCollection(filteredPost);
        return;
    }
}
