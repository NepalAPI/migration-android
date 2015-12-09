package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class QuestionEntity {
    String id;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("created_at")
    String createdAt;
    List<String> tags;
    String language;
    String question;

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String pCreatedAt) {
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
}
