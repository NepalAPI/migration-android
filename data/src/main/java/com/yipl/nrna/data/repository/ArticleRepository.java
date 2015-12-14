package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by Nirazan-PC on 12/14/2015.
 */
public class ArticleRepository implements IRepository<Post> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public ArticleRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper){
        mDataStoreFactory = pDataStoreFactory;
        mDataMapper = pDataMapper;
    }

    @Override
    public Observable<List<Post>> getList() {
        return mDataStoreFactory.createDBDataStore().getPostByType(-1,"text").map(
                postEntities -> mDataMapper.transformPost(postEntities)
        );
    }

    @Override
    public Observable<Post> getSingle(Long id) {
        //// TODO: 12/14/2015
        return Observable.empty();
    }
}
