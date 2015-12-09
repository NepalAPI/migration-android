package com.yipl.nrna.data.api;

import com.yipl.nrna.data.entity.LatestContentEntity;

import javax.inject.Inject;

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
}
