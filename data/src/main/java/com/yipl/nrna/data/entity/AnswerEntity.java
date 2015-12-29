package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by julian on 12/8/15.
 */
public class AnswerEntity {
    Long id;
    @SerializedName("created_at")
    Long createdAt;
    @SerializedName("updated_at")
    Long updatedAt;
    @SerializedName("question_id")
    Long questionId;
    String title;

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        createdAt = pCreatedAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long pQuestionId) {
        questionId = pQuestionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }
}
