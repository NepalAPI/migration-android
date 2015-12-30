package com.yipl.nrna.domain.repository;

import java.util.List;

import rx.Observable;

public interface IRepository<T> extends IBaseRepository<T> {
    Observable<List<T>> getListByStage(String pType, int pLimit);

    Observable<List<T>> getListByType(String pType, int pLimit);

    Observable<List<T>> getListByStageAndType(String pStage, String pType, int pLimit);

    Observable<List<T>> getListByQuestion(Long pQuestionId, String pStage, String pType, int
            pLimit);

    Observable<List<T>> getListByCountry(Long pId, String pStage, String pType, int pLimit);

    Observable<List<T>> getListByAnswer(Long pId, String pStage, String pType, int pLimit);
}
