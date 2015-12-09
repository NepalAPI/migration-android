package com.yipl.nrna.data.repository.datasource;

import android.content.Context;

import com.yipl.nrna.data.BuildConfig;
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

    @Inject
    public DataStoreFactory(Context pContext, DataMapper pDataMapper) {
        mContext = pContext;
        mDataMapper = pDataMapper;
    }

    public IDataStore createDataStore(){
        return createDataStore();
    }

    public RestDataStore createRestDataStore(){
        NetworkComponent networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(BuildConfig.BASE_URL))
                .build();
        return new RestDataStore(networkComponent.getApiRequest());
    }
}
