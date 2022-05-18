package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Repository.CategoryRepository;
import com.afshin.truthordare.Repository.DareRepository;
import com.afshin.truthordare.Repository.QuestionRepository;
import com.afshin.truthordare.Service.Pojo.Categories;
import com.afshin.truthordare.Service.Pojo.Dares;
import com.afshin.truthordare.Service.Pojo.Questions;
import com.afshin.truthordare.Service.Responses.BaseResponse;
import com.afshin.truthordare.Utils.Enums.Category;
import com.saphamrah.protos.Question;
import com.saphamrah.protos.QuestionResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.grpc.stub.StreamObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Response;
@HiltViewModel
public class GameChoiceViewModel extends AndroidViewModel {
    MutableLiveData<List<GameChoiceModel>> gameChoices = new MutableLiveData<>();
    MutableLiveData<Error> error =  new MutableLiveData<>();


   private CategoryRepository categoryRepository;
   private QuestionRepository questionRepository;
   private DareRepository dareRepository;

   @Inject
    public GameChoiceViewModel(Application application,CategoryRepository categoryRepository
            ,QuestionRepository questionRepository
     ,DareRepository dareRepository) {
       super(application);
       this.categoryRepository = categoryRepository;
       this.questionRepository = questionRepository;
       this.dareRepository  = dareRepository;
    }


    public void getQuestions() {
        questionRepository.getQuestions()
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
        dareRepository.getDares()
                .map(daresBaseResponse -> {
                    List<GameChoiceModel> gameChoiceModels = new ArrayList<>();
                    for (Dares dares : daresBaseResponse.getResult()) {
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
        gameChoiceModel.setBody("حقیقت");
        gameChoiceModel.setImage(R.drawable.mon_yellow);

        GameChoiceModel gameChoiceModel2 = new GameChoiceModel();
        gameChoiceModel2.setId(2001);
        gameChoiceModel2.setBody("جرئت");
        gameChoiceModel2.setImage(R.drawable.mon_red);

        gameChoiceModels.add(gameChoiceModel);
        gameChoiceModels.add(gameChoiceModel2);

        gameChoices.setValue(gameChoiceModels);
    }


    public void getAllCategories(){
        categoryRepository.getCategories()
                .map(daresBaseResponse -> {
                    List<GameChoiceModel> gameChoiceModels = new ArrayList<>();
                    for (Categories categories : daresBaseResponse.getResult()) {
                        GameChoiceModel gameChoiceModel = new GameChoiceModel();
                        gameChoiceModel.setId(categories.getCategoryID());
                        gameChoiceModel.setTitle(categories.getCategoryName());
                        gameChoiceModel.setBody(categories.getCategoryName());
                        gameChoiceModel.setImageServer(Base64.decode(categories.getCategoryImage(), Base64.NO_WRAP) );
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



    public LiveData<List<GameChoiceModel>> getGameChoices() {
        return gameChoices;
    }

    public LiveData<Error> getErrorLiveData() {
        return error;
    }








}
