package com.yipl.nrna.domain.repository;

import java.util.List;

import rx.Observable;

public interface QRepository<T> extends IBaseRepository<T> {
    Observable<List<T>> getListByQuestion(Long pQuestionId, int pLimit);
}
