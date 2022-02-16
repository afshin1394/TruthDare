package com.afshin.truthordare.MVVM.UI.Fragments;

import static com.afshin.truthordare.Utils.Enums.ToastType.ERROR;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afshin.truthordare.Adapters.Class.GameChoiceAdapter;
import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.ViewModel.GameChoiceViewModel;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Service.Pojo.Questions;
import com.afshin.truthordare.Service.Responses.BaseResponse;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.databinding.FragmentGameChoiceBinding;
import com.saphamrah.protos.DareModel;
import com.saphamrah.protos.DareResponse;
import com.saphamrah.protos.Question;

import java.util.ArrayList;
import java.util.List;


public class GameChoiceFragment extends DialogFragment implements UIEvents {

    private FragmentGameChoiceBinding fragmentIntroductionBinding;
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
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_drawable);
        gameChoiceViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.getInstance()).create(GameChoiceViewModel.class);


        fragmentIntroductionBinding.TVTitle.setText(String.format("%1$s %2$s %3$s %4$s", requester , "از", responder,context.getResources().getString(R.string.ask)));
        fragmentIntroductionBinding.TVTitle.setGravity(Gravity.CENTER);

        gameChoiceViewModel.getTruthOrDare();
        gameChoiceViewModel.getGameChoices().observe(getViewLifecycleOwner(), this::setAdapter);

        gameChoiceViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            Toast.showToast(getActivity(),ERROR, ToastDuration.SHORT,error.getMessage());
        });


    }


    private void setAdapter(List<GameChoiceModel> gameChoiceModels){
        GameChoiceAdapter gameChoiceAdapter = new GameChoiceAdapter(context, gameChoiceModels, gameChoiceModel -> {
            switch (gameChoiceModel.getId()){
                case 1001:
//                    gameChoiceViewModel.getAllCategories();
                    gameChoiceViewModel.getQuestions();
                    break;
                case 2001:
                    gameChoiceViewModel.getDares();
                    break;
            }
        });

        fragmentIntroductionBinding.RVGameChoice.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        fragmentIntroductionBinding.RVGameChoice.setAdapter(gameChoiceAdapter);

        //      fragmentIntroductionBinding.TVTitle.setText(String.format("%1$s %2$s",responder,context.getResources().getString(R.string.WhatDoYouChoose)));

        Log.i("adapterItems", "setAdapter: "+fragmentIntroductionBinding.RVGameChoice.getAdapter().getItemCount());
    }


    @Override
    public void onBackPressed() {

    }
}