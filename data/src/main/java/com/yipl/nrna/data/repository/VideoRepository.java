package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

public class VideoRepository implements IRepository<Post> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public VideoRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<Post>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore().getPostByType(pLimit, "video").map(
                pPostEntities -> mDataMapper.transformPost(pPostEntities)
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
        return mDataStoreFactory.createDBDataStore().getPostById(pId)
                .map(pPostEntity -> mDataMapper.transformPost(pPostEntity));
    }

    @Override
    public Observable<List<Post>> getListByQuestionAndType(Long pId) {
        return mDataStoreFactory.createDBDataStore().getPostByQuestionAndType(pId, "video").map(
                postEntities -> mDataMapper.transformPost(postEntities)
        );
    }
}
