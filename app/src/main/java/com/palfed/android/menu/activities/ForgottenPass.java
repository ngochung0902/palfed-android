package com.palfed.android.menu.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.R;

/**
 * Created by Android QTS on 1/6/2016.
 */
public class ForgottenPass extends Activity implements View.OnClickListener{
    private ImageView  ivClose, btnPre,  btnNext, btnRefresh, btnX;
    private TextView lbTitle;
    private WebView webBrowser;
    private ProgressDialog progressBar;
    private String url_load ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        url_load = getIntent().getStringExtra("url_load");

        lbTitle = (TextView)findViewById(R.id.lbTitle);
        ivClose = (ImageView)findViewById(R.id.ivClose);
        btnPre = (ImageView)findViewById(R.id.btnPre);
        btnNext = (ImageView)findViewById(R.id.btnNext);
        btnRefresh = (ImageView)findViewById(R.id.btnRefresh);
        btnX = (ImageView)findViewById(R.id.btnX);
        webBrowser = (WebView) findViewById(R.id.wen_browser);

        QTSRun.setFontTV(getApplicationContext(), lbTitle, QTSConst.FONT_ARBUTUSSLAB_REGULAR);
        WebSettings settings = webBrowser.getSettings();
        settings.setJavaScriptEnabled(true);
        webBrowser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webBrowser.getSettings().setBuiltInZoomControls(true);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .create();

        progressBar = ProgressDialog.show(this, null,
                "Loading...");

        webBrowser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
//				Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

            }
        });
        webBrowser.loadUrl(url_load);


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnX.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnPre.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnPre){
            if (webBrowser.canGoBack()){
                webBrowser.goBack();
            }
        }else if(v == btnNext){
            if (webBrowser.canGoForward()){
                webBrowser.goForward();
            }
        }else if (v == btnRefresh){
            webBrowser.reload();
        }else if (v == btnX){
            webBrowser.stopLoading();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
