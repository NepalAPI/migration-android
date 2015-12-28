package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

/**
 * Created by julian on 12/8/15.
 */
public class Answer extends BaseModel {
    Long mCreatedAt;
    Long mUpdatedAt;
    Long mQuestionId;
    String mTitle;

    public Answer() {
        setDataType(MyConstants.Adapter.TYPE_ANSWER);
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        mCreatedAt = pCreatedAt;
    }

    public Long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        mUpdatedAt = pUpdatedAt;
    }

    public Long getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(Long pQuestionId) {
        mQuestionId = pQuestionId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }
}
