package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.DataBase.Dao.ChallengerDao;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.Repository.ChallengerRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivityViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> deleteChallengers = new MutableLiveData<>();
    MutableLiveData<Error> error = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void deleteAllChallengers(Context context) {
        ChallengerRepository challengerRepository = ChallengerRepository.Instance(context);
        challengerRepository.deleteAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        deleteChallengers.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(new Error(e.getMessage(), e.getCause()));
                    }
                });

    }



    public LiveData<Boolean> deleteAllChallengersViewModel() {
        return deleteChallengers;
    }

    public LiveData<Error> getError() {
        return error;
    }
}
