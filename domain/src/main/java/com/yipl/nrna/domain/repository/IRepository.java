package com.yipl.nrna.domain.repository;

import java.util.List;

import rx.Observable;

public interface IRepository<T> {
    Observable<List<T>> getList();
    Observable<T> getSingle(Long id);
}
