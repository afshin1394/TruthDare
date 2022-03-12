package com.afshin.truthordare.Adapters.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.afshin.truthordare.Adapters.Interface.BottleEvents;
import com.afshin.truthordare.Models.BottleModel;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.BottlesItemViewBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class BottlesAdapter extends RecyclerView.Adapter<BottlesAdapter.ViewHolderBottles>{


    private List<BottleModel> bottles;
    private Context context;
    private BottleEvents bottleEvents;


    public BottlesAdapter(Context context,List<BottleModel> bottles,BottleEvents bottleEvents) {
        this.bottles = bottles;
        this.context = context;
        this.bottleEvents = bottleEvents;
    }

    @NonNull
    @Override
    public ViewHolderBottles onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        BottlesItemViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.bottles_item_view, parent, false);
        return new BottlesAdapter.ViewHolderBottles(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBottles holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return bottles.size();
    }

    public class ViewHolderBottles extends RecyclerView.ViewHolder{
        private  BottlesItemViewBinding binding;
        public ViewHolderBottles(@NonNull BottlesItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.IMGChoice.setOnClickListener(view -> bottleEvents.onBottleClick(bottles.get(getAdapterPosition())));
        }


        public void bind(int position) {

            Glide.with(context)
                    .load(bottles.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.IMGChoice);
        }


    }
}
