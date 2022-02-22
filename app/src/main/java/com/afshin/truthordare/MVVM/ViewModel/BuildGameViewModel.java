package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.Repository.BottleRepository;
import com.afshin.truthordare.Repository.ChallengerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BuildGameViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> challengersRefresh = new MutableLiveData<>();
    MutableLiveData<List<Challenger>> challengers = new MutableLiveData<>();
    MutableLiveData<List<Challenger>> modifiedChallengers = new MutableLiveData<>();
    MutableLiveData<Error> error = new MutableLiveData<>();

    public BuildGameViewModel(@NonNull Application application) {
        super(application);
    }

    public void refreshChallengers(Context context,List<Challenger> challengerModels){
        ChallengerRepository challengerRepository = ChallengerRepository.Instance(context);
        challengerRepository.deleteAll()
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                            challengerRepository.insertAll(challengerModels)
                                    .subscribe(new SingleObserver<Long[]>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(@NonNull Long[] inserted) {
                                            Log.i("bottles", "onSuccess: " + inserted);
                                            List<Long> in = Arrays.asList(inserted);
                                            if (in.contains(-1)) {
                                                onError(new Throwable());
                                            } else {
                                                challengersRefresh.postValue(true);
                                            }


                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            error.postValue(new Error(e.getMessage(), e.getCause()));

                                        }
                                    });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(new Error(e.getMessage(), e.getCause()));
                    }
                });


    }

    public void getAllChallengers(Context context)
    {
        ChallengerRepository challengerRepository = ChallengerRepository.Instance(context);
        challengerRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Challenger>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Challenger> challengersModels) {
                        Log.i("challengerssss", "onSuccess: "+challengersModels.size());
                         challengers.postValue(challengersModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        error.postValue(new Error(e.getMessage(), e.getCause()));

                    }
                });

    }


    public LiveData<Boolean> refreshChallengersLiveData() {
        return challengersRefresh;
    }

    public LiveData<List<Challenger>> getAllChallengersLiveData() {
        return challengers;
    }
    public LiveData<List<Challenger>> getAllModifiedChallengersLiveData() {
        return modifiedChallengers;
    }

    public LiveData<Error> getErrorLiveData() {
        return error;
    }

    public void checkItemSelection(int selectedValue, List<Challenger> challengers) {

        int challengerCreateCount = 0;
        List<Challenger> preChallengers = getPreviousChallengers(challengers);
        challengers.clear();
        challengers.addAll(preChallengers);

            if (selectedValue > challengers.size()) {
                challengerCreateCount = selectedValue - challengers.size();
                for (int i = 0; i < challengerCreateCount; i++) {
                    Challenger challenger = new Challenger();
                    challengers.add(challenger);
                }
            } else {
                challengerCreateCount = challengers.size() - selectedValue;
                for (int i = challengerCreateCount; i > 0; i--) {
                    challengers.remove(i);
                }
            }


            this.modifiedChallengers.postValue(challengers);
    }

    private List<Challenger> getPreviousChallengers(List<Challenger> challengers) {
        List<Challenger> preChallengers = new ArrayList<>();
        for (Challenger challenger : challengers) {
            if (challenger.getName()!=null){
                preChallengers.add(challenger);
            }
        }
        return preChallengers;

    }
}
