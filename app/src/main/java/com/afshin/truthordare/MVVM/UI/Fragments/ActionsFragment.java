package com.afshin.truthordare.MVVM.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afshin.truthordare.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTION_LIST_TYPE = "ACTION_LIST_TYPE";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static int LIST_TYPE;

    public ActionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionsFragment newInstance(String param1) {
        ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_LIST_TYPE, LIST_TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            LIST_TYPE = getArguments().getInt(ACTION_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action, container, false);
    }
}