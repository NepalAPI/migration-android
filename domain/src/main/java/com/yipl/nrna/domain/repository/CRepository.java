package com.yipl.nrna.domain.repository;

import java.util.List;

import rx.Observable;

public interface CRepository<T> extends IBaseRepository<T> {
    Observable<List<T>> getListByCountry(Long pCountryId, int pLimit);
}
