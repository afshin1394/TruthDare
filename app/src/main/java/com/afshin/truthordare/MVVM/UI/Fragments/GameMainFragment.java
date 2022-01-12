package com.afshin.truthordare.MVVM.UI.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.Utils.Enums.ToastType;
import com.afshin.truthordare.CustomViews.TruthDareView;
import com.afshin.truthordare.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GameMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
        List<Challenger> challengers = getArguments().getParcelableArrayList("challengers");

        for (Challenger challenger : challengers) {
            Log.i("challengerss", "onCreateView: " + challenger.toString());
        }
        view = inflater.inflate(R.layout.fragment_game_main, container, false);
        TruthDareView truthDareView = view.findViewById(R.id.CV_truthOrDare);
        truthDareView.setChallengers(challengers);
        truthDareView.setITruthDare(new TruthDareView.ITruthDare() {
            @Override
            public void onResult(Challenger requester, Challenger responder) {
                if (requester==null || responder==null)
                Toast.showToast(context, ToastType.INFO, ToastDuration.SHORT,getString(R.string.swipeAgain));
                else {


                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("responder", responder.getName());
                    args.putString("requester", requester.getName());
                    GameChoiceFragment gameChoiceFragment = new GameChoiceFragment();
                    gameChoiceFragment.setArguments(args);
                    new Handler().postDelayed(() -> gameChoiceFragment.show(ft,"dialog"),500);

                }
            }
        });

        return view;

    }

}