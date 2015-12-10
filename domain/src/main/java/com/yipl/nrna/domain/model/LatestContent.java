package com.yipl.nrna.domain.model;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class LatestContent extends BaseModel {
    List<Question> mQuestions;
    List<Post> mPosts;

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public List<Post> getPosts() {
        return mPosts;
    }

    public void setQuestions(List<Question> pQuestions) {
        mQuestions = pQuestions;
    }

    public void setPosts(List<Post> pPosts) {
        mPosts = pPosts;
    }
}
