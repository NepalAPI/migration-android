package com.yipl.nrna.ui.interfaces;

import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.model.Question;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public interface HomeFragmentView extends LoadDataView {
    void renderLatestQuestions(List<Question> pQuestions);
    void renderLatestAudios(List<Post> pAudios);
    void renderLatestVideos(List<Post> pVideos);
}
