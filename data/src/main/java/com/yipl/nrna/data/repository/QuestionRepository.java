package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Question;
import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;

public class QuestionRepository implements IBaseRepository<Question> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public QuestionRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override

    public Observable<List<Question>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore().getAllQuestion(null, pLimit).map(
                questionEntities -> mDataMapper.transformQuestion(questionEntities)
        );
    }

    @Override
    public Observable<Question> getSingle(Long pId) {
        throw new UnsupportedOperationException();
    }
}
