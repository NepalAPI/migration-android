package com.yipl.nrna.domain.repository;

import java.util.List;

import rx.Observable;

public interface IRepository<T> extends IBaseRepository<T> {
    Observable<List<T>> getListByStage(int pLimit, String pType);

    Observable<List<T>> getListByType(int pLimit, String pType);

    Observable<List<T>> getListByStageAndType(int pLimit, String pType, String pStage);

    Observable<List<T>> getListByQuestionAndType(Long pId);

    Observable<List<T>> getListByCountry(Long pId);

    Observable<List<T>> getListByAnswer(Long pId, int pLimit);
}
