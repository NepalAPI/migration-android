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
    public Observable<List<Post>> getList(int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllPosts(null, null, -1, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities));
    }

    @Override
    public Observable<Post> getSingle(Long pId) {
        return mDataStoreFactory.createDBDataStore()
                .getPostById(pId)
                .map(pPostEntity -> mDataMapper.transformPost(pPostEntity));
    }

    @Override
    public Observable<List<Post>> getListByDownloadStatus(int pDownloadStatus, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllPosts(null, null, pDownloadStatus, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities));
    }

    @Override
    public Observable<List<Post>> getListByStage(String pStage, int pDownloadStatus, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllPosts(pStage, null, pDownloadStatus, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities)
                );
    }

    @Override
    public Observable<List<Post>> getListByType(String pType, int pDownloadStatus, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllPosts(null, pType, pDownloadStatus, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities)
                );
    }

    @Override
    public Observable<List<Post>> getListByStageAndType(String pStage, String pType, int
            pDownloadStatus, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getAllPosts(pStage, pType, pLimit, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities)
                );
    }

    @Override
    public Observable<List<Post>> getListByQuestion(Long pQuestionId, String pStage, String
            pType, int pDownloadStatus, int pLimit, boolean includeChildContents) {
        return mDataStoreFactory.createDBDataStore()
                .getPostByQuestion(pQuestionId, pStage, pType, pLimit, pLimit, includeChildContents)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities));
    }

    @Override
    public Observable<List<Post>> getListByCountry(Long pId, String pStage, String pType, int
            pDownloadStatus, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getPostByCountry(pId, pStage, pType, pDownloadStatus, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities));
    }

    @Override
    public Observable<List<Post>> getListByAnswer(Long pAnswerId, String pStage, String pType,
                                                  int pDownloadStatus, int pLimit) {
        return mDataStoreFactory.createDBDataStore()
                .getPostByAnswer(pAnswerId, pStage, pType, pDownloadStatus, pLimit)
                .map(pPostEntities -> mDataMapper.transformPost(pPostEntities));
    }

    @Override
    public Observable<Boolean> updateDownloadStatus(long pId, boolean pDownloadStatus) {
        return Observable.just(
                mDataStoreFactory.createDBDataStore()
                        .updateDownloadStatus(pId, pDownloadStatus) != -1
        );
    }

    @Override
    public Observable<Boolean> setDownloadReference(long pId, long pReference) {
        return Observable.just(
                mDataStoreFactory.createDBDataStore().setDownloadReference(pId, pReference) != -1
        );
    }
}
