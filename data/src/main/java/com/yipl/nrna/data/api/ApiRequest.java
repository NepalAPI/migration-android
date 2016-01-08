package com.yipl.nrna.data.api;

import android.util.Log;

import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.DeletedContentDataEntity;

import javax.inject.Inject;

import retrofit.Response;
import rx.Observable;

public class ApiRequest {
    ApiService mApiService;

    @Inject
    public ApiRequest(ApiService pApiService) {
        mApiService = pApiService;
    }

    public Observable<LatestContentEntity> getLatestContents(long pLatestUpdateStamp) {
        return mApiService.getLatestContent(pLatestUpdateStamp);
    }

    public Observable<DeletedContentDataEntity> getDeletedContent(long pLatestUpdateStamp){
        Observable<DeletedContentDataEntity> a = mApiService.getDeletedContent();
        return a;
    }

    /**
     * For debug only
     */
    public Response getLatestContentsResponse(long pLatestUpdateStamp) {
        return mApiService.getLatestContentResponse();
    }
}
