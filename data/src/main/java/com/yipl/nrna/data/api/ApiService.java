package com.yipl.nrna.data.api;

import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.util.MyConstants;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ApiService {
    @GET(MyConstants.API.LATEST_CONTENT)
    Observable<LatestContentEntity> getLatestContent(@Query("last_update") long pLastUpdateStamp);
}
