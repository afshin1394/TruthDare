package com.afshin.truthordare.Repository;

import androidx.annotation.NonNull;

import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DareRepository {

    private  DareRepository dareRepository;
    private  ApiService apiService;

    @Inject
    public  DareRepository(ApiService apiService){
       this.apiService = apiService;
    }




    public  Single<BaseResponse<Dares>>  getDares() {
     return   apiService.getAllDares()
              .compose(RxHttpErrorHandler.parseHttpErrors())
              .subscribeOn(Schedulers.io())
              .map(Response::body);
    }



}
