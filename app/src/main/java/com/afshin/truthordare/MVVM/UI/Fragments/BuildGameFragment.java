package com.afshin.truthordare.MVVM.UI.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;


import com.afshin.truthordare.Adapters.Class.ChallengerNameAdapter;
import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.CustomViews.TruthDareView;
import com.afshin.truthordare.Interfaces.ChallengerNameEvents;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.UI.Activities.MainActivity;
import com.afshin.truthordare.MVVM.ViewModel.BuildGameViewModel;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Utils.BaseInfo;
import com.afshin.truthordare.Utils.CustomViewUtils;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.Utils.Enums.ToastType;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.Utils.RxUtils;
import com.afshin.truthordare.databinding.FragmentBulidGameBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */


public class BuildGameFragment extends Fragment implements AdapterView.OnItemSelectedListener, UIEvents {


    List<Challenger> challengers;

    private ChallengerNameAdapter challengerNameAdapter;
    private Context context;

    FragmentBulidGameBinding fragmentBulidGameBinding;
    private int backCounter = 0;
    private BuildGameViewModel buildGameViewModel;
    private int selectedValue;
    private boolean initSpinner;
    private boolean hasDeleteChoice;


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
    }


    public BuildGameFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        challengers = new ArrayList<>();
        initSpinner = true;
        hasDeleteChoice = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        buildGameViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.getInstance()).create(BuildGameViewModel.class);

        fragmentBulidGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulid_game, container, false);
        return fragmentBulidGameBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeSpinner();
        fragmentBulidGameBinding.BTNStartGame.setOnClickListener(view1 -> {
            int viewItemCount = challengerNameAdapter.getItemCount();
            buildGameViewModel.startGame(context,challengers,viewItemCount);
        });

        buildGameViewModel.getAllChallengers(context);

        buildGameViewModel.getBaseInfo().observe(getViewLifecycleOwner(), new Observer<BaseInfo>() {
            @Override
            public void onChanged(BaseInfo baseInfo) {
                Toast.showToast(context,baseInfo.getToastType(),baseInfo.getDuration(),baseInfo.getMessage());
            }
        });

        buildGameViewModel.getDeleteChoiceLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean delete) {
                Log.i("checkItemSelection", "onChanged: "+delete);
                hasDeleteChoice = delete;
                initializeRecyclerView(challengers,delete);
            }
        });

        buildGameViewModel.getAllChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<List<Challenger>>() {
            @Override
            public void onChanged(List<Challenger> challengers) {
                BuildGameFragment.this.challengers = challengers;
//                selectedValue = challengers.size();

                Log.i("BuildGameFragmentx", "getAllChallengersLiveData: "+challengers.toString());
                Log.i("getAllChallengersLiveData", "selectedValue: "+selectedValue);
                initializeRecyclerView(challengers,hasDeleteChoice);
                if (initSpinner)
                {
                    Log.i("initializeSpinner", "initializeSpinner: "+selectedValue);
                    fragmentBulidGameBinding.SPNumberOfChallengers.setSelection(selectedValue-3);
                    initSpinner = false;
                }

            }
        });

        buildGameViewModel.refreshChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refreshed) {

                Log.i("startGame","refreshed:" + refreshed);
                if (refreshed) {
                    navigateToGameMain();
                }
            }
        });

    }

    private void navigateToGameMain() {
        Log.i("startGame","onChanged:" + challengers);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("challengers", new ArrayList<>(challengers));
        NavigateUtil.Navigate(getActivity(), R.id.action_bulidGameFragment_to_gameMainFragment, bundle, R.id.nav_host_fragment);
    }


    private void initializeSpinner() {
        String[] items = new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        fragmentBulidGameBinding.SPNumberOfChallengers.setAdapter(adapter);
        fragmentBulidGameBinding.SPNumberOfChallengers.setOnItemSelectedListener(this);
    }

    private void initializeRecyclerView(List<Challenger> challengers,boolean hasDeleteChoice) {
        Log.i("initializeRecyclerView", "size:"+challengers.size());
        challengerNameAdapter = new ChallengerNameAdapter(challengers,hasDeleteChoice, new ChallengerNameEvents() {
            @Override
            public void onDelete(int position) {
                buildGameViewModel.deleteChallenger(selectedValue,position);
            }
        });
        fragmentBulidGameBinding.RVChallengerNames.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fragmentBulidGameBinding.RVChallengerNames.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        fragmentBulidGameBinding.RVChallengerNames.setAdapter(challengerNameAdapter);
    }


    //Spinner
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i("onItemSelected", "int value : "+i + "long Value"+ l);
        int selectedValue = Integer.parseInt(fragmentBulidGameBinding.SPNumberOfChallengers.getItemAtPosition(i).toString());
        Log.i("onItemSelected", "selected Value: "+selectedValue);
        Log.i("onItemSelected", "challengers.size: "+challengers.size() + "selected Value"+ selectedValue);
        Log.i("onItemSelected", "selected value: "+selectedValue);
        this.selectedValue = selectedValue;
        buildGameViewModel.checkItemSelection(selectedValue,challengers);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onBackPressed() {
        backCounter++;
        if (backCounter == 1) {
            com.afshin.truthordare.CustomViews.Toast.showToast(context, ToastType.INFO, ToastDuration.SHORT, getString(R.string.exitGame));
        } else {
            getActivity().finish();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backCounter = 0;
            }
        }, 2000);
    }
}