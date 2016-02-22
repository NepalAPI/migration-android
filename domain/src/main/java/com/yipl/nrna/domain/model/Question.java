package com.yipl.nrna.domain.model;

import com.yipl.nrna.domain.util.MyConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class Question extends BaseModel {
    Long mUpdatedAt;
    Long mCreatedAt;
    Long mParentId;
    List<String> mTags;
    String mLanguage;
    String mTitle;
    String mAnswer;
    String mStage;
    String childIds;
    Long weight;

    public Question() {
        setDataType(MyConstants.Adapter.TYPE_QUESTION);
    }

    public static List<Question> getDummyQuestions() {
        List<Question> list = new ArrayList<>();
        String[] tags = {"tag1", "tag2", "tag3"};
        for (int i = 0; i < 3; i++) {
            Question question = new Question();
            question.setId((long) i);
            question.setCreatedAt(123456252L);
            question.setUpdatedAt(123425645L);
            question.setTags(Arrays.asList(tags));
            question.setLanguage("ne");
            question.setTitle("This is sample question " + (i + 1) + " ?");
            list.add(question);
        }
        return list;
    }

    public Long getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Long pUpdatedAt) {
        mUpdatedAt = pUpdatedAt;
    }

    public Long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Long pCreatedAt) {
        mCreatedAt = pCreatedAt;
    }

    public Long getParentId() {
        return mParentId;
    }

    public void setParentId(Long pParentId) {
        mParentId = pParentId;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        mTitle = pTitle;
    }

    public String getStage() {
        return mStage;
    }

    public void setStage(String pStage) {
        mStage = pStage;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String pAnswer) {
        mAnswer = pAnswer;
    }

    public String getChildIds() {
        return childIds;
    }

    public void setChildIds(String pChildIds) {
        childIds = pChildIds;
    }


    public Long getWeight() {return weight; }

    public void setWeight(Long pWeight) {weight = pWeight; }

}
