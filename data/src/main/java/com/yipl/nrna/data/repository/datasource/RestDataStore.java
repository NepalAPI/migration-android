package com.yipl.nrna.data.repository.datasource;

import android.util.Log;

import com.yipl.nrna.data.api.ApiRequest;
import com.yipl.nrna.data.entity.LatestContentEntity;

import javax.inject.Inject;

import rx.Observable;

public class RestDataStore implements IDataStore{
    private static final String TAG = "RestDataSource";

    private ApiRequest mApiRequest;

    @Inject
    public RestDataStore(ApiRequest pApiRequest){
        mApiRequest = pApiRequest;
    }

    public Observable<LatestContentEntity> getLatestContents(long pLastUpdatedStamp){
        return mApiRequest.getLatestContents(pLastUpdatedStamp)
                .doOnNext(pLatestContentEntity -> {
                    //todo insert into database
                    Log.e(TAG, "getLatestContents: ");
                });
    }
}
