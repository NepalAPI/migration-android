package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

public class PostRepository implements IRepository<Post> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public PostRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<Post>> getListByType(int pLimit, String pType) {
        return mDataStoreFactory.createDBDataStore()
                .getPostByType(pLimit, pType)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities)
        );
    }
    @Override
    public Observable<List<Post>> getListByStage(int pLimit, String pStage) {
        return mDataStoreFactory.createDBDataStore()
                .getPostByStage(pLimit, pStage)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities)
        );
    }

    @Override
    public Observable<List<Post>> getListByStageAndType(int pLimit, String pType, String pStage) {
        return null;
    }

    @Override
    public Observable<List<Post>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllPosts(pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities));
    }

    @Override
    public Observable<Post> getSingle(Long pId) {
        //// TODO: 12/9/15
        return Observable.empty();
    }
}
