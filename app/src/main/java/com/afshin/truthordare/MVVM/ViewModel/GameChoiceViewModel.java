package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Repository.DareRepository;
import com.afshin.truthordare.Repository.QuestionRepository;
import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Pojo.Questions;
import com.afshin.truthordare.Service.Responses.BaseResponse;
import com.afshin.truthordare.Utils.Enums.Category;
import com.saphamrah.protos.Question;
import com.saphamrah.protos.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

import io.grpc.stub.StreamObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class GameChoiceViewModel extends AndroidViewModel {
    MutableLiveData<List<GameChoiceModel>> gameChoices = new MutableLiveData<>();
    MutableLiveData<Error> error =  new MutableLiveData<>();




    public GameChoiceViewModel(Application application) {
       super(application);

    }


    public void getQuestions() {
        QuestionRepository.Instance().getQuestions()
                .map((Function<BaseResponse<Questions>, List<GameChoiceModel>>) questionsBaseResponse -> {
                    List<GameChoiceModel> gameChoiceModels = new ArrayList<>();
                    for (Questions question : questionsBaseResponse.getResult()) {
                        GameChoiceModel gameChoiceModel = new GameChoiceModel();
                        gameChoiceModel.setId(question.getCategoryID());
                        gameChoiceModel.setTitle(question.getCategoryName());
                        gameChoiceModel.setBody(question.getQuestionStr());
                        gameChoiceModels.add(gameChoiceModel);
                    }
                    return gameChoiceModels;
                })
                .subscribe(new SingleObserver<List<GameChoiceModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<GameChoiceModel> gameChoiceModels) {
                        gameChoices.postValue(gameChoiceModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(new Error(e.getMessage(),e.getCause()));
                    }
                });
    }

    public void getDares() {
        DareRepository.Instance().getDares()
                .map((Function<BaseResponse<Dares>, List<GameChoiceModel>>) questionsBaseResponse -> {
                    List<GameChoiceModel> gameChoiceModels = new ArrayList<>();
                    for (Dares dares : questionsBaseResponse.getResult()) {
                        GameChoiceModel gameChoiceModel = new GameChoiceModel();
                        gameChoiceModel.setId(dares.getCategoryID());
                        gameChoiceModel.setTitle(dares.getCategoryName());
                        gameChoiceModel.setBody(dares.getDareStr());
                        gameChoiceModels.add(gameChoiceModel);
                    }
                    return gameChoiceModels;
                })
                .subscribe(new SingleObserver<List<GameChoiceModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<GameChoiceModel> gameChoiceModels) {
                        gameChoices.postValue(gameChoiceModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(new Error(e.getMessage(),e.getCause()));
                    }
                });
    }

    public void getTruthOrDare(){
        List<GameChoiceModel> gameChoiceModels = new ArrayList<>();
        GameChoiceModel gameChoiceModel = new GameChoiceModel();
        gameChoiceModel.setId(1001);
        gameChoiceModel.setTitle("حقیقت");
        gameChoiceModel.setImage(R.drawable.cat);

        GameChoiceModel gameChoiceModel2 = new GameChoiceModel();
        gameChoiceModel.setId(2001);
        gameChoiceModel.setTitle("جرئت");
        gameChoiceModel.setImage(R.drawable.hammer);

        gameChoiceModels.add(gameChoiceModel);
        gameChoiceModels.add(gameChoiceModel2);

        gameChoices.setValue(gameChoiceModels);
    }

    public LiveData<List<GameChoiceModel>> getGameChoices() {
        return gameChoices;
    }

    public LiveData<Error> getErrorLiveData() {
        return error;
    }








}
