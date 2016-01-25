package com.yipl.nrna.domain.interactor;

import com.yipl.nrna.domain.executor.PostExecutionThread;
import com.yipl.nrna.domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    private Subscription mSubscription = Subscriptions.empty();

    protected UseCase(ThreadExecutor pThreadExecutor, PostExecutionThread pPostExecutionThread) {
        mPostExecutionThread = pPostExecutionThread;
        mThreadExecutor = pThreadExecutor;
    }

    protected abstract Observable<T> buildUseCaseObservable();

    protected abstract Observable<T> buildUseCaseObservable(long reference);

    public void execute(Subscriber pSubscriber) {
        this.mSubscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(pSubscriber);
    }

    public void execute(Subscriber pSubscriber, long reference) {
        this.mSubscription = this.buildUseCaseObservable(reference)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(pSubscriber);
    }

    /**
     * UnSubscribes from current {@link Subscription}.
     */
    public void unSubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
