package com.afshin.truthordare.MVVM.UI.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afshin.truthordare.Adapters.Class.BottlesAdapter;
import com.afshin.truthordare.Adapters.Class.ChallengerNameAdapter;
import com.afshin.truthordare.Adapters.Interface.BottleEvents;
import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Challenger;

import com.afshin.truthordare.CustomViews.BottleView;
import com.afshin.truthordare.CustomViews.ExternalCircleView;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.UI.Activities.MainActivity;
import com.afshin.truthordare.MVVM.ViewModel.GameChoiceViewModel;
import com.afshin.truthordare.MVVM.ViewModel.GameMainViewModel;
import com.afshin.truthordare.MVVM.ViewModel.IntroductionViewModel;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.Utils.Enums.ToastType;
import com.afshin.truthordare.CustomViews.TruthDareView;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.protobuf.ExtensionRegistryLite;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class GameMainFragment extends Fragment implements UIEvents {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;
    private TruthDareView truthDareView;
    private ExternalCircleView externalCircleView;
    private BottleView bottleView;
    private BottleView.IBottle iBottle;
    private int backCounter = 0;
    private GameMainViewModel gameMainViewModel;
    private BottlesAdapter bottlesAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private RecyclerView recyclerView;
    private List<BottleModel> bottles;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        ((MainActivity) getActivity()).setOnBackPressedListener(this);

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gameMainViewModel = new ViewModelProvider(getActivity()).get(GameMainViewModel.class);

        // Inflate the layout for this fragment
        List<Challenger> challengers = getArguments().getParcelableArrayList("challengers");


        for (Challenger challenger : challengers) {
            Log.i("challengerss", "onCreateView: " + challenger.toString());
        }
        view = inflater.inflate(R.layout.fragment_game_main, container, false);
        bottles = new ArrayList<>();
        findViews(view);
        gameMainViewModel.getBottles();
        gameMainViewModel.getLocalBottlesLiveData().observe(getViewLifecycleOwner(), new Observer<List<BottleModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<BottleModel> bottleModels) {
               bottles.clear();
               bottles.addAll(bottleModels);
               bottlesAdapter.notifyDataSetChanged();
               bottleView.changeBottleBitmap(bottleModels.get(0).getImage());
//               truthDareView.changeBottleBitmap(bottleModels.get(0).getImage());
            }
        });

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

        bottleView.setIBottle(new BottleView.IBottle() {
            @Override
            public void identifyWhoAreGoingToPlay(double bottleAngle) {
                truthDareView.identifyWhoAreGoingToPlay(bottleAngle);
            }

            @Override
            public void onBottleActionDown(boolean down) {
                Log.i("actionDown", "step1"+down);
                truthDareView.onBottleActionDown(down);
                externalCircleView.onBottleActionDown(down);
            }
        });

        return view;

    }

    private void initializeBottlesAdapter(List<BottleModel> bottleModels)
    {
        recyclerView = view.findViewById(R.id.recycler_view);
        bottlesAdapter = new BottlesAdapter(context, bottleModels, bottleModel -> {
            bottleView.changeBottleBitmap(bottleModel.getImage());
//            truthDareView.changeBottleBitmap(bottleModel.getImage());
            bottleView.changeBottleBitmap(bottleModel.getImage());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(bottlesAdapter);
    }

    private void findViews(View view) {
        truthDareView = view.findViewById(R.id.CV_truthOrDare);
        bottleView = view.findViewById(R.id.CV_bottle);
        externalCircleView = view.findViewById(R.id.CV_externalCircleView);
        LinearLayout lnrlayBottomsheet = view.findViewById(R.id.linearSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        FloatingActionButton chooseBottle = view.findViewById(R.id.fabChooseBottle);
        FloatingActionButton editChallengers = view.findViewById(R.id.fabEditPlayer);
        FloatingActionMenu menu = view.findViewById(R.id.fabMenu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        editChallengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                NavigateUtil.Navigate(getActivity(), R.id.action_gameMainFragment_to_bulidGameFragment, bundle, R.id.nav_host_fragment);
            }
        });
        chooseBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.close(true);
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                     bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                else
                     bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });

        initializeBottlesAdapter(bottles);


    }

    @Override
    public void onBackPressed() {
        backCounter ++;
        if (backCounter == 1)
        {
            Toast.showToast(context,ToastType.INFO,ToastDuration.SHORT,getString(R.string.exitGame));
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