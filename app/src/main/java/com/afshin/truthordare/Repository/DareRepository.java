package com.afshin.truthordare.Repository;

import androidx.annotation.NonNull;

import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DareRepository {

    private static DareRepository dareRepository;
    private static ApiService apiService;

    public static DareRepository Instance(){
        if (apiService == null) {
            apiService = ApiClient.createService(ApiService.class);
        }
        if (dareRepository == null){
            dareRepository = new DareRepository();
        }
        return dareRepository;
    }




    public  Single<BaseResponse<Dares>>  getDares() {
     return   apiService.getAllDares()
              .compose(RxHttpErrorHandler.parseHttpErrors())
              .subscribeOn(Schedulers.io())
              .map(Response::body);
    }

    public  void   getDareByCategory(){

    }

}
