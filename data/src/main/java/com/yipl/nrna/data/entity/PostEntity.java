package com.yipl.nrna.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julian on 12/8/15.
 */
public class PostEntity {
    Long id;
    @SerializedName("created_at")
    Long createdAt;
    @SerializedName("updated_at")
    Long updatedAt;
    @SerializedName("question_ids")
    List<Long> questionIdList;
    List<String> tags;
    String language;
    String type;
    String source;
    String description;
    String title;
    PostDataEntity data;

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

    public List<Long> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(List<Long> pQuestionIdList) {
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

    public PostDataEntity getData() {
        return data;
    }

    public void setData(PostDataEntity pData) {
        data = pData;
    }

    public String getType(){ return type; }

    public void setType(String type){ this.type = type; }
}
