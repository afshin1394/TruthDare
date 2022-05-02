package com.afshin.truthordare.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afshin.truthordare.R;

public class AlertDialog {

    public void ImageDialog(Context context, final IImageDialog listener) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_image_dialog);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.findViewById(R.id.camera_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCamera();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.gallery_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickGallery();
                dialog.dismiss();
            }
        });


        dialog.show();


    }
}
