package com.afshin.truthordare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TruthDareView truthDareView=new TruthDareView(MainActivity.this);
        Log.i("Path", "onCreate: "+truthDareView.getPath());
    }
}