package com.afshin.truthordare.Repository;

import com.afshin.truthordare.Service.ApiClient;
import com.afshin.truthordare.Service.ApiService;
import com.afshin.truthordare.Service.GrpcChannel;
import com.afshin.truthordare.Service.Pojo.Questions;
import com.afshin.truthordare.Service.Reactive.RxHttpErrorHandler;
import com.afshin.truthordare.Service.Responses.BaseResponse;
import com.saphamrah.protos.CategoryIDRequest;
import com.saphamrah.protos.QuestionResponse;
import com.saphamrah.protos.QuestionaireGrpc;

import javax.inject.Inject;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public  class QuestionRepository {

    private  ApiService apiService;


    @Inject
    public  QuestionRepository( ApiService apiService){
        this.apiService = apiService;
    }



   public Single<BaseResponse<Questions>> getQuestions() {
       return   apiService.getAllQuestions()
               .compose(RxHttpErrorHandler.parseHttpErrors())
               .subscribeOn(Schedulers.single())
               .map(Response::body);
   }



}
