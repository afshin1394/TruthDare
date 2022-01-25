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

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public  class QuestionRepository {

    private static QuestionRepository questionRepository;
    private static ApiService apiService;

    public static QuestionRepository Instance(){
        if (apiService == null) {
            apiService = ApiClient.createService(ApiService.class);
        }
        if (questionRepository == null){
            questionRepository = new QuestionRepository();
        }
        return questionRepository;
    }



   public Single<BaseResponse<Questions>> getQuestions() {
       return   apiService.getAllQuestions()
               .compose(RxHttpErrorHandler.parseHttpErrors())
               .subscribeOn(Schedulers.io())
               .map(Response::body);
   }

    public  void  getQuestionByCategory(int type,StreamObserver<QuestionResponse> streamObserver){
//        ManagedChannel managedChannel = GrpcChannel.channel();
//        QuestionaireGrpc.QuestionaireStub QuestionStub = QuestionaireGrpc.newStub(managedChannel);
//        CategoryIDRequest categoryIDRequest = CategoryIDRequest.newBuilder().setCategoryIDRequest(type).build();
//        QuestionStub.getQuestionsByCategoryID(categoryIDRequest,streamObserver);
    }

}
