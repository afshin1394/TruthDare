package com.afshin.truthordare.Adapters.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.afshin.truthordare.Adapters.Interface.IGameChoiceAdapter;
import com.afshin.truthordare.Models.GameChoiceModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.GameChoiceItemBinding;

import java.util.ArrayList;
import java.util.List;


public class GameChoiceAdapter extends RecyclerView.Adapter<GameChoiceAdapter.ViewHolderGameChoice> {

 private List<GameChoiceModel> gameChoiceModels;
 private Context context;
 private IGameChoiceAdapter iGameChoiceAdapter;


    public GameChoiceAdapter( Context context,List<GameChoiceModel> gameChoiceModels,IGameChoiceAdapter iGameChoiceAdapter) {
        this.gameChoiceModels = gameChoiceModels;
        this.context = context;
        this.iGameChoiceAdapter =iGameChoiceAdapter;
    }

    @NonNull
    @Override
    public ViewHolderGameChoice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GameChoiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.game_choice_item, parent, false);
        return new GameChoiceAdapter.ViewHolderGameChoice(binding);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolderGameChoice holder, int position) {
        GameChoiceModel gameChoiceModel = gameChoiceModels.get(position);
        holder.bind(gameChoiceModel);
    }

    @Override
    public int getItemCount() {

        return gameChoiceModels.size();
    }

    public class ViewHolderGameChoice extends RecyclerView.ViewHolder{

        GameChoiceItemBinding binding;

        public ViewHolderGameChoice(@NonNull GameChoiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v-> {
                iGameChoiceAdapter.onChoose(gameChoiceModels.get(getAdapterPosition()));
            });
            binding.executePendingBindings();

        }

        public void bind(GameChoiceModel gameChoiceModel) {

            if (gameChoiceModel.getId() == 1001 || gameChoiceModel.getId() == 2001) {
                binding.IMGChoice.setImageResource(gameChoiceModel.getImage());
                binding.TVChoice.setText(gameChoiceModel.getBody());
                binding.TVChoice.setTextSize(20);

            }else{
                binding.TVChoice.setText(gameChoiceModel.getBody());
                binding.TVChoice.setTextSize(16);
                binding.TVChoice.setPadding(5,5,5,5);
            }

        }

    }




}
