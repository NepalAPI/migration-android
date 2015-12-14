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
    public Observable<List<Post>> getList() {
        //// TODO: 12/9/15
        return mDataStoreFactory.createDBDataStore().getPostByType(-1, "video").map(
                pPostEntitites -> mDataMapper.transformPost(pPostEntitites)
        );
    }

    @Override
    public Observable<Post> getSingle(Long pId) {
        //// TODO: 12/9/15
        return Observable.empty();
    }
}
