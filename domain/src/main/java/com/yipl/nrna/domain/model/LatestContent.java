package com.yipl.nrna.domain.model;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class LatestContent extends BaseModel {
    List<Question> mQuestions;
    List<Post> mPosts;
    List<Answer> mAnswers;
    List<CountryUpdate> mUpdates;

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> pQuestions) {
        mQuestions = pQuestions;
    }

    public List<Post> getPosts() {
        return mPosts;
    }

    public void setPosts(List<Post> pPosts) {
        mPosts = pPosts;
    }

    public List<Answer> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(List<Answer> pAnswers) {
        mAnswers = pAnswers;
    }

    public List<CountryUpdate> getUpdates() {
        return mUpdates;
    }

    public void setUpdates(List<CountryUpdate> pUpdates) {
        mUpdates = pUpdates;
    }
}
