package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Answer;
import com.yipl.nrna.domain.repository.QRepository;

import java.util.List;

import rx.Observable;

public class AnswerRepository implements QRepository<Answer> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public AnswerRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<Answer>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllAnswers(pLimit)
                .map(pAnswerEntities -> mDataMapper.transformAnswer(pAnswerEntities));
    }

    @Override
    public Observable<Answer> getSingle(Long pId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<Answer>> getListByQuestion(Long pQuestionId, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllAnswersByQuestion(pQuestionId, pLimit)
                .map(pAnswerEntities -> mDataMapper.transformAnswer(pAnswerEntities));
    }

}
