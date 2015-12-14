package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Post;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public interface InfoCenterContentFragmentView extends LoadDataView {
    void renderPosts(List<Post> pPosts);
}
