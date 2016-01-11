package com.yipl.nrna.data.repository;

import android.util.Log;

import com.yipl.nrna.data.entity.DeletedContentEntity;
import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.data.repository.datasource.RestDataStore;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LatestContentRepository implements IBaseRepository<LatestContent> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public LatestContentRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<LatestContent>> getList(int pLimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<LatestContent> getSingle(Long pLastUpdateStamp) {
        RestDataStore restDataStore = mDataStoreFactory.createRestDataStore();
        return restDataStore.getLatestContents(pLastUpdateStamp)
                .map(pLatestContentEntity -> mDataMapper.transformLatestContent
                        (pLatestContentEntity));
    }

}
