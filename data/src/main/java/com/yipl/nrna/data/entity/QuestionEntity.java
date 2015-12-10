package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class QuestionEntity {
    Integer id;
    @SerializedName("updated_at")
    Long updatedAt;
    @SerializedName("created_at")
    Long createdAt;
    String stage;
    List<String> tags;
    String language;
    String question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer pId) {
        id = pId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        createdAt = pCreatedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> pTags) {
        tags = pTags;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String pLanguage) {
        language = pLanguage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String pQuestion) {
        question = pQuestion;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String pStage) {
        stage = pStage;
    }
}
