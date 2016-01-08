package com.yipl.nrna.data.repository.datasource;

import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentRepository implements IBaseRepository {

    private final DataStoreFactory mDataStoreFactory;

    public DeletedContentRepository(DataStoreFactory pDataStoreFactory) {
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List> getList(int pLimit) {
        return null;
    }

    @Override
    public Observable getSingle(Long pLastUpdated) {
        RestDataStore restDataStore = mDataStoreFactory.createRestDataStore();
        return mDataStoreFactory.createRestDataStore().getDeletedContent(pLastUpdated);
    }
}
