package com.afshin.truthordare.Utils;

import android.app.Activity;
import android.companion.WifiDeviceFilter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SensorUtils {

    public static Intent openCamera(Context context, Intent intent) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                File photoFile = null;
                photoFile = createImageFile();
                Log.i("photoFile", "openCamera: " + photoFile);
                if (photoFile != null) {

                    Uri outputFileUri = Uri.fromFile(photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    return intent;
                }
            }
        } else {

            Log.e("SensorUtils", "openCamera: camera not available");

        }
        return null;
    }

    private static File createImageFile() {
        try {
            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/TruthDare/";
            File fileDir = new File(dir);
            if (!fileDir.exists())
                fileDir.mkdir();
            String file = dir + "1234" + ".jpg";
            File newfile = new File(file);
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                Log.i("IOException", "createImageFile: " + e.getMessage());
            }

            return newfile;
        } catch (Exception e) {
            return null;
        }


    }
}
