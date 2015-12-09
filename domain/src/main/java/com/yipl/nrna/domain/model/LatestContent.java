package com.yipl.nrna.domain.model;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class LatestContent extends BaseModel {
    List<Question> mQuestionList;
    List<Post> mPostList;

    public List<Question> getQuestionList() {
        return mQuestionList;
    }

    public List<Post> getPostList() {
        return mPostList;
    }

    public void setQuestionList(List<Question> pQuestionList) {
        mQuestionList = pQuestionList;
    }

    public void setPostList(List<Post> pPostList) {
        mPostList = pPostList;
    }
}
