package com.afshin.truthordare.Service.Reactive;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class RxHttpErrorHandler {
    public static <T> SingleTransformer<Response<T>, Response<T>> parseHttpErrors() {
        return upstream -> upstream
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(2, TimeUnit.MINUTES)
                    .flatMap(t -> {
                        Log.d("ObservableTransformer", t.raw().request().url().toString());
                        Log.d("ObservableTransformer", "doOnNext: code" + t.code() + "body" + t.raw().body());
                        String errorBody = "";
                        String message = "";

                        RxProperties.HttpErrorCode httpErrorCode = RxProperties.VerifyHttpError(t);
                        if (httpErrorCode.equals(RxProperties.HttpErrorCode.Success)) {
                            return Single.just(t);

                        }

                            switch (httpErrorCode) {
                                case Bad_Gateway:
                                    errorBody = "Bad_Gateway";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;

                                case BadRequest:
                                    errorBody = "BAD_REQUEST";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Forbidden:
                                    errorBody = "Forbidden";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Internal_Server_Error:
                                    errorBody = "Internal_Server_Error";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Not_Found:
                                    errorBody = "Not_Found";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Service_Unavailable:
                                    errorBody = "Service_Unavailable";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Gateway_Timeout:
                                    errorBody = "Gateway_Timeout";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;


                                case Unauthorized:
                                    errorBody = "Unauthorized";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;

                                case Unknown:
                                    errorBody = "NullData";
                                    message = String.format("error body : %1$s , code : %2$s , url : %3$s", errorBody, t.code(), t.raw().request().url());
                                    break;
                            }
                            throw new Exception(new Throwable(message, new Throwable(t.raw().request().url().toString())));


                    }).retryWhen(new RetryWithDelay(3, 2000));
        };


    }


