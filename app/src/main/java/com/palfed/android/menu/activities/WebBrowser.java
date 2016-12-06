package com.palfed.android.menu.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.commonhelper.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.Build.*;

/**
 * Created by Android QTS on 1/6/2016.
 */
public class WebBrowser extends Activity implements View.OnClickListener {
    private ImageView ivClose, btnPre, btnNext, btnRefresh, btnX;
    private TextView lbTitle;
    private WebView webBrowser;
    private ProgressDialog progressBar;
    private String url = "";
    JSONParser jsonParser = new JSONParser();
    JSONObject json = null;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    // for android 4.4
    private static final int SELECT_PICTURE = 1;
    Bitmap bitmap;
    private boolean isLoading = false;
    String imgPath, fileName, encodedString, uploadUri, browseCallbackFn, uploadCallbackFn, uploadFailCallbackFn;
    RequestParams params = new RequestParams();
    private Intent intent;
    int timeshow = 0;
    Timer T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        lbTitle = (TextView) findViewById(R.id.lbTitle);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        btnPre = (ImageView) findViewById(R.id.btnPre);
        btnNext = (ImageView) findViewById(R.id.btnNext);
        btnRefresh = (ImageView) findViewById(R.id.btnRefresh);
        btnX = (ImageView) findViewById(R.id.btnX);
        webBrowser = (WebView) findViewById(R.id.wen_browser);
        ivClose.setEnabled(false);
        QTSRun.setIsCheck(getApplicationContext(), true);
        final Intent intent = getIntent();
        url = intent.getStringExtra("url");
        if (url.endsWith("?")||url.contains("?")) {
            url = url + "&login=" + QTSRun.GetLogin_token(getApplicationContext())+"&login_device=Android";
        } else {
            url = url + "?login=" + QTSRun.GetLogin_token(getApplicationContext())+"&login_device=Android";

        }
        Log.e("WebBrowser","Url:" +url);
        QTSRun.setFontTV(getApplicationContext(), lbTitle, QTSConst.FONT_ARBUTUSSLAB_REGULAR);
        webBrowser.getSettings().setJavaScriptEnabled(true);
        webBrowser.getSettings().setAllowFileAccess(true);
        webBrowser.getSettings().setAllowContentAccess(true);
        webBrowser.getSettings().setSupportZoom(true);
        webBrowser.getSettings().setPluginState(WebSettings.PluginState.ON);
        webBrowser.setWebViewClient(new WebViewClient());
        webBrowser.getSettings().setJavaScriptEnabled(true);
        webBrowser.getSettings().setAllowFileAccess(true);
        webBrowser.addJavascriptInterface(new WebViewJavaScriptInterface(this), "Android");

        webBrowser.getSettings().setAppCacheEnabled(true);
//        webBrowser.loadUrl("https://design.palfed.com/upload-test");
        webBrowser.loadUrl(url);
        new GetLoginToken().execute();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QTSRun.setIsService(getApplicationContext(), false);
                Intent intent = new Intent(WebBrowser.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//		                    LakRun.showToast(getApplicationContext(), "count="+timeshow);
//                        Log.e("Show Timer", "second:" + timeshow);
                        timeshow++;
                        if (timeshow == 1440) {
                            webBrowser.reload();
                            timeshow = 0;
                        }
                    }
                });
            }
        }, 1000, 1000);
        btnX.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnPre.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnPre) {
            if (webBrowser.canGoBack()) {
                webBrowser.goBack();
            }
        } else if (v == btnNext) {
            if (webBrowser.canGoForward()) {
                webBrowser.goForward();
            }
        } else if (v == btnRefresh) {
            webBrowser.reload();
        } else if (v == btnX) {
            webBrowser.stopLoading();
            QTSRun.setIsService(getApplicationContext(),false);
        }
    }

    class GetLoginToken extends AsyncTask<String, Void, String> {
        String login_token = "";
        String token = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String status = "";
            HashMap<String, String> params = new HashMap<>();
            params.put("token_hash", QTSRun.getTokenhash(getApplicationContext()));
            try {
                json = jsonParser.makeHttpRequest(QTSConst.URL_LOGINTOKEN, "POST", params);
                if (json != null) {
                    Log.e("setLocation", json.toString());
                    status = json.getString("status");
                    if (status.equalsIgnoreCase("Success")) {
                        login_token = json.getString("login_token");
                        token = json.getString("token");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("Success")) {
                QTSRun.SetLogin_token(getApplicationContext(), login_token);
                QTSRun.setToken(getApplicationContext(), token);
                QTSRun.setTokenhash(getApplicationContext(), md5(QTSRun.getSecret(getApplicationContext()) + token));
                Log.e("new token hash", md5(QTSRun.getSecret(getApplicationContext()) + token));
                ivClose.setEnabled(true);
            }
            isLoading = true;
        }
    }

    /// create MD5 //////////////
    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (QTSRun.getIsService(getApplicationContext())){
//            Log.e("onResume", "start");
//            try {
//                Log.e("onResume", "run broadcast");
//                IntentFilter ifi = new IntentFilter();
//                ifi.addAction(QTSConst.ACTION_BROADCAST_WEB);
//                this.registerReceiver(bReceiverWeb, ifi);
//                stopService(intent);
//            }catch (Exception ex){
//                Log.e("onResume", "can't run broadcast");
//            }
//        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
////        QTSRun.setIsService(getApplicationContext(),true);
//        intent = new Intent(this, TimeService.class);
//        startService(intent);
//        Log.e("onPause", "start");
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e("stop", "close");
//        stopService(intent);
//        QTSRun.setIsService(getApplicationContext(), false);
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isLoading){
//                QTSRun.setIsService(getApplicationContext(),false);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                finish();
                return true;
            }else{
                return  false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        T.cancel();
        super.onDestroy();

    }
//    private BroadcastReceiver bReceiverWeb = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context arg0, Intent arg1) {
////            QTSRun.showToast(getApplicationContext(),"reload webbrowser");
//            QTSRun.setIsService(getApplicationContext(),false);
//            webBrowser.reload();
//            Log.e("Reload web","reloaded");
//        }
//
//    };

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//    }

    //flipscreen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != intent) {
            try {
                    Uri selectedImage = intent.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA};

                    //setting file name as label
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgPath = cursor.getString(columnIndex);
                    cursor.close();
                    String fileNameSegments[] = imgPath.split("/");
                    fileName = fileNameSegments[fileNameSegments.length - 1];
                    params.put("filename", fileName);
                    //setting image file name on website
                    webBrowser.loadUrl("javascript:"+browseCallbackFn+"()");

            }catch (Exception e){
                Log.e("Error", e.getMessage());
//                for (int i=0; i < 20; i++) {
//                    Toast.makeText(this, "Something went wrong " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
            }
        }else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != WebBrowser.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
//        else
//            Toast.makeText(this.getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }
    public class WebViewJavaScriptInterface{

        private Context context;

        /*
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }

        /*
         * This method can be called from Android. @JavascriptInterface
         * required after SDK version 17.
         */
        @JavascriptInterface
        public void openGallery(final String browse_callback_fn){
            browseCallbackFn = browse_callback_fn;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_PICTURE);
        }

        @SuppressLint("NewApi")
        @JavascriptInterface
        public void uploadImage(final String upload_destination, final String upload_callback_fn, final String upload_fail_callback_fn) {

            uploadUri=upload_destination;
            uploadCallbackFn=upload_callback_fn;
            uploadFailCallbackFn=upload_fail_callback_fn;
            //Toast.makeText(getApplicationContext(),"Path:" + , Toast.LENGTH_LONG).show();
            if (imgPath != null && !imgPath.isEmpty()) {
//                Toast.makeText(getApplicationContext(), "Uploading : " + upload_destination, Toast.LENGTH_LONG).show();
                encodeImagetoString();

            } else {
//                Toast.makeText(getApplicationContext(),
//                        "You must select image from gallery before you try to upload",
//                        Toast.LENGTH_LONG).show();
            }
        }
    }

    //AsyncTask is using for background process running
    public void encodeImagetoString(){ new AsyncTask<Void, Void , String>(){
        protected void onPreExecute(){
            //Toast.makeText(getApplicationContext(), "Encoding Image", Toast.LENGTH_LONG).show();
        };

        @Override
        protected String doInBackground(Void... params){
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            bitmap = BitmapFactory.decodeFile(imgPath,options);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            encodedString = Base64.encodeToString(byte_arr, 0);
            return "";

        }
        @Override
        protected void onPostExecute(String msg) {

            params.put("image", encodedString);

            triggerImageUpload();
        }

    }.execute(null, null, null);
    }
    public void triggerImageUpload() {
        //Toast.makeText(getApplicationContext(), "Uploading Image", Toast.LENGTH_LONG).show();

        makehttpcall();
    }

    //makehttpcall
    public void makehttpcall() {
        // prgDialog.setMessage("Invoking Php");
        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the address to your address.
        // http://192.168.1.1/webview/upload_image.php
//        Toast.makeText(getApplicationContext(), "Post to  : " + uploadUri, Toast.LENGTH_LONG).show();

        client.post(uploadUri,
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        webBrowser.loadUrl("javascript:"+uploadCallbackFn+"('"+response+"')");
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        webBrowser.loadUrl("javascript:"+uploadFailCallbackFn+"()");
                        // When Http response code is '404'
                        if (statusCode == 404) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Requested resource not found",
//                                    Toast.LENGTH_LONG).show();

                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Something went wrong at server end",
//                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
//                            Toast.makeText(
//                                    getApplicationContext(),
//                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
//                                            + statusCode, Toast.LENGTH_LONG)
//                                    .show();
                        }
                    }
                });
    }

}
