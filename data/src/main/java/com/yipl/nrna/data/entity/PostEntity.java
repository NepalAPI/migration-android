package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julian on 12/8/15.
 */
public class PostEntity {
    String id;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("question_ids")
    List<String> questionIdList;
    List<String> tags;
    String language;
    String source;
    String description;
    String title;

    public String getId() {
        return id;
    }

    public void setId(String pId) {
        id = pId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String pCreatedAt) {
        createdAt = pCreatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String pUpdatedAt) {
        updatedAt = pUpdatedAt;
    }

    public List<String> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(List<String> pQuestionIdList) {
        questionIdList = pQuestionIdList;
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

    public String getSource() {
        return source;
    }

    public void setSource(String pSource) {
        source = pSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }
}
