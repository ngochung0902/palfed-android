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
                            //getNavMenu();
                            finish();
                        }else{
                            Intent intent = new Intent(SplashScreen.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            //getNavMenu();
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

    private void getNavMenu(){
        Ion.with(SplashScreen.this)
                .load(QTSConst.URL_GET_MENU+"?token_hash="+QTSRun.getTokenhash(getApplicationContext())+"&get-nav=1")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        Log.e("errorsplash",QTSConst.URL_GET_MENU+"?token_hash="+QTSRun.getTokenhash(getApplicationContext())+"&get-nav=1");
                        if (e == null){
                            try {
                                json = new JSONObject(result.toString());
                                if (json != null) {
                                    Log.e("Result", json.toString());
                                    if (json.getString("status").equalsIgnoreCase("Success")){
                                        JSONArray jsonAddress = json.getJSONArray("nav");
                                        Log.e("Nav Json array",jsonAddress.toString());
                                        QTSConst.arrList = new ArrayList<NavMenuObject>();
                                        NavMenuObject pr_Object = new NavMenuObject();
                                        QTSRun.SetLogin_token(getApplicationContext(), json.getString("login_token"));
                                        QTSRun.setTokenhash(getApplicationContext(), MainActivity.md5(QTSRun.getSecret(getApplicationContext()) + json.getString("token")));
                                        Log.e("tokenhash login", MainActivity.md5(QTSRun.getSecret(getApplicationContext()) + json.getString("token")));
                                        for (int i=0;i<=jsonAddress.length();i++)
                                        {
                                            JSONObject navmenu = jsonAddress.getJSONObject(i);
                                            pr_Object.setAction(navmenu.getString("action"));
                                            pr_Object.setTitle(navmenu.getString("title"));
                                            pr_Object.setId(i);
                                            Log.e("Title",navmenu.getString("title").toString());
                                            Log.e("Action",navmenu.getString("action").toString());
                                            Log.e("Id",i+"");
                                            QTSConst.arrList.add(new NavMenuObject(i,navmenu.getString("action"),navmenu.getString("title")));
                                            QTSConst.arr.add(new LVNav(navmenu.getString("title")));
                                        }

                                    }
                                    else {
                                        Log.e("error","No Success");
                                    }
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

}
