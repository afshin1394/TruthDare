package com.afshin.truthordare.MVVM.ViewModel;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Repository.BottleRepository;
import com.afshin.truthordare.Service.Pojo.Bottles;
import com.afshin.truthordare.Service.Responses.BaseResponse;
import com.afshin.truthordare.Utils.Constants;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.Utils.PermissionUtils;

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
    MutableLiveData<Boolean> permissionGranted = new MutableLiveData<>();


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
    public LiveData<Boolean> getPermissionGranted() {
        return permissionGranted;
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
         ActivityCompat.requestPermissions(((Activity) context), permissions, ALL_PERMISSIONS);
        }else{
          NavigateUtil.Navigate(((FragmentActivity) context), R.id.action_introductionFragment_to_bulidGameFragment,null,R.id.nav_host_fragment);
        }

    }


}
