package com.yipl.nrna.data.repository.datasource;

import com.yipl.nrna.data.database.DatabaseDao;
import com.yipl.nrna.data.entity.AnswerEntity;
import com.yipl.nrna.data.entity.CountryEntity;
import com.yipl.nrna.data.entity.PostEntity;
import com.yipl.nrna.data.entity.QuestionEntity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/9/2015.
 */
public class DBDataStore implements IDataStore {

    DatabaseDao database;

    @Inject
    public DBDataStore(DatabaseDao database) {
        this.database = database;
    }

    public Observable<List<PostEntity>> getAllPosts(int limit) {
        return database.getAllPosts(limit);
    }

    public Observable<List<PostEntity>> getPostByType(int pLimit, String pType) {
        return database.getPostByType(pLimit, pType);
    }

    public Observable<List<PostEntity>> getPostByStage(int pLimit, String pStage) {
        return database.getPostByStage(pLimit, pStage);
    }

    public Observable<PostEntity> getPostById(Long pId) {
        return database.getPostById(pId);
    }

    public Observable<List<QuestionEntity>> getAllQuestion(int limit) {
        return database.getAllQuestions(limit);
    }

    public Observable<List<PostEntity>> getPostByQuestionAndType(Long pQId, String pType) {
        return database.getPostByQuestionAndType(pQId, pType);
    }

    public Observable<List<PostEntity>> getPostByAnswer(Long pAnswerId, int pLimit) {
        return database.getPostByAnswer(pAnswerId, pLimit);
    }

    public Observable<List<AnswerEntity>> getAllAnswers(int limit) {
        return database.getAllAnswers(limit);
    }

    public Observable<List<AnswerEntity>> getAllAnswersByQuestion(Long pQuestionId, int pLimit) {
        return database.getAnswersByQuestion(pQuestionId, pLimit);
    }

    public Observable<List<CountryEntity>> getAllCountries(int pLimit) {
        return database.getAllCountries(pLimit);
    }

    public Observable<CountryEntity> getCountryById(long pId) {
        return database.getCountryById(pId);
    }

    public Observable<List<PostEntity>> getPostByCountry(Long pId) {
        return database.getPostByCountry(pId);
    }

    public Observable<List<String>> getTags() {
        return database.getTags();
    }

}
