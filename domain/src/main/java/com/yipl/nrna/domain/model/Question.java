package com.yipl.nrna.domain.model;


import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class Question extends BaseModel{
    String mUpdatedAt;
    String mCreatedAt;
    List<String> mTags;
    String mLanguage;
    String mQuestion;

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String pUpdatedAt) {
        mUpdatedAt = pUpdatedAt;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String pCreatedAt) {
        mCreatedAt = pCreatedAt;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> pTags) {
        mTags = pTags;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String pLanguage) {
        mLanguage = pLanguage;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String pQuestion) {
        mQuestion = pQuestion;
    }
}
