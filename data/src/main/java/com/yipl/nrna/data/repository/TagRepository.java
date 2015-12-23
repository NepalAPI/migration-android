package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/22/2015.
 */
public class TagRepository implements IRepository<String> {

    DataStoreFactory mDataStoreFactory;
    DataMapper mDataMapper;

    public TagRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper){
        mDataStoreFactory = pDataStoreFactory;
        mDataMapper = pDataMapper;
    }

    @Override
    public Observable<List<String>> getList(int pLimit) {
        //return mDataStoreFactory.createDBDataStore().getTags().asObservable();
        return null;
    }

    @Override
    public Observable<List<String>> getListByStage(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<String>> getListByType(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<String>> getListByStageAndType(int pLimit, String pType, String pStage) {
        return null;
    }

    @Override
    public Observable<String> getSingle(Long id) {
        return null;
    }

    @Override
    public Observable<List<String>> getListByQuestionAndType(Long pId) {
        return null;
    }

    @Override
    public Observable<List<String>> getListByCountry(Long pId) {
        return null;
    }
}
