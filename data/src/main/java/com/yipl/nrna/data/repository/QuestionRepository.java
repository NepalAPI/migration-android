package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

public class QuestionRepository implements IRepository<Question> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public QuestionRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override

    public Observable<List<Question>> getList(int pLimit) {

        return mDataStoreFactory.createDBDataStore().getAllQuestion(pLimit).map(
                questionEntities -> mDataMapper.transformQuestion(questionEntities)
        );
    }

    @Override
    public Observable<List<Question>> getListByStage(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<Question>> getListByType(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<Question>> getListByStageAndType(int pLimit, String pType, String pStage) {
        return null;
    }

    @Override
    public Observable<Question> getSingle(Long pId) {
        //// TODO: 12/9/15
        return Observable.empty();

    }

    @Override
    public Observable<List<Question>> getListByQuestionAndType(Long pId) {
        return null;
    }
}
