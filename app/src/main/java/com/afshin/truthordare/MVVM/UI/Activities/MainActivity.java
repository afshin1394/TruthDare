package com.afshin.truthordare.MVVM.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.UiAutomation;
import android.os.Bundle;
import android.util.Log;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.ViewModel.MainActivityViewModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private UIEvents uiEvents;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
    }

    @Override
    public void onBackPressed() {
      uiEvents.onBackPressed();
    }
    public void setOnBackPressedListener(UIEvents uiEvents) {
        this.uiEvents = uiEvents;
    }
}