package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.Repository.BottleRepository;
import com.afshin.truthordare.Service.Pojo.Bottles;
import com.afshin.truthordare.Service.Responses.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Response;

public class IntroductionViewModel extends AndroidViewModel {

    private Application application;
    public IntroductionViewModel(@NonNull Application application) {

        super(application);
        this.application = application;


    }
    MutableLiveData<List<BottleModel>> bottles = new MutableLiveData<>();
    MutableLiveData<Error> error = new MutableLiveData<>();

    public void getBottles(Context context){
        BottleRepository.Instance(context).getBottles()
                .subscribe(new SingleObserver<List<BottleModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<BottleModel> bottleModels) {
                        Log.i("bottles", "onSuccess: "+bottleModels);
                        bottles.postValue(bottleModels);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(new Error(e.getMessage(),e.getCause()));

                    }
                });

    }



    public LiveData<List<BottleModel>> getBottlesLiveData() {
        return bottles;
    }

    public LiveData<Error> getErrorLiveData() {
        return error;
    }
}
