package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julian on 12/8/15.
 */
public class Post extends BaseModel {
    Long mCreatedAt;
    Long mUpdatedAt;
    List<Long> mQuestionIdList;
    List<String> mTags;
    String mLanguage;
    String mSource;
    String mDescription;
    String mTitle;
    String type;
    PostData mData;
    String stage;

    public static List<Post> getDummyPosts(String pType) {
        List<Post> list = new ArrayList<>();
        String[] tags = {"tag1", "tag2", "tag3"};
        PostData data = new PostData();
        data.setContent("This is sample content...");
        data.setMediaUrl("https://storage.googleapis.com/anthem/National%20Anthem%20Of%20Nepal%20-%5B%20www.mp3mp4.ninja%20%5D.mp3");
        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setId(((long) i));
            post.setTitle("This is a sample " + pType + " " + i + ".");
            post.setCreatedAt(123456252L);
            post.setUpdatedAt(123425645L);
            post.setDescription("this is the description");
            post.setTags(Arrays.asList(tags));
            post.setData(data);
            post.setQuestionIdList(new ArrayList());
            post.setType(pType);
            list.add(post);
        }
        return list;
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

    public List<Long> getQuestionIdList() {
        return mQuestionIdList;
    }

    public void setQuestionIdList(List<Long> pQuestionIdList) {
        mQuestionIdList = pQuestionIdList;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String pStage) {
        stage = pStage;
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
