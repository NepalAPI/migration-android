package com.yipl.nrna.data.di;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.yipl.nrna.data.Database.DatabaseHelper;
import com.yipl.nrna.data.api.ApiRequest;
import com.yipl.nrna.data.api.ApiService;
import com.yipl.nrna.data.api.HeaderInterceptor;
import com.yipl.nrna.data.entity.mapper.DataMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Dagger module that provides objects for API requests
 */
@Module
public class NetworkModule {
    String mBaseUrl;
    public NetworkModule(String pBaseUrl){
        mBaseUrl = pBaseUrl;
    }

    @Provides
    @Singleton
    DataMapper provideDataMapper(){
        return new DataMapper();
    }

    @Provides
    OkHttpClient provideHttpClient(){
        OkHttpClient okClient = new OkHttpClient();
        okClient.interceptors().add(new HeaderInterceptor());
        return okClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient pOkHttpClient){
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(pOkHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ApiRequest provideApiRequest(Retrofit pRetrofit){
        return new ApiRequest(pRetrofit.create(ApiService.class));
    }
}
