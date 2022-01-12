package com.afshin.truthordare.Utils;

import androidx.navigation.NavOptions;

import com.afshin.truthordare.R;

public class NavigationOption {
    public static NavOptions getNavOptions() {

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.slide_out)
                .build();

        return navOptions;
    }
}
