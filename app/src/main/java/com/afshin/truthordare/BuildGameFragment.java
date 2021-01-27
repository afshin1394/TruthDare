package com.afshin.truthordare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BuildGameFragment extends Fragment implements  NumberPicker.OnValueChangeListener , ChallengerNameAdapterEvents {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Map<Integer,String> challengerNames=new HashMap<>();
    List<String> challengers=new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ChallengerNameAdapter challengerNameAdapter;
    private ArrayList<EditText> editTextList;

    @BindView(R.id.RV_ChallengerNames)
    RecyclerView RV_ChallengerNames;

    @BindView(R.id.TV_chooseNumberOfChallengers)
    TextView TV_chooseNumberOfChallengers;

    @BindView(R.id.NP_chooseNumberOfChallengers)
    NumberPicker NP_chooseNumberOfChallengers;



    @OnClick(R.id.TV_chooseNumberOfChallengers)
    public void chooseNumberOfChallengersTVClick(){
        ArrayList<Challenger> challengers=new ArrayList<>();

            if (challengerNames.size() < challengerNameAdapter.getItemCount()){
                Toast.makeText(getActivity(),"نام همه بازیکنان را وارد کنید",Toast.LENGTH_LONG).show();
            }else{
                TruthDareView truthDareView=new TruthDareView(getActivity());
                for (Integer key:challengerNames.keySet()){
                    Log.i("challengerNames", "chooseNumberOfChallengersTVClick: "+key+" "+challengerNames.get(key));
                    Challenger challenger=new Challenger(challengerNames.get(key),truthDareView.generateRandomColor());
                    challengers.add(challenger);
                }


                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("challengers", challengers);
                NavigateUtil navigateUtil=NavigateUtil.getInstance();
                navigateUtil.navigate(getActivity(),R.id.action_bulidGameFragment_to_gameMainFragment,bundle);

            }
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
        clearEditTexts(editTextList);

        Log.i("valueChange", "onValueChange: "+i1);
        challengerNameAdapter.alterChallengerSize(i1);
        challengerNameAdapter.notifyDataSetChanged();
    }



    @Override
    public void onFocusChange(EditText editText, int position) {
        editTextList = new ArrayList<>();
        editTextList.add(editText);
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
    private void clearEditTexts(ArrayList<EditText> editTextList) {
        for (EditText editText : editTextList) {
            editText.getText().clear();
        }
    }
}