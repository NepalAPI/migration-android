package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

import java.util.List;

/**
 * Created by julian on 12/8/15.
 */
public class Post extends BaseModel {
    Long mCreatedAt;
    Long mUpdatedAt;
    List<Long> mQuestionIdList;
    List<Long> mAnswerIdList;
    List<String> mTags;
    String mLanguage;
    String mSource;
    String mDescription;
    String mTitle;
    String type;
    PostData mData;
    List<String> stage;
    boolean downloadStatus;

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

    public List<Long> getQuestionIdList() {
        return mQuestionIdList;
    }

    public void setQuestionIdList(List<Long> pQuestionIdList) {
        mQuestionIdList = pQuestionIdList;
    }

    public List<Long> getAnswerIdList() {
        return mAnswerIdList;
    }

    public void setAnswerIdList(List<Long> pAnswerIdList) {
        mAnswerIdList = pAnswerIdList;
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

    public String getSource() {
        return mSource;
    }

    public void setSource(String pSource) {
        mSource = pSource;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String pDescription) {
        mDescription = pDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public PostData getData() {
        return mData;
    }

    public void setData(PostData pData) {
        mData = pData;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }

    public List<String> getStage() {
        return stage;
    }

    public void setStage(List<String> pStage) {
        stage = pStage;
    }

    public boolean isDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(boolean pDownloadStatus) {
        downloadStatus = pDownloadStatus;
    }

    @Override
    public int getDataType() {
        if (getType().equalsIgnoreCase("audio")) {
            return MyConstants.Adapter.TYPE_AUDIO;
        } else if (getType().equalsIgnoreCase("video")) {
            return MyConstants.Adapter.TYPE_VIDEO;
        } else {
            return MyConstants.Adapter.TYPE_TEXT;
        }
    }
}
