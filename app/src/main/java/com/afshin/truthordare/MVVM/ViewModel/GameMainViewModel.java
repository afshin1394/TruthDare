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

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class GameMainViewModel extends AndroidViewModel {
    public GameMainViewModel(@NonNull Application application) {
        super(application);
    }

    MutableLiveData<List<BottleModel>> bottles = new MutableLiveData<>();
    MutableLiveData<Error> error = new MutableLiveData<>();

    public void getBottles(Context context){
        BottleRepository.Instance(context).getLocalBottles()
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

    public LiveData<List<BottleModel>> getLocalBottlesLiveData() {
        return bottles;
    }

}
