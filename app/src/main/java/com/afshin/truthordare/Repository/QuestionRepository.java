package com.afshin.truthordare.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Service.GrpcChannel;
import com.saphamrah.protos.CaregoryIDRequest;
import com.saphamrah.protos.CategoryIDRequest;
import com.saphamrah.protos.DareGrpc;
import com.saphamrah.protos.DareResponse;
import com.saphamrah.protos.Question;
import com.saphamrah.protos.QuestionRequest;
import com.saphamrah.protos.QuestionResponse;
import com.saphamrah.protos.QuestionaireGrpc;

import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public  class QuestionRepository {

    private static QuestionRepository questionRepository;

    public static QuestionRepository Instance(){
        if (questionRepository == null){
            questionRepository = new QuestionRepository();
        }
        return questionRepository;
    }



   public  void  getQuestions(StreamObserver<QuestionResponse> streamObserver) {
       ManagedChannel managedChannel = GrpcChannel.channel();
       QuestionaireGrpc.QuestionaireStub questionaireStub = QuestionaireGrpc.newStub(managedChannel);
       QuestionRequest questionRequest = QuestionRequest.newBuilder().build();
       questionaireStub.getAllQuestions(questionRequest,streamObserver);
   }

    public  void  getQuestionByCategory(int type,StreamObserver<QuestionResponse> streamObserver){
        ManagedChannel managedChannel = GrpcChannel.channel();
        QuestionaireGrpc.QuestionaireStub QuestionStub = QuestionaireGrpc.newStub(managedChannel);
        CategoryIDRequest categoryIDRequest = CategoryIDRequest.newBuilder().setCategoryIDRequest(type).build();
        QuestionStub.getQuestionsByCategoryID(categoryIDRequest,streamObserver);
    }

}
