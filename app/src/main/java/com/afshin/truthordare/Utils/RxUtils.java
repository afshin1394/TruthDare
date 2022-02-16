package com.afshin.truthordare.Utils;

import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RxUtils {

    public static <T> Observable<T> makeObservable(final Callable<T> func) {

        return new Observable<T>() {
            @Override
            protected void subscribeActual(Observer<? super T> observer) {
                try {
                    observer.onNext(func.call());

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("RxUtils", "Error makeObservable");

                }
            }
        };
    }
}
