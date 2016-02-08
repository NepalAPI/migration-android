package com.yipl.nrna.data.repository.datasource;

import com.yipl.nrna.domain.repository.IBaseRepository;

import java.util.List;

import rx.Observable;

/**
 * Created by Nirazan-PC on 1/8/2016.
 */
public class DeletedContentRepository implements IBaseRepository {

    private final DataStoreFactory mDataStoreFactory;

    public DeletedContentRepository(DataStoreFactory pDataStoreFactory) {
        mDataStoreFactory = pDataStoreFactory;
    }

    @Override
    public Observable<List> getList(int pLimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<Boolean> getSingle(Long pLastUpdateStamp) {
        return mDataStoreFactory.createRestDataStore()
                .getDeletedContent(pLastUpdateStamp)
                .map(pDeletedEntity -> {
                    if (pDeletedEntity != null) {
                        if (!pDeletedEntity.getPosts().isEmpty() || !pDeletedEntity.getQuestions()
                                .isEmpty() || !pDeletedEntity.getAnswers().isEmpty() ||
                                !pDeletedEntity.getUpdates().isEmpty()) {
                            return true;
                        }
                        return false;
                    }
                    return null;
                });
    }
}
