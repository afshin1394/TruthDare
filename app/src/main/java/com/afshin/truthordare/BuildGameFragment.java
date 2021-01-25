package com.afshin.truthordare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuildGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildGameFragment extends Fragment implements  NumberPicker.OnValueChangeListener , ChallengerNameAdapterEvents {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HashMap<Integer,String> challengerNames=new HashMap<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ChallengerNameAdapter challengerNameAdapter;


    @BindView(R.id.RV_ChallengerNames)
    RecyclerView RV_ChallengerNames;

    @BindView(R.id.TV_chooseNumberOfChallengers)
    TextView TV_chooseNumberOfChallengers;

    @BindView(R.id.NP_chooseNumberOfChallengers)
    NumberPicker NP_chooseNumberOfChallengers;



    @OnClick(R.id.TV_chooseNumberOfChallengers)
    public void chooseNumberOfChallengersTVClick(){
            for (Integer key:challengerNames.keySet()){
                Log.i("challengerNames", "chooseNumberOfChallengersTVClick: "+key+" "+challengerNames.get(key));
            }
    }

    public BuildGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BulidGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuildGameFragment newInstance(String param1, String param2) {
        BuildGameFragment fragment = new BuildGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bulid_game, container, false);
        ButterKnife.bind(this, view);
        NP_chooseNumberOfChallengers.setMaxValue(24);
        NP_chooseNumberOfChallengers.setMinValue(3);
        NP_chooseNumberOfChallengers.setOnValueChangedListener(BuildGameFragment.this);
        initializeRecyclerView();
        return view;
    }

    private void initializeRecyclerView() {
        challengerNameAdapter = new ChallengerNameAdapter(3, this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RV_ChallengerNames.setLayoutManager(linearLayoutManager);
        RV_ChallengerNames.setAdapter(challengerNameAdapter);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        challengerNames.clear();
        challengerNameAdapter.alterChallengerSize(i1);
        challengerNameAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(EditText editText, int position) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("afterTextChanged", "afterTextChanged: "+editable.toString()+" "+position);
                if (challengerNames.containsKey(position)){
                    challengerNames.replace(position,editable.toString());
                }else{
                    challengerNames.put(position,editable.toString());
                }

            }
        });
    }
}