package com.afshin.truthordare;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.TypefaceCompatUtil;

import com.afshin.truthordare.Utils.TypeFaceUtil;

public class BaseApplication extends Application {

    private static BaseApplication instance = null;
    private static Context context;
    private static Activity currentActivity;
    public static final String TAG = BaseApplication.class.getSimpleName();
    public BaseApplication() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
//        TypeFaceUtil.overrideFont(this, "DEFAULT", getResources().getFont(R.font.b_bardiya).toString());

    }


    /**
     * get current context anywhere in app
     * @return just use BaseApplication.getContext
     */
    public static Context getContext() {
        if (currentActivity != null) {
            return currentActivity;
        }
        return context;
    }

    public static Context getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public static synchronized BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }

}
