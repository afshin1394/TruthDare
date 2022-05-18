package com.afshin.truthordare.MVVM.ViewModel;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.DataBase.Dao.ChallengerDao;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Repository.BottleRepository;
import com.afshin.truthordare.Repository.ChallengerRepository;
import com.afshin.truthordare.Utils.Constants;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.Utils.PermissionUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
@HiltViewModel
public class MainActivityViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> deleteChallengers = new MutableLiveData<>();
    MutableLiveData<Error> error = new MutableLiveData<>();
    MutableLiveData<List<BottleModel>> bottles = new MutableLiveData<>();
    BottleRepository bottleRepository;
    ChallengerRepository challengerRepository;

    @Inject
    public MainActivityViewModel(@NonNull Application application,BottleRepository bottleRepository,ChallengerRepository challengerRepository) {
        super(application);
        this.bottleRepository = bottleRepository;
        this.challengerRepository = challengerRepository;
    }
    public void getBottles(){
        bottleRepository.getBottles()
                .subscribe(new SingleObserver<List<BottleModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("bottles", "onSubscribe ");

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
    public void deleteAllChallengers(Context context) {

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
    public LiveData<List<BottleModel>> getBottlesLiveData() {
        return bottles;
    }


    public LiveData<Boolean> deleteAllChallengersViewModel() {
        return deleteChallengers;
    }

    public LiveData<Error> getError() {
        return error;
    }

    public void checkPermissions(Context context) {
        int ALL_PERMISSIONS = Constants.ALL_PERMISSION;

        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA
        };
        boolean hasPermission = PermissionUtils.hasPermissions(context,permissions);
        Log.i("hasPremission", "checkPermissions: "+hasPermission);
        if (!hasPermission){
            ActivityCompat.requestPermissions(((FragmentActivity) context),permissions, ALL_PERMISSIONS);
        }else{
            NavigateUtil.Navigate(((FragmentActivity) context), R.id.action_introductionFragment_to_bulidGameFragment,null,R.id.nav_host_fragment);
        }

    }
}
