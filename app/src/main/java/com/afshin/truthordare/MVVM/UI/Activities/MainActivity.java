package com.afshin.truthordare.MVVM.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;

import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}