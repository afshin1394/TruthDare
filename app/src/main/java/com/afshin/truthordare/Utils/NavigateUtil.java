package com.afshin.truthordare.Utils;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class NavigateUtil
{



    public static void Navigate(FragmentActivity activity, int actionId, Bundle bundle,int hostId)
    {
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
//        supportFragmentManager.beginTransaction();
        supportFragmentManager.executePendingTransactions();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(hostId);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(actionId, bundle, NavigationOption.getNavOptions());
    }


}

