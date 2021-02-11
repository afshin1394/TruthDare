package com.afshin.truthordare;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class NavigateUtil
{

    private static NavigateUtil instance = null;
    public static NavigateUtil getInstance(){
        if (instance==null)
            instance=new NavigateUtil();

        return instance;
    }
    public NavigateUtil() {
    }

    public  void navigate(FragmentActivity activity, int actionId, Bundle bundle,int hostId)
    {
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        supportFragmentManager.executePendingTransactions();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(hostId);
        NavController navController = navHostFragment.getNavController();

        navController.navigate(actionId, bundle, NavigationOption.getNavOptions());
    }
}

