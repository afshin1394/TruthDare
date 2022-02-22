package com.afshin.truthordare.Adapters.Class;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.ChallengerItemviewBinding;

import java.util.List;

public class ChallengerNameAdapter extends RecyclerView.Adapter<ChallengerNameAdapter.ViewHolderChallengers>  {


    List<Challenger> challengers;



    public ChallengerNameAdapter(List<Challenger> challengers)
    {
        this.challengers = challengers;
    }

    @NonNull
    @Override
    public ViewHolderChallengers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ChallengerItemviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.challenger_itemview, parent, false);
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
        private  ChallengerItemviewBinding binding;

        public ViewHolderChallengers(ChallengerItemviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void bind(int position)
        {
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
