package com.afshin.truthordare.MVVM.UI.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.afshin.truthordare.Adapters.Class.ChallengerNameAdapter;
import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.CustomViews.TruthDareView;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.databinding.FragmentBulidGameBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BuildGameFragment extends Fragment implements  AdapterView.OnItemSelectedListener {

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void chooseNumberOfChallengersTVClick()
    {
        ArrayList<Challenger> challengersList = new ArrayList<>();

        Log.i("challengerNames", "chooseNumberOfChallengersTVClick: challengerNames:"+challengerNames.size()+"challengerNameGetItemCount:"+challengerNameAdapter.getItemCount() );
        if (challengers.size() < challengerNameAdapter.getItemCount())
        {
            Toast.makeText(getActivity(), "نام همه بازیکنان را وارد کنید", Toast.LENGTH_LONG).show();
        }
        else
            {
                Log.i("Challengers", "chooseNumberOfChallengersTVClick: "+challengers.size());

            TruthDareView truthDareView = new TruthDareView(getActivity());
            for (int i=0;i<challengers.size();i++)
            {
                Log.i("challengerchallengersssNames", "chooseNumberOfChallengersTVClick: " + i + " " + challengerNames.get(i));
                Challenger challenger = new Challenger(challengers.get(i).getName(),null, truthDareView.generateRandomColor());
                challengersList.add(challenger);
            }


            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("challengers", challengersList);
            NavigateUtil.Navigate(getActivity(), R.id.action_bulidGameFragment_to_gameMainFragment, bundle,R.id.nav_host_fragment);
        }
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


        fragmentBulidGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulid_game, container, false);
        return fragmentBulidGameBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        initializeSpinner();
        fragmentBulidGameBinding.BTNStartGame.setOnClickListener(view1 -> {
                chooseNumberOfChallengersTVClick();
        });


    }

    private void initializeSpinner()
    {
        String[] items = new String[]{"3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.drop_down_item);
        fragmentBulidGameBinding.SPNumberOfChallengers.setAdapter(adapter);
        fragmentBulidGameBinding.SPNumberOfChallengers.setOnItemSelectedListener(this);
    }

    private void initializeRecyclerView() {
        challengerNameAdapter = new ChallengerNameAdapter(challengers);
        fragmentBulidGameBinding.RVChallengerNames.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        fragmentBulidGameBinding.RVChallengerNames.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        fragmentBulidGameBinding.RVChallengerNames.setAdapter(challengerNameAdapter);
    }



    //Spinner
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        ((TextView) fragmentBulidGameBinding.SPChooseNumberOfChallengers.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
//        ((TextView) fragmentBulidGameBinding.SPChooseNumberOfChallengers.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
//        ((TextView) fragmentBulidGameBinding.SPChooseNumberOfChallengers.getSelectedView()).setOutlineSpotShadowColor(getResources().getColor(R.color.white));


        fragmentBulidGameBinding.SPNumberOfChallengers.setSelection(i);
        int value = Integer.parseInt(fragmentBulidGameBinding.SPNumberOfChallengers.getItemAtPosition(i).toString());
        ArrayList<Challenger> challengerArrayList = new ArrayList<>();
        for (int index = 0 ;index<value;index++)
        {
            Challenger challenger = new Challenger();
            challengerArrayList.add(challenger);
        }
        challengers.clear();
        challengers.addAll(challengerArrayList);


        challengerNameAdapter.notifyDataSetChanged();
        Log.i("itemCount", "onItemSelected: "+challengerNameAdapter.getItemCount()+"i:"+value);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}