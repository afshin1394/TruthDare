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
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.UI.Activities.MainActivity;
import com.afshin.truthordare.MVVM.ViewModel.BuildGameViewModel;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Map<Integer, String> challengerNames = new HashMap<>();
    List<Challenger> challengers;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ChallengerNameAdapter challengerNameAdapter;
    private ArrayList<EditText> editTextList;
    private Context context;

    FragmentBulidGameBinding fragmentBulidGameBinding;
    private int backCounter = 0;
    private BuildGameViewModel buildGameViewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
    }


    public BuildGameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        challengers = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


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
        initializeRecyclerView(challengers);
        fragmentBulidGameBinding.BTNStartGame.setOnClickListener(view1 -> {
            startGameClick(challengers);
        });
        buildGameViewModel.getAllChallengers(context);

        buildGameViewModel.getAllChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<List<Challenger>>() {
            @Override
            public void onChanged(List<Challenger> challengers) {
                BuildGameFragment.this.challengers = challengers;
                Log.i("setttt", "onChanged: "+challengers.size());

                if (challengers.size() == 0)
                    initializeSpinner(0);
                else
                    initializeSpinner(challengers.size() - 3);

                challengerNameAdapter.setChallengers(BuildGameFragment.this.challengers);

            }
        });

        buildGameViewModel.getAllModifiedChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<List<Challenger>>() {
            @Override
            public void onChanged(List<Challenger> challengers) {
                Log.i("settttModified", "onChanged: "+challengers.size());

                challengerNameAdapter.setChallengers(BuildGameFragment.this.challengers);

            }
        });



//        buildGameViewModel.getBottles(context);
//        buildGameViewModel.getBottlesLiveData().observe(getViewLifecycleOwner(), new Observer<BottleModel>() {
//            @Override
//            public void onChanged(BottleModel bottleModel) {
//
//            }
//        });


    }


    private void startGameClick(List<Challenger> challengers) {


        if (challengers.size() < challengerNameAdapter.getItemCount()) {
            Toast.showToast(context, ToastType.INFO, ToastDuration.SHORT, "نام همه بازیکنان را وارد کنید");

        } else {
            for (int i = 0; i < challengers.size(); i++) {
                if (challengers.get(i).getName() == null) {
                    Toast.showToast(context, ToastType.INFO, ToastDuration.SHORT, "نام همه بازیکنان را وارد کنید");
                    break;
                }
                challengers.get(i).setColor(CustomViewUtils.generateRandomColor());
                if (i == challengers.size() - 1)
                    startGame(challengers);
            }
        }
    }

    private void startGame(List<Challenger> challengersList) {
        buildGameViewModel.refreshChallengers(context, challengersList);
        buildGameViewModel.refreshChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refreshed) {
                if (refreshed) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("challengers", new ArrayList<>(challengersList));
                    NavigateUtil.Navigate(getActivity(), R.id.action_bulidGameFragment_to_gameMainFragment, bundle, R.id.nav_host_fragment);
                }

            }
        });

    }


    private void initializeSpinner(int selection) {
        String[] items = new String[]{"3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        fragmentBulidGameBinding.SPNumberOfChallengers.setAdapter(adapter);
        fragmentBulidGameBinding.SPNumberOfChallengers.setOnItemSelectedListener(this);
        fragmentBulidGameBinding.SPNumberOfChallengers.setSelection(selection);
    }

    private void initializeRecyclerView(List<Challenger> challengers) {
        challengerNameAdapter = new ChallengerNameAdapter(challengers);
        fragmentBulidGameBinding.RVChallengerNames.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fragmentBulidGameBinding.RVChallengerNames.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        fragmentBulidGameBinding.RVChallengerNames.setAdapter(challengerNameAdapter);
    }


    //Spinner
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        fragmentBulidGameBinding.SPNumberOfChallengers.setSelection(i);
        int selectedValue = Integer.parseInt(fragmentBulidGameBinding.SPNumberOfChallengers.getItemAtPosition(i).toString());
        Log.i("selectedValuee", "onItemSelected: "+selectedValue);
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