package com.yipl.nrna.data.repository.datasource;

import android.content.Context;

import com.yipl.nrna.data.BuildConfig;
import com.yipl.nrna.data.database.DatabaseDao;
import com.yipl.nrna.data.di.DaggerNetworkComponent;
import com.yipl.nrna.data.di.NetworkComponent;
import com.yipl.nrna.data.di.NetworkModule;
import com.yipl.nrna.data.di.PerActivity;
import com.yipl.nrna.data.entity.mapper.DataMapper;

import javax.inject.Inject;

@PerActivity
public class DataStoreFactory {
    private final Context mContext;
    private final DataMapper mDataMapper;
    private final DatabaseDao mDatabaseDao;

    @Inject
    public DataStoreFactory(Context pContext, DataMapper pDataMapper, DatabaseDao pDatabaseDao) {
        mContext = pContext;
        mDataMapper = pDataMapper;
        mDatabaseDao = pDatabaseDao;
    }

    public DBDataStore createDBDataStore() {
        return new DBDataStore(mDatabaseDao);
    }

    public RestDataStore createRestDataStore() {
        NetworkComponent networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();
        return new RestDataStore(mContext, networkComponent.getApiRequest(), mDatabaseDao);
    }
}
