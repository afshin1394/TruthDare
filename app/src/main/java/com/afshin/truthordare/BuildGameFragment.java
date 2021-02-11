package com.afshin.truthordare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afshin.truthordare.databinding.FragmentBulidGameBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BuildGameFragment extends Fragment implements NumberPicker.OnValueChangeListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Map<Integer, String> challengerNames = new HashMap<>();
    List<Challenger> challengers = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ChallengerNameAdapter challengerNameAdapter;
    private ArrayList<EditText> editTextList;

    FragmentBulidGameBinding fragmentBulidGameBinding;


    private void chooseNumberOfChallengersTVClick() {

//        ArrayList<Challenger> challengers = new ArrayList<>();
//
//        if (challengerNames.size() < challengerNameAdapter.getItemCount())
//        {
//            Toast.makeText(getActivity(), "نام همه بازیکنان را وارد کنید", Toast.LENGTH_LONG).show();
//        }
//        else
//            {
//            TruthDareView truthDareView = new TruthDareView(getActivity());
//            for (Integer key : challengerNames.keySet())
//            {
//                Log.i("challengerNames", "chooseNumberOfChallengersTVClick: " + key + " " + challengerNames.get(key));
//                Challenger challenger = new Challenger(challengerNames.get(key), truthDareView.generateRandomColor());
//                challengers.add(challenger);
//            }
//
//
//            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("challengers", challengers);
//            NavigateUtil navigateUtil = NavigateUtil.getInstance();
//            navigateUtil.navigate(getActivity(), R.id.action_bulidGameFragment_to_gameMainFragment, bundle,R.id.nav_host_fragment);
//        }
    }

    public BuildGameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentBulidGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulid_game, container, false);
        return fragmentBulidGameBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeNumberPicker();
        initializeRecyclerView();
        fragmentBulidGameBinding.TVChooseNumberOfChallengers.setOnClickListener(this::chooseNumberOfChallengersTVClick);
    }

    private void chooseNumberOfChallengersTVClick(View view) {
        challengers.forEach(new Consumer<Challenger>() {
            @Override
            public void accept(Challenger challenger) {
                Log.i("challengerNames", "accept: " + challenger.getName());
            }
        });

    }


    private void initializeNumberPicker() {

        NumberPicker numberPicker = fragmentBulidGameBinding.NPChooseNumberOfChallengers;
        numberPicker.setMinValue(3);
        numberPicker.setMaxValue(26);
        Log.d("fragmentBuild", "initializeNumberPicker: " + numberPicker.getMaxValue() + "\n" + numberPicker.getMinValue() + "\n");
        fragmentBulidGameBinding.NPChooseNumberOfChallengers.setOnValueChangedListener(this::onValueChange);
    }

    private void initializeRecyclerView() {
        for (int i = 0; i < 3; i++) {
            Challenger challenger = new Challenger();
            challengers.add(challenger);
        }
        challengerNameAdapter = new ChallengerNameAdapter(challengers);
        fragmentBulidGameBinding.RVChallengerNames.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentBulidGameBinding.RVChallengerNames.setAdapter(challengerNameAdapter);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1)
    {

        Log.i("valueChange", "onValueChange: " + i1);
        if (i1 > i) {
            Log.i("onValueChange", "i1 > inewValue: "+i1);
            for (int j = i; j < i1; j++)
            {
                Challenger challenger = new Challenger();
                challengers.add(challenger);
                Log.i("onValueChange", "adding: "+challengers.size());
                challengerNameAdapter.notifyItemInserted(challengers.size()-1);
            }
        } else {
            Log.i("onValueChange", "i1 < inewValue: "+i1);
            for (int j = i; j > i1; j--) {
                Log.i("onValueChange", "removing: "+challengers.get(challengers.size() - 1));
                challengers.remove(challengers.size() - 1);
                challengerNameAdapter.notifyItemRemoved(challengers.size()-1);
                Log.i("onValueChange", "i1 < inewValue: "+i1);

            }
        }
       challengers.forEach(new Consumer<Challenger>() {
           @Override
           public void accept(Challenger challenger) {
               Log.i("challengers", "accept: "+challenger.toString());
           }
       });
   }


}