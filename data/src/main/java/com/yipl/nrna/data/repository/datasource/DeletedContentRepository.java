package com.yipl.nrna.data.repository.datasource;

import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentRepository implements IBaseRepository {

    private final DataStoreFactory mDataStoreFactory;
    private Long mLastUpdateStamp;

    public DeletedContentRepository(DataStoreFactory pDataStoreFactory, long pLastUpdateStamp) {
        mDataStoreFactory = pDataStoreFactory;
        mLastUpdateStamp = pLastUpdateStamp;
    }

    @Override
    public Observable<List> getList(int pLimit) {
        return null;
    }

    @Override
    public Observable getSingle(Long id) {
        return mDataStoreFactory.createRestDataStore().getDeletedContent(mLastUpdateStamp);
    }
}
