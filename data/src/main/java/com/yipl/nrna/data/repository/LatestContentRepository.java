package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.data.repository.datasource.RestDataStore;
import com.yipl.nrna.domain.model.LatestContent;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

public class LatestContentRepository implements IRepository<LatestContent> {
    private static final String TAG = "LatestContentRepository";

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public LatestContentRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<LatestContent>> getList(int pLimit) {
        return null;
    }

    @Override
    public Observable<List<LatestContent>> getListByType(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<LatestContent>> getListByStage(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<LatestContent>> getListByStageAndType(int pLimit, String pType, String pStage) {
        return null;
    }

    @Override
    public Observable<LatestContent> getSingle(Long pLastUpdateStamp) {
        RestDataStore restDataStore = mDataStoreFactory.createRestDataStore();

        restDataStore.getLatestContents(pLastUpdateStamp);
        return restDataStore.getLatestContents(pLastUpdateStamp)
                .map(pLatestContentEntity -> mDataMapper.transformLatestContent
                        (pLatestContentEntity));
    }

    @Override
    public Observable<List<LatestContent>> getListByQuestionAndType(Long pId) {
        return null;
    }
}
