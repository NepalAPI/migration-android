package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Post;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/21/2015.
 */
public interface RelatedContentFragmentView extends LoadDataView {
    public void renderRelatedContent(List<Post> pPosts);
}
