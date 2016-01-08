package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentDataEntity {

    @SerializedName("posts")
    List<DeletedContentEntity> mPosts;
    @SerializedName("answers")
    List<DeletedContentEntity> mAnswers;
    @SerializedName("questions")
    List<DeletedContentEntity> mQuestions;
    @SerializedName("updates")
    List<DeletedContentEntity> mUpdates;

    public List<DeletedContentEntity> getPosts() {
        return mPosts;
    }

    public void setPosts(List<DeletedContentEntity> pPosts) {
        mPosts = pPosts;
    }

    public List<DeletedContentEntity> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(List<DeletedContentEntity> pAnswers) {
        mAnswers = pAnswers;
    }

    public List<DeletedContentEntity> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<DeletedContentEntity> pQuestions) {
        mQuestions = pQuestions;
    }

    public List<DeletedContentEntity> getUpdates() {
        return mUpdates;
    }

    public void setUpdates(List<DeletedContentEntity> pUpdates) {
        mUpdates = pUpdates;
    }
}
