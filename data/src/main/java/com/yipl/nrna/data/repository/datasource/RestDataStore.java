package com.yipl.nrna.data.repository.datasource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.yipl.nrna.data.Database.DatabaseDao;
import com.yipl.nrna.data.api.ApiRequest;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.exception.NetworkConnectionException;

import java.io.IOException;

import retrofit.Response;
import rx.Observable;

public class RestDataStore implements IDataStore {

    private final Context mContext;
    private final ApiRequest mApiRequest;
    private final DatabaseDao mDatabase;

    public RestDataStore(Context pContext, ApiRequest pApiRequest, DatabaseDao pDatabase) {
        mApiRequest = pApiRequest;
        mDatabase = pDatabase;
        mContext = pContext;
    }

    public Observable<LatestContentEntity> getLatestContents(long pLastUpdatedStamp) {
        if (isThereInternetConnection()) {
            //getLatestContentsResponse(pLastUpdatedStamp);
            return mApiRequest.getLatestContents(pLastUpdatedStamp)
                    .doOnNext(pLatestContentEntity -> mDatabase.insertUpdate(pLatestContentEntity));
        } else {
            return Observable.error(new NetworkConnectionException());
        }
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    /**
     * for debug only
     */
    public void getLatestContentsResponse(long pLastUpdatedStamp) {
        Response response = mApiRequest.getLatestContentsResponse(pLastUpdatedStamp);
        try {
            Log.d("myLog-ApiResponse: ", response.raw().body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
