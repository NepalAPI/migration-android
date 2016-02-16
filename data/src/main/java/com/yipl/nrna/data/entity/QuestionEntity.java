package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class QuestionEntity {
    Long id;
    @SerializedName("updated_at")
    Long updatedAt;
    @SerializedName("created_at")
    Long createdAt;
    @SerializedName("parent")
    Long parentId;
    String stage;
    List<String> tags;
    String language;
    String title;
    @SerializedName("answer")
    String answer;
    String childIds;
    @SerializedName("weight")
    Long weight;

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long pParentId) {
        parentId = pParentId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        title = pTitle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String pAnswer) {
        answer = pAnswer;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String pStage) {
        stage = pStage;
    }

    public String getChildIds() {
        return childIds;
    }

    public void setChildIds(String pChildIds) {
        childIds = pChildIds;
    }

    public Long getWeight() { return weight; }

    public void setWeight(Long pWeight) {weight = pWeight; }
}
