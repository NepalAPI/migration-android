package com.yipl.nrna.data.entity;

import java.util.List;

/**
 * Created by julian on 12/7/15.
 */
public class LatestContentEntity {
    MetaEntity meta;
    List<QuestionEntity> questions;
    List<PostEntity> posts;
    List<CountryEntity> countries;
    List<AnswerEntity> answers;

    public MetaEntity getMeta() {
        return meta;
    }

    public void setMeta(MetaEntity pMeta) {
        meta = pMeta;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> pQuestions) {
        questions = pQuestions;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> pPosts) {
        posts = pPosts;
    }

    public List<CountryEntity> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryEntity> pCountries) {
        countries = pCountries;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> pAnswers) {
        answers = pAnswers;
    }
}
