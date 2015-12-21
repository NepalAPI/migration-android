package com.yipl.nrna.domain.repository;

import java.util.List;

import rx.Observable;

public interface IRepository<T> {
    Observable<List<T>> getList(int pLimit);

    Observable<List<T>> getListByStage(int pLimit, String pType);

    Observable<List<T>> getListByType(int pLimit, String pType);

    Observable<List<T>> getListByStageAndType(int pLimit, String pType, String pStage);

    Observable<T> getSingle(Long id);

    Observable<List<T>> getListByQuestionAndType(Long pId);

    Observable<List<T>> getListByCountry(Long pId);
}
