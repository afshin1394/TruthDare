package com.afshin.truthordare.Service.Reactive;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Flow;

import org.reactivestreams.Publisher;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RetryWithDelay implements Function<Flowable<? extends Throwable>, Flowable<?>> {
    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
    }


    @Override
    public Flowable<?> apply(@NonNull Flowable<? extends Throwable> flowable) {
        return flowable
                .flatMap((Function<Throwable, Publisher<?>>) throwable ->
                {
                    if (++retryCount < maxRetries) {
                        // When this Observable calls onNext, the original
                        // Observable will be retried (i.e. re-subscribed).
                        return Flowable.timer(retryDelayMillis,
                                TimeUnit.MILLISECONDS);
                    }

                    // Max retries hit. Just pass the error along.
                    return Flowable.error(throwable);
                });
    }
}
