package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.afshin.truthordare.Repository.DareRepository;
import com.afshin.truthordare.Repository.QuestionRepository;
import com.saphamrah.protos.DareModel;
import com.saphamrah.protos.DareResponse;
import com.saphamrah.protos.Question;
import com.saphamrah.protos.QuestionResponse;

import java.util.List;

import io.grpc.stub.StreamObserver;

public class GameChoiceViewModel extends AndroidViewModel {
    MutableLiveData<List<Question>> questions;
    MutableLiveData<List<DareModel>> dares;



    public GameChoiceViewModel(Application application) {
       super(application);
    }


    public void getQuestions() {
        QuestionRepository.Instance().getQuestions(new StreamObserver<QuestionResponse>() {
            @Override
            public void onNext(QuestionResponse value) {
                questions.postValue(value.getQuestionListList());
            }

            @Override
            public void onError(Throwable t) {
                questions.postValue(null);

            }

            @Override
            public void onCompleted() {

            }
        });
    }

    public void getDares() {
        DareRepository.Instance().getDares(new StreamObserver<DareResponse>() {
            @Override
            public void onNext(DareResponse value) {
                dares.postValue(value.getDareListList());
            }

            @Override
            public void onError(Throwable t) {
                dares.postValue(null);

            }

            @Override
            public void onCompleted() {

            }
        });
    }


    public LiveData<List<Question>> getQuestionsLiveData() {
        return questions;
    }
    public LiveData<List<DareModel>> getDaresLiveData() {
        return dares;
    }

}
