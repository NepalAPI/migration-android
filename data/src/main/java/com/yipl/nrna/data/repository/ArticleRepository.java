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

    public ArticleRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataStoreFactory = pDataStoreFactory;
        mDataMapper = pDataMapper;
    }

    @Override
    public Observable<List<Post>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore().getPostByType(pLimit, "text").map(
                postEntities -> mDataMapper.transformPost(postEntities)
        );
    }

    @Override
    public Observable<List<Post>> getListByStage(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<Post>> getListByType(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<Post>> getListByStageAndType(int pLimit, String pType, String pStage) {
        return null;
    }

    @Override
    public Observable<Post> getSingle(Long pId) {
        return mDataStoreFactory.createDBDataStore().getPostById(pId).map(
                postEntity -> mDataMapper.transformPost(postEntity)
        );
    }

    @Override
    public Observable<List<Post>> getListByQuestionAndType(Long pId) {
        return mDataStoreFactory.createDBDataStore().getPostByQuestionAndType(pId, "text").map(
                postEntities -> mDataMapper.transformPost(postEntities)
        );
    }

    @Override
    public Observable<List<Post>> getListByCountry(Long pId) {
        return null;
    }
}
