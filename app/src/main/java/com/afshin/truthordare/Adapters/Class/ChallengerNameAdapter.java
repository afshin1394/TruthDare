package com.afshin.truthordare.Adapters.Class;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.Interfaces.ChallengerNameEvents;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.BuildGameItemviewBinding;

import java.util.List;

public class ChallengerNameAdapter extends RecyclerView.Adapter<ChallengerNameAdapter.ViewHolderChallengers>  {


    List<Challenger> challengers;
    boolean hasDeleteChoice = false;
    private ChallengerNameEvents challengerNameEvents;



    public ChallengerNameAdapter(List<Challenger> challengers,boolean hasDeleteChoice,ChallengerNameEvents challengerNameEvents)
    {
        this.challengerNameEvents = challengerNameEvents;
        this.challengers = challengers;
        this.hasDeleteChoice = hasDeleteChoice;
    }

    @NonNull
    @Override
    public ViewHolderChallengers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        BuildGameItemviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.build_game_itemview, parent, false);
        return new ViewHolderChallengers(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChallengers holder, int position) {
        Log.i("ChallengerNameAdapter", "onBindViewHolder: "+position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return challengers.size();
    }



    class ViewHolderChallengers extends RecyclerView.ViewHolder implements TextWatcher {
        private  BuildGameItemviewBinding binding;

        public ViewHolderChallengers(BuildGameItemviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.deleteChoice.setOnClickListener(view -> {
               challengerNameEvents.onDelete(getAdapterPosition());
            });
        }

        public void bind(int position)
        {
         binding.deleteChoice.setVisibility(hasDeleteChoice?View.VISIBLE:View.GONE);
         binding.txtName.setText(challengers.get(position).getName());
         binding.txtName.addTextChangedListener(this);
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            challengers.get(getAdapterPosition()).setName(editable.toString());

        }



    }
    public void setChallengers(List<Challenger> challengers)
    {
        Log.i("setChallengers", "size: "+challengers.size());
        Log.i("setChallengers", "content: "+challengers);
        if (challengers.size() == 0)
        {
            Challenger challenger = new Challenger();
            Challenger challenger1 = new Challenger();
            Challenger challenger2 = new Challenger();
            challengers.add(challenger);
            challengers.add(challenger1);
            challengers.add(challenger2);
        }
        this.challengers.clear();
        this.challengers.addAll(challengers);
        notifyDataSetChanged();
    }
}
