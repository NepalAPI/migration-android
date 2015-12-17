package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.BaseModel;
import com.yipl.nrna.domain.model.Post;

import java.util.List;

/**
 * Created by Nirazan-PC on 12/16/2015.
 */
public interface ListClickCallbackInterface {
    void onListItemSelected(BaseModel pModel);

    void onAudioItemSelected(List<Post> pAudios);
}
