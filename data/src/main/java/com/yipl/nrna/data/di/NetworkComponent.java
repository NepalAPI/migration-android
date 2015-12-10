package com.yipl.nrna.data.di;

import com.yipl.nrna.data.Database.DatabaseHelper;
import com.yipl.nrna.data.api.ApiRequest;
import com.yipl.nrna.data.entity.mapper.DataMapper;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    Retrofit getRetrofit();

    ApiRequest getApiRequest();

    DataMapper getDataMapper();




}
