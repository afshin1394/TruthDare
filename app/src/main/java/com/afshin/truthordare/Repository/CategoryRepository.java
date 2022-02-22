package com.afshin.truthordare.Repository;

import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.Pojo.Categories;
import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CategoryRepository {
    private static CategoryRepository categoryRepository;
    private static ApiService apiService;


    public static CategoryRepository Instance(){
        if (apiService == null) {
            apiService = ApiClient.createService(ApiService.class);
        }
        if (categoryRepository == null){
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }

    public Single<BaseResponse<Categories>> getCategories() {
        return   apiService.getAllCategories()
                .compose(RxHttpErrorHandler.parseHttpErrors())
                .subscribeOn(Schedulers.io())
                .map(Response::body);
    }
}
