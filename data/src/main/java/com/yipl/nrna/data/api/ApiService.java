package com.yipl.nrna.data.api;

import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.DeletedContentDataEntity;
import com.yipl.nrna.domain.util.MyConstants;

import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ApiService {
    @GET(MyConstants.API.LATEST_CONTENT)
    Observable<LatestContentEntity> getLatestContent(@Query("last_updated") long pLastUpdateStamp);

    @GET(MyConstants.API.DELETED_CONTENT)
    Observable<DeletedContentDataEntity> getDeletedContent(@Query("last_updated") long pLastUpdateStamp);

    /**
     * for debug only
     */
    @GET(MyConstants.API.LATEST_CONTENT)
    Response getLatestContentResponse();
}
