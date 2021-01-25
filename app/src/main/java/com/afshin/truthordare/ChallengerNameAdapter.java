package com.afshin.truthordare;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallengerNameAdapter extends RecyclerView.Adapter<ChallengerNameAdapter.ViewHolderChallengers> {

    int numberOfEditText;
    ChallengerNameAdapterEvents challengerNameAdapterEvents;
    Map<Integer,String> dataEntry=new HashMap<>();

    public ChallengerNameAdapter(int editTextList,ChallengerNameAdapterEvents challengerNameAdapterEvents) {
        this.numberOfEditText = editTextList;
        this.challengerNameAdapterEvents=challengerNameAdapterEvents;
    }

    @NonNull
    @Override
    public ViewHolderChallengers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenger_itemview, parent, false);
        return new ViewHolderChallengers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChallengers holder, int position) {
        Log.i("positionnn", "onBindViewHolder: "+position);
       holder.bind(holder,position);
    }

    @Override
    public int getItemCount() {
        return numberOfEditText;
    }

    public void alterChallengerSize(int numberOfEditText) {
        this.numberOfEditText = numberOfEditText;
    }

    class ViewHolderChallengers extends RecyclerView.ViewHolder {
        public ViewHolderChallengers(@NonNull View itemView) {
            super(itemView);
        }

        public void bind( ViewHolderChallengers holder, int position) {
             EditText editText=holder.itemView.findViewById(R.id.ETV_ChallengerName);
             editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                 @Override
                 public void onFocusChange(View view, boolean hasFocus)
                 {
                     if (hasFocus)
                     {
                         challengerNameAdapterEvents.onFocusChange(editText,position);
                     }
                 }
             });
        }
    }




}
