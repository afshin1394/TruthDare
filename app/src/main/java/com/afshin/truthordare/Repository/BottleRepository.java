package com.afshin.truthordare.Repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.DataBase.Dao.BottleDao;
import com.afshin.truthordare.DataBase.DataBase;
import com.afshin.truthordare.DataBase.Entity.BottleEntity;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.Pojo.Bottles;
import com.afshin.truthordare.Service.Pojo.Categories;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;
import com.afshin.truthordare.Utils.RxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BottleRepository {
    private static BottleRepository bottleRepository;
    private static ApiService apiService;
    private static BottleDao bottleDao;
    public static  BottleRepository Instance(Context context) {
        if (bottleDao == null) {
            bottleDao = DataBase.getInstance(context).bottleDao();
        }
        if (apiService == null) {
            apiService = ApiClient.createService(ApiService.class);
        }

        if (bottleRepository == null) {
            bottleRepository = new BottleRepository();
        }

        return bottleRepository;
    }

    public Single<List<BottleModel>> getLocalBottles(){
      Callable<List<BottleEntity>> bottleCallable = ()-> bottleDao.getAll();
      return   Single.fromCallable(bottleCallable)
              .map(bottleEntities -> {
                  List<BottleModel> bottleModels = new ArrayList<>();
                  for (BottleEntity bottleEntity : bottleEntities) {
                      BottleModel bottleModel = new BottleModel();
                      bottleModel.setName(bottleEntity.getName());
                      bottleModel.setCategory(bottleEntity.getCategoryId());
                      bottleModel.setImage(bottleEntity.getImage());
                      bottleModel.setIsPurchased(bottleEntity.getIsPurchased());
                      bottleModels.add(bottleModel);
                  }
                  return bottleModels;
              });

    }


    public Single<List<BottleModel>> getBottles() {

        return apiService.getAllBottles()
                .compose(RxHttpErrorHandler.parseHttpErrors())
                .subscribeOn(Schedulers.io())
                .map(Response::body)
                .map(bottlesBaseResponse -> {
                    List<BottleEntity> bottleEntities = new ArrayList<>();
                    for (Bottles bottles : bottlesBaseResponse.getResult()) {
                        BottleEntity bottleEntity = new BottleEntity();
                        bottleEntity.setGuid(bottles.getBottleGuid());
                        bottleEntity.setName(bottles.getName());
                        bottleEntity.setImage(Base64.decode(bottles.getImage(), Base64.NO_WRAP));
                        bottleEntity.setIsPurchased(bottles.getIsPurchased());
                        bottleEntities.add(bottleEntity);
                    }
                     Completable.fromCallable((Callable<Integer>) () -> bottleDao.deleteAll())
                    .subscribeOn(Schedulers.io()).andThen(new Completable() {
                                 @Override
                                 protected void subscribeActual(CompletableObserver s) {
                                     Callable<Long[]> insertAllCallable = () -> bottleDao.insertAllOrders(bottleEntities);
                                     RxUtils.makeObservable(insertAllCallable)
                                             .subscribeOn(Schedulers.io())
                                             .subscribe(new Observer<Long[]>() {
                                                 @Override
                                                 public void onSubscribe(@NonNull Disposable d) {

                                                 }

                                                 @Override
                                                 public void onNext(@NonNull Long[] longs) {

                                                 }

                                                 @Override
                                                 public void onError(@NonNull Throwable e) {

                                                 }

                                                 @Override
                                                 public void onComplete() {

                                                 }
                                             });
                                 }
                             }).subscribe();




                    List<BottleModel> bottleModels = new ArrayList<>();
                    for (BottleEntity bottleEntity : bottleEntities) {
                        BottleModel bottleModel = new BottleModel();
                        bottleModel.setName(bottleEntity.getName());
                        bottleModel.setCategory(bottleEntity.getCategoryId());
                        bottleModel.setImage(bottleEntity.getImage());
                        bottleModel.setIsPurchased(bottleEntity.getIsPurchased());
                        bottleModels.add(bottleModel);
                    }

                    return bottleModels;

                });

    }
}
