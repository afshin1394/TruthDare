package com.afshin.truthordare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.afshin.truthordare.databinding.ChallengerItemviewBinding;

import java.util.List;

public class ChallengerNameAdapter extends RecyclerView.Adapter<ChallengerNameAdapter.ViewHolderChallengers> implements  ChallengerNameAdapterEvents{

    int numberOfEditText;
    List<Challenger> challengers;
    ChallengerNameAdapterEvents challengerNameAdapterEvents;


    public ChallengerNameAdapter(List<Challenger> challengers) {

        this.challengers = challengers;
    }

    @NonNull
    @Override
    public ViewHolderChallengers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ChallengerItemviewBinding itemBinding =
                ChallengerItemviewBinding.inflate(layoutInflater, parent, false);
        return new ViewHolderChallengers(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChallengers holder, int position) {
        Challenger challenger = challengers.get(position);
        holder.bind(challenger);
    }

    @Override
    public int getItemCount() {
        return challengers != null ? challengers.size() : 0;
    }

    public void setChallengerSize(int numberOfEditText) {
        this.numberOfEditText = numberOfEditText;
    }

    @Override
    public void onFocusChange(Challenger challenger) {

    }

    class ViewHolderChallengers extends RecyclerView.ViewHolder {
        private final ChallengerItemviewBinding binding;

        public ViewHolderChallengers(ChallengerItemviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Challenger challenger) {
            binding.setChallenger(challenger);
            binding.executePendingBindings();

        }
    }


}
