package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.UserPreferenceEntity;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by Nirazan-PC on 1/26/2016.
 */
public class UserPreferenceRepository implements IBaseRepository<Boolean> {

    private final DataStoreFactory mDataStoreFactory;
    private final UserPreferenceEntity mUserPreferenceEntity;

    public UserPreferenceRepository(DataStoreFactory pDataStoreFactory, UserPreferenceEntity pUserPreferenceEntity) {
        mDataStoreFactory = pDataStoreFactory;
        mUserPreferenceEntity = pUserPreferenceEntity;
    }

    @Override
    public Observable<List<Boolean>> getList(int pLimit) {
        return null;
    }

    @Override
    public Observable<Boolean> getSingle(Long id) {
        return mDataStoreFactory.createRestDataStore().sendUserPreference(mUserPreferenceEntity);
    }
}
