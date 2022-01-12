package com.afshin.truthordare.MVVM.UI.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afshin.truthordare.Adapters.Class.GameChoiceAdapter;
import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.MVVM.ViewModel.GameChoiceViewModel;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.FragmentGameChoiceBinding;
import com.saphamrah.protos.DareModel;
import com.saphamrah.protos.DareResponse;
import com.saphamrah.protos.Question;

import java.util.ArrayList;
import java.util.List;


public class GameChoiceFragment extends DialogFragment {

    private FragmentGameChoiceBinding fragmentIntroductionBinding;
    private GameChoiceAdapter gameChoiceAdapter;
    private ArrayList<GameChoiceModel> gameChoiceModels;
    private Context context;
    private String responder;
    private String requester;
    private GameChoiceViewModel gameChoiceViewModel;
    public GameChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        responder = getArguments().getString("responder","");
        requester = getArguments().getString("requester","");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentIntroductionBinding = FragmentGameChoiceBinding.inflate(getLayoutInflater());
        return fragmentIntroductionBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeGameChoiceModels();
        fragmentIntroductionBinding.TVTitle.setText(String.format("%1$s %2$s %3$s %4$s", requester , "از", responder,context.getResources().getString(R.string.ask)));
        fragmentIntroductionBinding.TVTitle.setGravity(Gravity.CENTER);
        //      fragmentIntroductionBinding.TVTitle.setText(String.format("%1$s %2$s",responder,context.getResources().getString(R.string.WhatDoYouChoose)));
        gameChoiceAdapter = new GameChoiceAdapter(context, gameChoiceModels, gameChoiceModel -> {

        });
        fragmentIntroductionBinding.RVGameChoice.setVisibility(View.VISIBLE);
        fragmentIntroductionBinding.RVGameChoice.setLayoutManager(new GridLayoutManager(context,2));
        fragmentIntroductionBinding.RVGameChoice.setAdapter(gameChoiceAdapter);

    }

    private void initializeGameChoiceModels()
    {
        gameChoiceModels = new ArrayList<>();
        GameChoiceModel gameChoiceModel1 = new GameChoiceModel();
        gameChoiceModel1.setGameChoiceId(1);
        gameChoiceModel1.setGameChoiceImage(R.drawable.dare);
        gameChoiceModel1.setGameChoiceName(getString(R.string.dare));


        GameChoiceModel gameChoiceModel2 = new GameChoiceModel();
        gameChoiceModel2.setGameChoiceId(2);
        gameChoiceModel2.setGameChoiceImage(R.drawable.truth);
        gameChoiceModel2.setGameChoiceName(getString(R.string.Truth));

        gameChoiceModels.add(gameChoiceModel1);
        gameChoiceModels.add(gameChoiceModel2);
    }
}