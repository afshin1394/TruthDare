package com.afshin.truthordare;

import androidx.navigation.NavOptions;

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
