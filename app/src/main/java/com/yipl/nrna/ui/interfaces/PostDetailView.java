package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Post;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public interface PostDetailView extends LoadDataView {
    void renderPostDetail(Post post);

    void onDownloadStarted(String message);
}
