package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Country;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

public class CountryRepository implements IRepository<Country> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public CountryRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<Country>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllCountries(pLimit)
                .map(pCountryEntities -> mDataMapper.transformCountry(pCountryEntities));
    }

    @Override
    public Observable<List<Country>> getListByStage(int pLimit, String pType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<Country>> getListByType(int pLimit, String pType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<Country>> getListByStageAndType(int pLimit, String pType, String pStage) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Country> getSingle(Long pId) {
        return mDataStoreFactory.createDBDataStore()
                .getCountryById(pId)
                .map(pCountryEntity -> mDataMapper.transformCountry(pCountryEntity));
    }

    @Override
    public Observable<List<Country>> getListByQuestionAndType(Long pId) {
        return null;
    }

    @Override
    public Observable<List<Country>> getListByCountry(Long pId) {
        return null;
    }
}
