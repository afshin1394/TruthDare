package com.afshin.truthordare.Repository;

import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.Pojo.Categories;
import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CategoryRepository {
    private  ApiService apiService;

    @Inject
    public  CategoryRepository (ApiService apiService){
         this.apiService = apiService;
    }

    public Single<BaseResponse<Categories>> getCategories() {
        return   apiService.getAllCategories()
                .compose(RxHttpErrorHandler.parseHttpErrors())
                .subscribeOn(Schedulers.io())
                .map(Response::body);
    }
}
