package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Post;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public interface VideoListView extends LoadDataView {
    public void renderVideoList(List<Post> pVideos);
}
