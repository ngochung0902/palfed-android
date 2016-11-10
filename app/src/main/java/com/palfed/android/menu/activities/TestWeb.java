package com.palfed.android.menu.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.palfed.android.menu.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Android QTS on 4/6/2016.
 */
public class TestWeb extends Activity {

    WebView website;

//    Bitmap bitmap;

//    String result = "";

//    private ValueCallback<Uri> mUploadMessage;
//    private final static int FILECHOOSER_RESULTCODE=1;
//    private static final int SELECT_PICTURE = 1;
//    String imgPath, fileName, encodedString;
//    RequestParams params = new RequestParams();
private static final String TYPE_IMAGE = "image/*";
    private static final int INPUT_FILE_REQUEST_CODE = 1;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri[]> mUploadMessage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        website = (WebView) findViewById(R.id.wen_browser);
        website.setWebViewClient(new WebViewClient());

        website.getSettings().setJavaScriptEnabled(true);
        website.getSettings().setAllowFileAccess(true);
//        website.addJavascriptInterface(new webappinterface(this), "android");
//        website.getSettings().setAppCacheEnabled(true);
        website.loadUrl("http://www.script-tutorials.com/demos/199/index.html");
        website.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onCloseWindow(WebView w) {
                super.onCloseWindow(w);
                finish();
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                WebView childView = new WebView(TestWeb.this);
                final WebSettings settings = childView.getSettings();
                settings.setDomStorageEnabled(true);
                settings.setJavaScriptEnabled(true);
                settings.setAllowFileAccess(true);
                settings.setAllowContentAccess(true);
                childView.setWebChromeClient(this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(childView);
                resultMsg.sendToTarget();
                return false;
            }

            // For Android Version < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                //System.out.println("WebViewActivity OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU), n=1");
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(TYPE_IMAGE);
                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
            }

            // For 3.0 <= Android Version < 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                //System.out.println("WebViewActivity 3<A<4.1, OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU,aT), n=2");
                openFileChooser(uploadMsg, acceptType, "");
            }

            // For 4.1 <= Android Version < 5.0
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                //System.out.println("WebViewActivity 4.1<A<5, OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU,aT,c), n=3");
                mUploadMessage = uploadFile;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(TYPE_IMAGE);
                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
            }
            // For Android Version 5.0+
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                System.out.println("WebViewActivity A>5, OS Version : " + Build.VERSION.SDK_INT + "\t onSFC(WV,VCUB,FCP), n=3");
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType(TYPE_IMAGE);
                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
                return true;
            }
            // Work on Android 4.4.2 Zenfone 5
            public void showFileChooser(ValueCallback<Uri[]> filePathCallback,String acceptType, boolean paramBoolean){
                if (mUploadMessage2 != null) {
                    mUploadMessage2.onReceiveValue(null);
                    mUploadMessage2 = null;
                }

                mUploadMessage2 = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), INPUT_FILE_REQUEST_CODE);
            }
//            public void showFileChooser(ValueCallback<String[]> uploadFileCallback,
//                                        FileChooserParams fileChooserParams) {
//                if (mUploadMessage2 != null) {
//                    mUploadMessage2.onReceiveValue(null);
//                }
//                mUploadMessage2 = uploadFileCallback;
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType(TYPE_IMAGE);
//                startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
//            }
        });

    } //oncreate Ends

    //flipscreen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INPUT_FILE_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mFilePathCallback == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
                Uri[] results = null;
                if (resultCode == RESULT_OK) {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
                if (mUploadMessage2 == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
//                String[] results = null;
//                if (resultCode == RESULT_OK) {
//                    Log.e("uri data",""+data.getData());
//                    Log.e("data",data.getDataString());
//                    String dataString = data.getDataString();
//                    if (dataString != null) {
//                        results = new String[]{dataString};
//                    }
//                }
                Uri[] results = null;
                if (resultCode == RESULT_OK) {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                        Log.e("uri data",""+results[0]);
//                    Log.e("data",data.getDataString());
                    }
                }
                mUploadMessage2.onReceiveValue(results);
                mUploadMessage2 = null;
            }else {
                if (mUploadMessage == null) {
                    super.onActivityResult(requestCode, resultCode, data);
                    return;
                }
                Uri result = null;
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        result = data.getData();
                    }
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
//For javascriptInterface

//    class webappinterface{
//        Context mContext;
//        webappinterface(Context c){
//            mContext = c;
//        }
//
//        @JavascriptInterface
//        public void opengallary(){
//
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, SELECT_PICTURE);
//            // intent.setType("image/*");
//            // intent.setAction(Intent.ACTION_GET_CONTENT);
//            //   startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
//        }
//        @SuppressLint("NewApi")
//        @JavascriptInterface
//        public void uploadImage() {
//
//            //Toast.makeText(getApplicationContext(),"Path:" + , Toast.LENGTH_LONG).show();
//            if (imgPath != null && !imgPath.isEmpty()) {
//                Toast.makeText(getApplicationContext(), "Uploading : " + imgPath, Toast.LENGTH_LONG).show();
//
//                encodeImagetoString();
//
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "You must select image from gallery before you try to upload",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try { if(requestCode==FILECHOOSER_RESULTCODE)
//        {
//            if (null == mUploadMessage) return;
//            Uri result = data == null || resultCode != RESULT_OK ? null
//                    : data.getData();
//            Log.e("Uri Image", result.toString());
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        }else
//            if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                //setting file name as label
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgPath = cursor.getString(columnIndex);
//                cursor.close();
//                String fileNameSegments[] = imgPath.split("/");
//                fileName = fileNameSegments[fileNameSegments.length - 1];
//                params.put("filename", fileName);
//                //setting image file name on website
//                website.loadUrl("javascript:setFileUri('" + fileName + "')");
//
//            } else {
//                Toast.makeText(this, "Pick a image first", Toast.LENGTH_LONG).show();
//            }
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Something Wrong", Toast.LENGTH_LONG).show();
//        }
//    }
//    //AsyncTask is using for background process running
//    public void encodeImagetoString(){ new AsyncTask<Void, Void , String>(){
//        protected void onPreExecute(){
//
//        };
//
//        @Override
//        protected String doInBackground(Void... params){
//            BitmapFactory.Options options = null;
//            options = new BitmapFactory.Options();
//            options.inSampleSize = 3;
//            bitmap = BitmapFactory.decodeFile(imgPath,options);
//
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
//            byte[] byte_arr = stream.toByteArray();
//            encodedString = Base64.encodeToString(byte_arr, 0);
//            return "";
//
//        }
//        @Override
//        protected void onPostExecute(String msg) {
//
//            params.put("image", encodedString);
//            // Trigger Image upload
//
//            triggerImageUpload();
//        }
//
//    }.execute(null, null, null);
//    }
//    public void triggerImageUpload() {
//        makehttpcall();
//    }
//
//    //makehttpcall
//    public void makehttpcall() {
//        // prgDialog.setMessage("Invoking Php");
//        AsyncHttpClient client = new AsyncHttpClient();
//        // Don't forget to change the address to your address.
//        // http://192.168.1.1/webview/upload_image.php
//
//        client.post("http://www.script-tutorials.com/demos/199/index.html",
//                params, new AsyncHttpResponseHandler() {
//                    // When the response returned by REST has Http
//                    // response code '200'
//                    @Override
//                    public void onSuccess(String response) {
//
//                        Toast.makeText(getApplicationContext(), response,
//                                Toast.LENGTH_LONG).show();
//                    }
//
//                    // When the response returned by REST has Http
//                    // response code other than '200' such as '404',
//                    // '500' or '403' etc
//                    @Override
//                    public void onFailure(int statusCode, Throwable error,
//                                          String content) {
//
//                        // When Http response code is '404'
//                        if (statusCode == 404) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Requested resource not found",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        // When Http response code is '500'
//                        else if (statusCode == 500) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Something went wrong at server end",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        // When Http response code other than 404, 500
//                        else {
//                            Toast.makeText(
//                                    getApplicationContext(),
//                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
//                                            + statusCode, Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    }
//                });
//    }
}