package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Post;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/11/2015.
 */
public interface AudioListView extends LoadDataView {
    public void renderAudiolist(List<Post> pPosts);
}
