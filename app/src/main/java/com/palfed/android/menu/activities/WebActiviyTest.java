package com.palfed.android.menu.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.palfed.android.menu.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Android QTS on 4/8/2016.
 */
public class WebActiviyTest extends Activity {

    private WebView wv1;
    private static final int SELECT_PICTURE = 1;
    Bitmap bitmap;
    String result = "";
    String imgPath, fileName, encodedString, uploadUri, browseCallbackFn, uploadCallbackFn, uploadFailCallbackFn;
    RequestParams params = new RequestParams();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        wv1=(WebView)findViewById(R.id.wen_browser);
        wv1.setWebViewClient(new WebViewClient());
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setAllowFileAccess(true);
        wv1.addJavascriptInterface(new WebViewJavaScriptInterface(this), "Android");

        wv1.getSettings().setAppCacheEnabled(true);

        wv1.loadUrl("https://design.palfed.com/upload-test");
    } //oncreate Ends

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
                Toast.makeText(getApplicationContext(), "Uploading : " + upload_destination, Toast.LENGTH_LONG).show();

                encodeImagetoString();

            } else {
                Toast.makeText(getApplicationContext(),
                        "You must select image from gallery before you try to upload",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data){
                Uri selectedImage = data.getData();
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
                wv1.loadUrl("javascript:"+browseCallbackFn+"()");

            }
            else {
                Toast.makeText(this,"Please pick a image first",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            for (int i=0; i < 20; i++) {
                Toast.makeText(this, "Something went wrong " + e.getMessage(), Toast.LENGTH_LONG).show();
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

            // Trigger Image upload

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
        Toast.makeText(getApplicationContext(), "Post to  : " + uploadUri, Toast.LENGTH_LONG).show();

        client.post(uploadUri,
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        wv1.loadUrl("javascript:"+uploadCallbackFn+"('"+response+"')");
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {

                        wv1.loadUrl("javascript:"+uploadFailCallbackFn+"()");
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();

                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}