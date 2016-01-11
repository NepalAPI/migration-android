package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.CountryUpdate;
import com.yipl.nrna.domain.repository.CRepository;

import java.util.List;

import rx.Observable;

public class CountryUpdateRepository implements CRepository<CountryUpdate> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public CountryUpdateRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<CountryUpdate>> getList(int pLimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<CountryUpdate> getSingle(Long pId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<CountryUpdate>> getListByCountry(Long pCountryId, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getUpdatesByCountry(pCountryId, pLimit)
                .map(pUpdateEntities -> mDataMapper.transformCountryUpdate(pUpdateEntities));
    }

}
