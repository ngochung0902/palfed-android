package com.palfed.android.menu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.objects.LVNav;
import com.palfed.android.menu.activities.objects.NavMenuObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Android QTS on 12/17/2015.
 */
public class SplashScreen extends Activity{

    private ImageView ivLogo;
    protected boolean _active = true;
    protected int _splashTime = 1000;
    JSONObject json = null;
    public static ArrayList<String> arr = new ArrayList<String>();
    public static ArrayList<NavMenuObject> arrList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWidthHeight();
        ivLogo = (ImageView)findViewById(R.id.ivLogo);
        QTSRun.setLayoutView(ivLogo, QTSRun.GetWidthDevice(getApplicationContext()) * 2 / 3, QTSRun.GetWidthDevice(getApplicationContext()) * 2 / 3 * 106 / 190);
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(_splashTime);
                        QTSRun.setIsService(getApplicationContext(), false);
                        if (QTSRun.getIsRegister(getApplicationContext())){
                            Intent intent = new Intent(SplashScreen.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Intent intent = new Intent(SplashScreen.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                } catch (InterruptedException ex) {
                }
            }
        };
        splashTread.start();
    }
    private void getWidthHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int wwidth = displaymetrics.widthPixels;
        QTSRun.SetWidthDevice(this, wwidth);
        QTSRun.SetHeightDevice(this, height);
    }

}
