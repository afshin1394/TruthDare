package com.afshin.truthordare.MVVM.UI.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.UI.Activities.MainActivity;
import com.afshin.truthordare.MVVM.ViewModel.GameChoiceViewModel;
import com.afshin.truthordare.MVVM.ViewModel.IntroductionViewModel;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Utils.Constants;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.Utils.Enums.ToastType;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.Utils.PermissionUtils;
import com.afshin.truthordare.databinding.FragmentIntroductionBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroductionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class IntroductionFragment extends Fragment implements UIEvents {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private FragmentIntroductionBinding fragmentIntroductionBinding;
    private IntroductionViewModel introductionViewModel;


    private int backCounter = 0;
    private Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
    }

    public IntroductionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroductionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroductionFragment newInstance(String param1, String param2) {
        IntroductionFragment fragment = new IntroductionFragment();
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

        fragmentIntroductionBinding = FragmentIntroductionBinding.inflate(getLayoutInflater(), container, false);
        introductionViewModel = new ViewModelProvider(getActivity()).get(IntroductionViewModel.class);




        return fragmentIntroductionBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onBackPressed() {
        backCounter ++;
        if (backCounter == 1)
        {
            Toast.showToast(context, ToastType.INFO, ToastDuration.SHORT,getString(R.string.exitGame));
        }else{
            getActivity().finish();
        }
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                backCounter = 0;
            }
        }, 2000);
    }




}