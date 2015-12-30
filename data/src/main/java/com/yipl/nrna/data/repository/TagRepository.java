package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/22/2015.
 */
public class TagRepository implements IBaseRepository<String> {

    DataStoreFactory mDataStoreFactory;
    DataMapper mDataMapper;

    public TagRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataStoreFactory = pDataStoreFactory;
        mDataMapper = pDataMapper;
    }

    @Override
    public Observable<List<String>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore().getTags().asObservable();
    }

    @Override
    public Observable<String> getSingle(Long id) {
        throw new UnsupportedOperationException();
    }
}
