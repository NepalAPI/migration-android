package com.yipl.nrna.data.repository.datasource;

import android.database.Cursor;
import android.view.KeyEvent;


import com.yipl.nrna.data.Database.DatabaseDao;
import com.yipl.nrna.data.Database.DatabaseHelper;
import com.yipl.nrna.data.api.ApiRequest;
import com.yipl.nrna.data.entity.LatestContentEntity;
import com.yipl.nrna.data.entity.PostDataEntity;
import com.yipl.nrna.data.entity.PostEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Nirazan-PC on 12/9/2015.
 */
public class DBDataStore implements IDataStore {

    DatabaseDao database;

    @Inject
    public DBDataStore(DatabaseDao database){
        this.database = database;
    }

    public Observable<List<PostEntity>> getAllPosts(int limit){
        return database.getAllPosts(limit);
    }

    public Observable<List<PostEntity>> getPostByType(int pLimit, String type){
        return database.getPostByType(pLimit, type);
    }
}
