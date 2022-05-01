package com.afshin.truthordare.MVVM.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.app.UiAutomation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.ViewModel.MainActivityViewModel;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Utils.Constants;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.Utils.PermissionUtils;
import com.afshin.truthordare.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private UIEvents uiEvents;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        mainActivityViewModel.getBottles(MainActivity.this);
        mainActivityViewModel.getBottlesLiveData().observe(MainActivity.this, bottleModels -> {
            Log.i("bottleModelsGood", "onChanged: "+bottleModels);
            mainActivityViewModel.checkPermissions(MainActivity.this);

        });
    }

    @Override
    public void onBackPressed() {
      uiEvents.onBackPressed();
    }
    public void setOnBackPressedListener(UIEvents uiEvents) {
        this.uiEvents = uiEvents;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mainActivityViewModel.checkPermissions(MainActivity.this);
    }



}