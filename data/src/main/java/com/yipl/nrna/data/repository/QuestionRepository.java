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
    public Observable<List<Question>> getList() {
        //// TODO: 12/9/15
        return Observable.empty();
    }

    @Override
    public Observable<Question> getSingle(Long pId) {
        //// TODO: 12/9/15
        return Observable.empty();
    }
}
