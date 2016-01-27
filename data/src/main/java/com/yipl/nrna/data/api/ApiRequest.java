package com.yipl.nrna.data.api;

import com.yipl.nrna.data.entity.DeletedContentDataEntity;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.UserPreferenceEntity;

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

    public Observable<DeletedContentDataEntity> getDeletedContent(long pLatestUpdateStamp) {
        return mApiService.getDeletedContent(pLatestUpdateStamp);
    }

    public Observable<Boolean> sendUserPreference(UserPreferenceEntity pUserPreferenceEntity){
        UserPreferenceEntity entity = pUserPreferenceEntity;
        return mApiService.sendUserPreference(pUserPreferenceEntity);
    }

    /**
     * For debug only
     */
    public Response getLatestContentsResponse(long pLatestUpdateStamp) {
        return mApiService.getLatestContentResponse();
    }
}
