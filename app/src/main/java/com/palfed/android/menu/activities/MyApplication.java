package com.palfed.android.menu.activities;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Administrator on 12/1/2016.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
    public static boolean isRefreshList = true;
    MainActivity mainActivity;

}