package com.afshin.truthordare.Repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.DataBase.Dao.BottleDao;
import com.afshin.truthordare.DataBase.Dao.ChallengerDao;
import com.afshin.truthordare.DataBase.DataBase;
import com.afshin.truthordare.DataBase.Entity.ChallengerEntity;
import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.Pojo.Categories;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ChallengerRepository {

    private static ChallengerRepository challengerRepository;
    private static ChallengerDao challengerDao;
    public static ChallengerRepository Instance(Context context){
        if (challengerDao == null) {
            challengerDao = DataBase.getInstance(context).challengerDao();
        }
        if (challengerRepository == null){
            challengerRepository = new ChallengerRepository();
        }
        return challengerRepository;
    }

    public Single<List<Challenger>> getAll() {
        Callable<List<ChallengerEntity>> getAllCallable = () -> challengerDao.getAll();
        return  Single.fromCallable(getAllCallable)
                .map(challengerEntities -> {
                    Log.i("challengerEntity", "getAll: "+challengerEntities);
                    List<Challenger> challengers = new ArrayList<>();
                    for (ChallengerEntity challengerEntity : challengerEntities) {
                        Challenger challenger = new Challenger();
                        challenger.setId(challengerEntity.getId());
                        challenger.setName(challengerEntity.getName());
                        if (challengerEntity.getImage()!=null)
                        challenger.setImage(Uri.parse(challengerEntity.getImage()));
                        challenger.setEndAngle(challengerEntity.getEndAngle());
                        challenger.setStartAngle(challengerEntity.getStartAngle());
                        challengers.add(challenger);
                    }
                    return challengers;

                }).subscribeOn(Schedulers.io());
    }

    public Single<Long[]> insertAll(List<Challenger> challengers) {
        return Single.just(challengers)
                .map(challengers1 -> {
                    List<ChallengerEntity> challengerEntities = new ArrayList<>();
                    for (Challenger challenger : challengers1) {
                        ChallengerEntity challengerEntity = new ChallengerEntity();
                        challengerEntity.setColor(challenger.getColor());
                        if(challenger.getImage()!=null)
                        challengerEntity.setImage(challenger.getImage().toString());
                        challengerEntity.setName(challenger.getName());
                        challengerEntity.setStartAngle(challenger.getStartAngle());
                        challengerEntity.setEndAngle(challenger.getEndAngle());
                        challengerEntities.add(challengerEntity);
                    }
                    return challengerEntities;
                }).map(challengerEntities -> challengerDao.insertAllCahllengers(challengerEntities))
                .subscribeOn(Schedulers.io());

    }

    public Single<Integer> deleteAll() {
        Callable<Integer> deleteAllCallable  = () -> challengerDao.deleteAll();
        return Single.fromCallable(deleteAllCallable)
                .subscribeOn(Schedulers.io());
    }

    public void updateImage(String image,int id) {
         challengerDao.updateImage(image,id);
    }
}
