package com.afshin.truthordare.CustomViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.afshin.truthordare.Utils.Enums.ToastDuration;
import com.afshin.truthordare.Utils.Enums.ToastType;
import com.afshin.truthordare.R;
import com.afshin.truthordare.databinding.ToastMessageBinding;

public class Toast {


    public static void showToast(Context context, ToastType toastType , ToastDuration toastDuration, String text ){
        android.widget.Toast toast=new android.widget.Toast(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_message, null);
        ToastMessageBinding binding = DataBindingUtil.bind(layout);
        toast.setView(binding.getRoot());
        toast.setDuration(toastDuration.equals(ToastDuration.LONG)? android.widget.Toast.LENGTH_LONG:android.widget.Toast.LENGTH_SHORT);
        switch (toastType){
            case CONFIRM:
                binding.IMGMessage.setImageResource(R.drawable.ic_checked);
                break;

            case ERROR:
                binding.IMGMessage.setImageResource(R.drawable.ic_error);
                break;

            case INFO:
                binding.IMGMessage.setImageResource(R.drawable.ic_exclamation);
                break;
        }

         binding.TVMessage.setText(text);
        if (context != null)
            toast.show();
    }
}
