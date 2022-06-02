package com.afshin.truthordare.MVVM.UI.Fragments;

import static android.app.Activity.RESULT_OK;

import static com.afshin.truthordare.Utils.Constants.CAMERA_REQUEST;
import static com.afshin.truthordare.Utils.Constants.GALLERY_PICTURE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.afshin.truthordare.Adapters.Class.ChallengerNameAdapter;
import com.afshin.truthordare.BaseApplication;
import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.CustomViews.IImageDialog;
import com.afshin.truthordare.CustomViews.Toast;
import com.afshin.truthordare.Interfaces.ChallengerNameEvents;
import com.afshin.truthordare.Interfaces.UIEvents;
import com.afshin.truthordare.MVVM.UI.Activities.MainActivity;
import com.afshin.truthordare.MVVM.ViewModel.BuildGameViewModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.Utils.BaseInfo;
import com.afshin.truthordare.Utils.Constants;
import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.Utils.Enums.ToastType;
import com.afshin.truthordare.Utils.NavigateUtil;
import com.afshin.truthordare.Utils.SensorUtils;
import com.afshin.truthordare.databinding.FragmentBulidGameBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */

@AndroidEntryPoint
public class BuildGameFragment extends Fragment implements AdapterView.OnItemSelectedListener, UIEvents {

    public  final String TAG = BuildGameFragment.this.getClass().getSimpleName();
    List<Challenger> challengers;

    private ChallengerNameAdapter challengerNameAdapter;
    private Context context;

    FragmentBulidGameBinding fragmentBulidGameBinding;
    private int backCounter = 0;
    private BuildGameViewModel buildGameViewModel;
    private int selectedValue;
    private boolean initSpinner;
    private boolean hasDeleteChoice;
    private int position;
    private Uri imageUri;
    private boolean editPlayer;

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
        Log.i(TAG, "onCreate: create");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        buildGameViewModel = new ViewModelProvider(getActivity()).get(BuildGameViewModel.class);
        editPlayer = getArguments().getBoolean(Constants.EDIT_PLAYERS);
        Log.i(TAG, "onCreateView: editPlayer "+editPlayer);
        Log.i(TAG, "onCreateView: onCreateView");
        fragmentBulidGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bulid_game, container, false);
        initializeSpinner();
        fragmentBulidGameBinding.BTNStartGame.setOnClickListener(view1 -> {
            int viewItemCount = challengerNameAdapter.getItemCount();
            Log.i(TAG, "onViewCreated: startGameCliked");
            editPlayer = false;
            buildGameViewModel.startGame(context,challengers,viewItemCount);
        });

        buildGameViewModel.getAllChallengers();

        buildGameViewModel.getBaseInfo().observe(getViewLifecycleOwner(), new Observer<BaseInfo>() {
            @Override
            public void onChanged(BaseInfo baseInfo) {
                Log.i(TAG, " baseInfo");

                Toast.showToast(context,baseInfo.getToastType(),baseInfo.getDuration(),baseInfo.getMessage());
            }
        });
        buildGameViewModel.getDeleteChoiceLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean delete) {
                Log.i(TAG, " delete");
                hasDeleteChoice = delete;
                initializeRecyclerView(challengers,delete);
            }
        });

        buildGameViewModel.getAllChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<List<Challenger>>() {
            @Override
            public void onChanged(List<Challenger> challengers) {
                BuildGameFragment.this.challengers = challengers;

                Log.i(TAG, " getAll");

                initializeRecyclerView(challengers,hasDeleteChoice);

                if (initSpinner)
                {
                    fragmentBulidGameBinding.SPNumberOfChallengers.setSelection(challengers.size() - 3);
                }

            }
        });
        buildGameViewModel.refreshChallengersLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean refreshed) {

                Log.i(TAG,"refreshed:");

                if (refreshed) {
                    navigateToGameMain();
                }
            }
        });
        return fragmentBulidGameBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void navigateToGameMain() {
        Log.i(TAG,"onChanged:" + challengers);
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
        Log.i(TAG, "initializeSpinner: init spinner:"+initSpinner +"challengers size:"+challengers.size() );
    }

    private void initializeRecyclerView(List<Challenger> challengers,boolean hasDeleteChoice) {
        Log.i(TAG, "size:"+challengers.size());
        challengerNameAdapter = new ChallengerNameAdapter(context,challengers,hasDeleteChoice, new ChallengerNameEvents() {
            @Override
            public void onDelete(int position) {
                buildGameViewModel.deleteChallenger(selectedValue,position);
            }

            @Override
            public void onProfilePic(int position) {
                startDialog(position);
//                buildGameViewModel.checkProfilePic(position);
            }
        });
        fragmentBulidGameBinding.RVChallengerNames.setItemAnimator(new DefaultItemAnimator());
        fragmentBulidGameBinding.RVChallengerNames.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        fragmentBulidGameBinding.RVChallengerNames.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        fragmentBulidGameBinding.RVChallengerNames.setAdapter(challengerNameAdapter);
    }

    private void startDialog(int position) {
        this.position = position;

        new com.afshin.truthordare.CustomViews.AlertDialog().ImageDialog(context, new IImageDialog() {
            @Override
            public void onClickGallery() {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(
                        pictureActionIntent,
                        GALLERY_PICTURE);
            }

            @Override
            public void onClickCamera() {
                openCamera(CAMERA_REQUEST);

            }
        });

    }

    public void openCamera(int requestCode) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
         imageUri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }
    //Spinner
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "onItemSelected: "+initSpinner);
        if (initSpinner)
        {
            initSpinner = false;
            //new player run into
            if (challengers.size() == 0)
            buildGameViewModel.checkItemSelection(3, challengers);
        }else {
            Log.i(TAG, "int value : " + i + "long Value" + l);
            int selectedValue = Integer.parseInt(fragmentBulidGameBinding.SPNumberOfChallengers.getItemAtPosition(i).toString());
            Log.i(TAG, "selected Value: " + selectedValue);
            Log.i(TAG, "challengers.size: " + challengers.size() + "selected Value" + selectedValue);
            Log.i(TAG, "selected value: " + selectedValue);
            this.selectedValue = selectedValue;
            buildGameViewModel.checkItemSelection(selectedValue, challengers);
        }
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

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView: dispose");
        super.onDestroyView();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                if(resultCode == RESULT_OK)
                {
                    switch (requestCode){
                        case CAMERA_REQUEST:
                            buildGameViewModel.setChallengerImage(imageUri,position);
                            challengerNameAdapter.notifyItemChanged(position);
                            break;

                        case GALLERY_PICTURE:
                            Uri selectedImage = data.getData();
                            buildGameViewModel.setChallengerImage(selectedImage,position);
                            challengerNameAdapter.notifyItemChanged(position);
                            break;
                    }


                }



    }


}