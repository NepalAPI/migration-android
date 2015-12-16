package com.yipl.nrna.data.repository;

import com.yipl.nrna.data.entity.mapper.DataMapper;
import com.yipl.nrna.data.repository.datasource.DataStoreFactory;
import com.yipl.nrna.domain.model.Post;
import com.yipl.nrna.domain.repository.IRepository;

import java.util.List;

import rx.Observable;

public class AudioRepository implements IRepository<Post> {

    private final DataStoreFactory mDataStoreFactory;
    private final DataMapper mDataMapper;

    public AudioRepository(DataStoreFactory pDataStoreFactory, DataMapper pDataMapper) {
        mDataMapper = pDataMapper;
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List<Post>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore().getPostByType(pLimit, "audio").map(
                pPostEntitites -> mDataMapper.transformPost(pPostEntitites)
        );
    }

    @Override
    public Observable<List<Post>> getListByType(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<Post>> getListByStage(int pLimit, String pType) {
        return null;
    }

    @Override
    public Observable<List<Post>> getListByStageAndType(int pLimit, String pType, String pStage) {
        return null;
    }
    @Override
    public Observable<Post> getSingle(Long pId) {
        //// TODO: 12/9/15
        return Observable.empty();
    }

    @Override
    public Observable<List<Post>> getListByQuestionAndType(Long pId) {
        return mDataStoreFactory.createDBDataStore().getPostByQuestionAndType(pId, "audio").map(
                postEntities -> mDataMapper.transformPost(postEntities)
        );
    }
}
