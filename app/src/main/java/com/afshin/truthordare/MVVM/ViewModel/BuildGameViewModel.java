package com.afshin.truthordare.MVVM.ViewModel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.Repository.BottleRepository;
import com.afshin.truthordare.Repository.ChallengerRepository;
import com.afshin.truthordare.Utils.BaseInfo;
import com.afshin.truthordare.Utils.CustomViewUtils;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.Utils.Enums.ToastType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BuildGameViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> challengersRefresh = new MutableLiveData<>();
    MutableLiveData<List<Challenger>> challengers = new MutableLiveData<>();
    MutableLiveData<Boolean> deleteChoice = new MutableLiveData<>();
    MutableLiveData<BaseInfo> baseInfo = new MutableLiveData<>();

    public BuildGameViewModel(@NonNull Application application) {
        super(application);
    }

    public void refreshChallengers(Context context, List<Challenger> challengerModels) {
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
                                        Log.i("insertAll", "onError: "+e.getMessage());
                                        baseInfo.postValue(new BaseInfo(ToastType.ERROR, ToastDuration.SHORT, "مشکلی پیش اومده!"));

                                    }
                                });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("deleteAll", "onError: "+e.getMessage());
                        baseInfo.postValue(new BaseInfo(ToastType.ERROR, ToastDuration.SHORT, "مشکلی پیش اومده!"));
                    }
                });


    }

    public void getAllChallengers(Context context) {
        ChallengerRepository challengerRepository = ChallengerRepository.Instance(context);
        challengerRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Challenger>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Challenger> challengersModels) {
                        Log.i("BuildGameFragmentx", "onSuccess: "+challengersModels);
                        challengers.setValue(challengersModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        baseInfo.postValue(new BaseInfo(ToastType.ERROR, ToastDuration.SHORT, "مشکلی پیش اومده!"));

                    }
                });

    }

    public void checkItemSelection(int selectedValue, List<Challenger> challengers) {

        int challengerCreateCount = 0;
        Log.i("checkItemSelection", "init: " + challengers.size() + "selected value:" + selectedValue);

        List<Challenger> preChallengers = getPreviousChallengers(challengers);
        challengers.clear();
        challengers.addAll(preChallengers);
        Log.i("checkItemSelection", "getPreviousChallengers: " + challengers.size() + "selected value :" + selectedValue);
        if (selectedValue >= challengers.size()) {
            challengerCreateCount = selectedValue - challengers.size();
            Log.i("checkItemSelection", "selectedValue >= challengers.size() " + challengerCreateCount);
            for (int i = 0; i < challengerCreateCount; i++) {
                Challenger challenger = new Challenger();
                challengers.add(challenger);
            }
            deleteChoice.postValue(false);
        } else {
            Log.i("checkItemSelection", "selectedValue < challengers.size(): " + challengerCreateCount);
            challengerCreateCount = challengers.size() - selectedValue;
            if (challengerCreateCount < 0) {
                Log.i("checkItemSelection", "deleteChoice: false");
                deleteChoice.postValue(false);
            }
            else {
                Log.i("checkItemSelection", "deleteChoice: true");
                deleteChoice.postValue(true);
            }

        }

        Log.i("checkItemSelection", "challengers.size(): " + challengers.size());
        Log.i("checkItemSelection", "challengers" + challengers);
        this.challengers.setValue(challengers);
    }

    public void deleteChallenger(int selectedValue, int position) {

        Log.i("deleteChallenger", "deleteChallenger: "+this.challengers.getValue().get(position));
        if (this.challengers.getValue().get(position) != null) {
            this.challengers.getValue().remove(position);
        }
        this.challengers.setValue(this.challengers.getValue());

        Log.i("deleteChallenger", "deleteChallenger: " + this.challengers.getValue() + "selectedValue"+selectedValue);

        if (this.challengers.getValue().size() == selectedValue)
            this.deleteChoice.setValue(false);




    }


    private List<Challenger> getPreviousChallengers(List<Challenger> challengers) {
        List<Challenger> preChallengers = new ArrayList<>();
        for (Challenger challenger : challengers) {
            if (challenger.getName() != null) {
                if (!challenger.getName().equals(""))
                    preChallengers.add(challenger);
            }
        }
        return preChallengers;

    }


    public void startGame(Context context, List<Challenger> challengers, int viewItemCount) {

        Log.i("startGameClick", " challenger size: " + challengers.size() + "challengerNameAdapter.getItemCount(): " + viewItemCount);
        if (challengers.size() < viewItemCount) {
            baseInfo.postValue(new BaseInfo(ToastType.INFO, ToastDuration.LONG, "نام همه بازیکنان را وارد کنید"));
        } else {
            for (int i = 0; i < challengers.size(); i++) {
                if (challengers.get(i).getName() == null || challengers.get(i).getName().equals("")) {
                    Log.i("startGameClick", "name:" + challengers.get(i).getName());
                    baseInfo.postValue(new BaseInfo(ToastType.INFO, ToastDuration.LONG, "نام همه بازیکنان را وارد کنید"));
                    break;
                }
                challengers.get(i).setColor(CustomViewUtils.generateRandomColor());
                if (i == challengers.size() - 1)
                    refreshChallengers(context, challengers);
            }
        }
    }

    public LiveData<Boolean> refreshChallengersLiveData() {
        return challengersRefresh;
    }

    public LiveData<List<Challenger>> getAllChallengersLiveData() {
        return challengers;
    }



    public LiveData<Boolean> getDeleteChoiceLiveData() {
        return deleteChoice;
    }


    public LiveData<BaseInfo> getBaseInfo() {
        return baseInfo;
    }

    public void checkProfilePic(int position) {

    }

    public void setChallengerImage(Context context,Uri uri, int position) {
        Objects.requireNonNull(challengers.getValue()).get(position).setImage(uri);
        ChallengerRepository challengerRepository = ChallengerRepository.Instance(context);
        challengerRepository.updateImage(uri.toString(),challengers.getValue().get(position).getId());
    }
}
