package com.palfed.android.menu.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.activities.gcm.GCMClientManager;
import com.palfed.android.menu.activities.objects.FrRequestObj;
import com.palfed.android.menu.activities.objects.FriendObjParent;
import com.palfed.android.menu.activities.objects.LVNav;
import com.palfed.android.menu.activities.objects.MenuObject;
import com.palfed.android.menu.activities.objects.NavMenuObject;
import com.palfed.android.menu.activities.objects.OptionObject;
import com.palfed.android.menu.activities.objects.ParentObject;
import com.palfed.android.menu.activities.objects.TagsObject;
import com.palfed.android.menu.activities.objects.UserObject;
import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.commonhelper.GPSTracker;
import com.palfed.android.menu.activities.commonhelper.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.facebook.internal.CallbackManagerImpl.RequestCodeOffset.Login;

/**
 * Created by Android QTS on 12/17/2015.
 */
public class LoginActivity extends Activity {
    private ImageView ivLogo, ivbgLogin;
    private TextView lbEmail, lbPassword, tvForgotPass;
    private Button btnLogin, btnSignUp;
    private EditText edEmail, edPassword;
    private String facebook_access_token = "";
    private int w, h;
//    JSONParser jsonParser = new JSONParser();
    JSONObject json = null;
//    private ProgressDialog pDialog;
    private ProgressDialog mProgressDialog;
    private String email = "";
    private String password = "";

    private String longitude = "";
    private String latitude = "";
    private GPSTracker gps;

    private ArrayList<ParentObject> arrList = null;
    private ArrayList<MenuObject> menuList = null;
    private ArrayList<OptionObject> optionList = null;
    private ArrayList<ParentObject> arrList1 = null;
    private ArrayList<MenuObject> menuList1 = null;
    private ArrayList<OptionObject> optionList1 = null;
    private UserObject us_Object;

    private ArrayList<FriendObjParent> arrayParents = null;

    private String android_device_id = "";
    private String localtime;

    private static final String PROJECT_NUMBER = "657281687844";
    //    String regId;
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    static final String TAG = "Register Palfed";
    //------------------
    private Button loginButton;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        ivbgLogin = (ImageView) findViewById(R.id.ivbgLogin);
        lbEmail = (TextView) findViewById(R.id.lbEmail);
        lbPassword = (TextView) findViewById(R.id.lbPassword);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        loginButton = (Button) findViewById(R.id.login_button);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        w = QTSRun.GetWidthDevice(getApplicationContext());
        h = QTSRun.GetHeightDevice(getApplicationContext());
        QTSRun.setLayoutView(ivLogo, w / 4, w / 4 * 106 / 190);
        QTSRun.setLayoutView(ivbgLogin, w * 2 / 3, w * 2 / 3);
        QTSRun.setLayoutView(btnLogin, w / 3, (int) getResources().getDimension(R.dimen.margin35));
        QTSRun.setLayoutView(btnSignUp, w / 3, (int) getResources().getDimension(R.dimen.margin35));
        QTSRun.setLayoutView(loginButton, w * 3 / 5, (int) getResources().getDimension(R.dimen.margin35));
        QTSRun.setFontTV(getApplicationContext(), lbEmail, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(getApplicationContext(), lbPassword, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontTV(getApplicationContext(), tvForgotPass, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(getApplicationContext(), btnLogin, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(getApplicationContext(), btnSignUp, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontEditText(getApplicationContext(), edEmail, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontEditText(getApplicationContext(), edPassword, QTSConst.FONT_ROBOTOSLAB_REGULAR);
        QTSRun.setFontButton(getApplicationContext(), loginButton, QTSConst.FONT_ROBOTOSLAB_REGULAR);

        gps = new GPSTracker(getApplicationContext());
        if (gps.canGetLocation()) {
            gps.getLocation();
            longitude = gps.getLongitude() + "";
            latitude = gps.getLatitude() + "";
        } else {
            longitude = "";
            latitude = "";
        }
        QTSRun.setTimezone(getApplicationContext(), timeZone());
        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        // App code
//                        Log.e("On Successfull", "User ID: "
//                                + loginResult.getAccessToken().getUserId()
//                                + "\n" +
//                                "Auth Token: "
//                                + loginResult.getAccessToken().getToken());
//                        QTSRun.setIsFBLogin(getApplicationContext(), true);
//                        facebook_access_token = loginResult.getAccessToken().getToken();
//                        email="";
//                        password="";
//                        localtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                                .format(Calendar.getInstance().getTime()).toString();
//                        new GetData().execute();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                        Log.e("Login Facebook", "Login attempt canceled.");
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                        Log.e("Login Facebook", "Login attempt failed.");
//                        exception.printStackTrace();
//                    }
//                });
        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                Log.e("Registration id", registrationId);
                android_device_id = registrationId;
                QTSRun.setRegistrationId(getApplicationContext(), registrationId);
                //send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
        if (!QTSRun.isNetworkAvailable(getApplicationContext())) {
            Intent intent = new Intent(LoginActivity.this,NoInternetAct.class);
            startActivity(intent);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edEmail.getText().toString().trim().length() > 0) {
                    if (QTSRun.isValidEmail(edEmail.getText().toString().trim())) {
                        if (edPassword.getText().toString().trim().length() > 0) {
                            email = edEmail.getText().toString().trim();
                            password = edPassword.getText().toString().trim();
                            QTSRun.setemail(getApplicationContext(), email);
                            QTSRun.setLname(getApplicationContext(), password);
                            facebook_access_token = "";
                            localtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .format(Calendar.getInstance().getTime()).toString();
                            Log.e("LoginActivity", "localtime:" + localtime);
                            if (QTSRun.isNetworkAvailable(getApplicationContext()))
                                getData();
                            else {
                                Intent intent = new Intent(LoginActivity.this,NoInternetAct.class);
                                startActivity(intent);
                            }
                        } else {
                            QTSRun.showToast(getApplicationContext(), "Password is required");
                        }
                    } else {
                        QTSRun.showToast(getApplicationContext(), "Email is incorrect");
                    }
                } else {
                    QTSRun.showToast(getApplicationContext(), "Email is required");
                }
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QTSRun.isNetworkAvailable(getApplicationContext())){
                    Intent intent = new Intent(LoginActivity.this, ForgottenPass.class);
                    intent.putExtra("url_load", QTSConst.URL_FORGOTTEN_PASSWORD);
                    startActivity(intent);
                }else {
                    Intent in = new Intent(LoginActivity.this,
                            NoInternetAct.class);
                    startActivity(in);
                }

            }
        });
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.palfed.android.menu",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = "";
                password = "";
                initializeFacebook();
//                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email", "user_friends"));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QTSRun.isNetworkAvailable(getApplicationContext())){
                    Intent intent = new Intent(LoginActivity.this, ForgottenPass.class);
                    intent.putExtra("url_load", "https://www.palfed.com/register?device=android");
                    startActivity(intent);
                }else {
                    Intent in = new Intent(LoginActivity.this,
                            NoInternetAct.class);
                    startActivity(in);
                }

            }
        });
    }

    LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    private void initializeFacebook() {
        loginManager = getLoginManager();
        loginManager.logInWithReadPermissions(this,
                Arrays.asList("public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(final LoginResult result) {
                        Log.e("LoginActivity", "Login.initializeFacebook.onSuccess Granted Permissions= " + result.getRecentlyGrantedPermissions().toString());
                        Log.e("LoginActivity", "Login.initializeFacebook.onSuccess");
                        facebook_access_token = result.getAccessToken().getToken();
                        QTSRun.setIsFBLogin(getApplicationContext(), true);
                        email = "";
                        password = "";
                        localtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(Calendar.getInstance().getTime()).toString();
                        getData();

//                        new GetData().execute();
//                        GraphRequest request = GraphRequest.newMeRequest(result.getAccessToken(),
//                                new GraphRequest.GraphJSONObjectCallback() {
//
//                                    @Override
//                                    public void onCompleted(JSONObject object,
//                                                            GraphResponse response) {
//                                        if (response.getError() != null) {
//                                            // handle error
//                                        } else {
//
//                                        }
//
//                                    }
//                                });
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,name,email,gender,birthday");
//                        request.setParameters(parameters);
//                        request.executeAsync();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("LoginActivity", "Login.initializeFacebook.onError " + error.getMessage());
                        if (error instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.e("LoginActivity", "Login.initializeFacebook.onCancel ");
                    }
                });
    }

    public static String timeZone() {
        Calendar calendar = Calendar.getInstance();
        String timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
        Log.e("LoginActivity", "timezone:" + timeZone);
        return timeZone;
    }

    private void getData() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(true);
        showProgressDialog();
        Ion.with(getApplicationContext())
                .load("POST", QTSConst.URL_LOGIN)
                .setBodyParameter("email", email)
                .setBodyParameter("password", password)
                .setBodyParameter("facebook_access_token", facebook_access_token)
                .setBodyParameter("no-details", "false")
                .setBodyParameter("device", "Android")
                .setBodyParameter("secret", "1")
                .setBodyParameter("android_registration_id", android_device_id)
                .setBodyParameter("longitude", longitude)
                .setBodyParameter("latitude", latitude)
                .setBodyParameter("localtime", localtime)
                .setBodyParameter("timezone", QTSRun.getTimezone(getApplicationContext()))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        hideProgressDialog();
                        if (e == null) {
                            try {
                                json = new JSONObject(result.toString());
                                if (json != null) {
                                    Log.d("Result", json.toString());
                                    if (json.getString("status").equalsIgnoreCase("Success")) {
                                        arrList = new ArrayList<ParentObject>();
                                        arrList1 = new ArrayList<ParentObject>();
                                        arrayParents = new ArrayList<FriendObjParent>();
                                        ParentObject pr_Object = new ParentObject();
                                        ParentObject pr_Object1 = new ParentObject();
                                        pr_Object.setStatus(json.getString("status"));
                                        pr_Object.setBase_url(json.getString("base_url"));
                                        pr_Object.setNotifications_url(json.getString("notifications_url"));
                                        pr_Object.setFriend_requests_url(json.getString("friend_requests_url"));
                                        pr_Object.setSecret(json.getString("secret"));
                                        pr_Object.setToken(json.getString("token"));
                                        pr_Object.setLogin_token(json.getString("login_token"));
                                        pr_Object1.setStatus(json.getString("status"));
                                        pr_Object1.setBase_url(json.getString("base_url"));
                                        pr_Object1.setNotifications_url(json.getString("notifications_url"));
                                        pr_Object1.setFriend_requests_url(json.getString("friend_requests_url"));
                                        pr_Object1.setSecret(json.getString("secret"));
                                        pr_Object1.setToken(json.getString("token"));
                                        pr_Object1.setLogin_token(json.getString("login_token"));

                                        QTSRun.setSecret(getApplicationContext(), json.getString("secret"));
                                        QTSRun.setTokenhash(getApplicationContext(), md5(json.getString("secret") + json.getString("token")));
                                        QTSRun.setToken(getApplicationContext(), json.getString("token"));
                                        QTSRun.SetLogin_token(getApplicationContext(), json.getString("login_token"));
//                                        login_token = json.getString("login_token");
//                                        token = json.getString("token");
//                                        token_hash = json.getString("secret") + json.getString("token");
//                                        secret = json.getString("secret");

//                        Log.e("get token_hash",json.getString("secret")+"//"+json.getString("token")+"//"+token_hash);
                                        if (json.toString().contains("friend_requests")) {
                                            JSONArray arr_parents = json.getJSONArray("friend_requests");
                                            if (arr_parents.length() > 0) {
                                                FriendObjParent parentObj_Fr = new FriendObjParent();
                                                parentObj_Fr.setmTitle("Friend Requests");
                                                parentObj_Fr.setIsRequest(1);
                                                ArrayList<FrRequestObj> arrayChildren = new ArrayList<FrRequestObj>();
                                                for (int m = 0; m < arr_parents.length(); m++) {
                                                    JSONObject item_Child = arr_parents.getJSONObject(m);
                                                    FrRequestObj objChild = new FrRequestObj();
                                                    objChild.setName(item_Child.getString("name"));
                                                    objChild.setPicture_url(item_Child.getString("picture"));
                                                    objChild.setFr_id(item_Child.getString("fr_id"));
                                                    objChild.setGuid(item_Child.getString("guid"));
                                                    objChild.setRequest_html(item_Child.getString("request_html"));
//                                    objChild.setIsRequest(1);
                                                    arrayChildren.add(objChild);
                                                }
                                                parentObj_Fr.setmArrayChildren(arrayChildren);
                                                arrayParents.add(parentObj_Fr);
                                            }
                                        }
                                        if (json.toString().contains("friend_suggestions")) {
                                            JSONArray arr_parents = json.getJSONArray("friend_suggestions");
                                            if (arr_parents.length() > 0) {
                                                FriendObjParent parentObj_Fr = new FriendObjParent();
                                                parentObj_Fr.setmTitle("Friend Suggestions");
                                                parentObj_Fr.setIsRequest(0);
                                                ArrayList<FrRequestObj> arrayChildren = new ArrayList<FrRequestObj>();
                                                for (int n = 0; n < arr_parents.length(); n++) {
                                                    JSONObject item_Child = arr_parents.getJSONObject(n);
                                                    FrRequestObj objChild = new FrRequestObj();
                                                    objChild.setName(item_Child.getString("name"));
                                                    objChild.setPicture_url(item_Child.getString("picture"));
                                                    objChild.setFr_id(item_Child.getString("fr_id"));
                                                    objChild.setGuid(item_Child.getString("guid"));
                                                    objChild.setRequest_html(item_Child.getString("request_html"));
//                                    objChild.setIsRequest(0);
                                                    arrayChildren.add(objChild);
                                                }
                                                parentObj_Fr.setmArrayChildren(arrayChildren);
                                                arrayParents.add(parentObj_Fr);
                                            }
                                        }
                                        JSONArray arr_menu = json.getJSONArray("menu");
                                        menuList = new ArrayList<MenuObject>();
                                        menuList1 = new ArrayList<MenuObject>();
                                        for (int i = 0; i < arr_menu.length(); i++) {
                                            JSONObject item_menu = arr_menu.getJSONObject(i);
                                            MenuObject mn_Object = new MenuObject();
                                            MenuObject mn_Object1 = new MenuObject();
                                            mn_Object.setTs(item_menu.getString("ts"));
                                            mn_Object.setDay(item_menu.getString("day"));
                                            mn_Object.setDay_no_year(item_menu.getString("day_no_year"));
                                            mn_Object.setYmd(item_menu.getString("ymd"));
                                            mn_Object1.setTs(item_menu.getString("ts"));
                                            mn_Object1.setDay(item_menu.getString("day"));
                                            mn_Object1.setDay_no_year(item_menu.getString("day_no_year"));
                                            mn_Object1.setYmd(item_menu.getString("ymd"));

                                            if (item_menu.toString().contains("day_name")) {
                                                mn_Object.setDay_name(item_menu.getString("day_name") + "");
                                                mn_Object1.setDay_name(item_menu.getString("day_name") + "");
                                            } else {
                                                mn_Object.setDay_name("");
                                                mn_Object1.setDay_name("");
                                            }

                                            JSONArray arr_options = item_menu.getJSONArray("options");
                                            optionList = new ArrayList<OptionObject>();
                                            optionList1 = new ArrayList<OptionObject>();
                                            for (int j = 0; j < arr_options.length(); j++) {
                                                JSONObject item_options = arr_options.getJSONObject(j);
                                                OptionObject op_object = new OptionObject();
                                                op_object.setTitle(item_options.getString("title"));
                                                op_object.setDescription(item_options.getString("description"));
                                                op_object.setMenu_type(item_options.getString("menu_type") + "");
                                                if (item_options.toString().contains("name")) {
                                                    op_object.setName(item_options.getString("name"));
                                                }
                                                if (item_options.toString().contains("tags")) {
                                                    JSONArray arr_tags = item_options.getJSONArray("tags");
                                                    ArrayList<TagsObject> arrTags_list = new ArrayList<TagsObject>();
                                                    for (int x = 0; x < arr_tags.length(); x++) {
                                                        TagsObject objTag = new TagsObject();
                                                        objTag.setTag(arr_tags.getJSONObject(x).getString("tag"));
                                                        objTag.setType(arr_tags.getJSONObject(x).getString("type"));
                                                        if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("feature")) {
                                                            objTag.setMcolor(getResources().getColor(R.color.feature));
                                                        } else if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("warning")) {
                                                            objTag.setMcolor(getResources().getColor(R.color.warning));
                                                        } else if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("general")) {
                                                            objTag.setMcolor(getResources().getColor(R.color.general));
                                                        } else if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("pending")) {
                                                            objTag.setMcolor(getResources().getColor(R.color.pending));
                                                        }
                                                        arrTags_list.add(objTag);
                                                    }
                                                    op_object.setTags(arrTags_list);
                                                }
                                                if (item_options.toString().contains("profile_pic_url")) {
                                                    op_object.setProfile_pic_url(item_options.getString("profile_pic_url"));
                                                }
                                                if (item_options.toString().contains("url")) {
                                                    op_object.setUrl(item_options.getString("url") + "");
                                                }
                                                if (item_options.toString().contains("food_image_url")) {
                                                    op_object.setFood_image_url(item_options.getString("food_image_url") + "");
                                                } else {
                                                    op_object.setFood_image_url("");
                                                }
                                                if (item_options.toString().contains("ready_date")) {
                                                    op_object.setReady_date(item_options.getString("ready_date") + "");
                                                }
                                                if (item_options.toString().contains("ready_time")) {
                                                    op_object.setReady_time(item_options.getString("ready_time") + "");
                                                }
                                                if (item_options.toString().contains("ready_timestamp")) {
                                                    op_object.setReady_timestamp(item_options.getString("ready_timestamp") + "");
                                                }
                                                if (item_options.toString().contains("ready_timestamp_tz_offset")) {
                                                    op_object.setReady_timestamp_tz_offset(item_options.getString("ready_timestamp_tz_offset") + "");
                                                }
                                                if (item_options.toString().contains("lat_long")) {
                                                    op_object.setLat_long(item_options.getString("lat_long") + "");
                                                }
                                                if (item_options.toString().contains("distance")) {
                                                    op_object.setDistance(item_options.getString("distance") + "");
                                                }
                                                if (item_options.toString().contains("distance_unit")) {
                                                    op_object.setDistance_unit(item_options.getString("distance_unit") + "");
                                                }
                                                if (item_options.toString().contains("from_now_text")) {
                                                    op_object.setFrom_now_text(item_options.getString("from_now_text") + "");
                                                }
                                                if (item_options.toString().contains("i_am_making_this")) {
                                                    op_object.setI_am_making_this(item_options.getString("i_am_making_this") + "");
                                                }
                                                if (item_options.toString().contains("can_join")) {
                                                    op_object.setCan_join(item_options.getString("can_join") + "");
                                                } else {
                                                    op_object.setCan_join("");
                                                }
                                                if (item_options.toString().contains("bring_container")) {
                                                    op_object.setBring_container(item_options.getString("bring_container") + "");
                                                }
                                                if (item_options.toString().contains("eating")) {
                                                    op_object.setEating(item_options.getString("eating") + "");
                                                }
                                                if (item_options.toString().contains("eating_out")) {
                                                    op_object.setEating_out(item_options.getString("eating_out") + "");
                                                }
                                                if (item_options.toString().contains("available_portions")) {
                                                    op_object.setAvailable_portions(item_options.getString("available_portions") + "");
                                                }
                                                if (item_options.toString().contains("portions_im_eating")) {
                                                    op_object.setPortions_im_eating(item_options.getString("portions_im_eating") + "");
                                                }
                                                if (item_options.toString().contains("notify_text")) {
                                                    op_object.setNotify_text(item_options.getString("notify_text") + "");
                                                } else {
                                                    op_object.setNotify_text("");
                                                }
                                                if (item_options.toString().contains("mutual_friend_text")) {
                                                    op_object.setMutual_friend_text(item_options.getString("mutual_friend_text") + "");

                                                }
                                                if (item_options.toString().contains("mutual_friend_html")) {
                                                    op_object.setMutual_friend_html(item_options.getString("mutual_friend_html") + "");
                                                }
                                                if (item_options.toString().contains("restaurant")) {
                                                    op_object.setRestaurant(item_options.getString("restaurant"));
                                                }
                                                if (item_options.toString().contains("restaurant_profile_pic_url")) {
                                                    op_object.setRestaurant_profile_pic_url(item_options.getString("restaurant_profile_pic_url"));
                                                }

                                                if (item_options.toString().contains("comments")) {
                                                    if (item_options.getString("comments").toString().length() > 0) {
                                                        op_object.setComments(item_options.getString("comments"));
                                                    } else {
                                                        op_object.setComments("0");
                                                    }
                                                }

                                                optionList.add(op_object);
                                                if (op_object.getNotify_text().length() > 0) {//&& op_object.getI_am_making_this().equalsIgnoreCase("1")){
                                                    optionList1.add(op_object);
                                                }
                                            }
                                            mn_Object.setOptionObjectArrayList(optionList);
                                            menuList.add(mn_Object);
                                            if (optionList1.size() > 0) {
                                                mn_Object1.setOptionObjectArrayList(optionList1);
                                                menuList1.add(mn_Object1);
                                            }

                                        }
                                        pr_Object.setMenuObjectArrayList(menuList);
                                        pr_Object1.setMenuObjectArrayList(menuList1);
                                        JSONObject item_user = json.getJSONObject("user");
                                        us_Object = new UserObject();
                                        us_Object.setProfile_pic_url(item_user.getString("profile_pic_url"));
                                        us_Object.setNotification_count(item_user.get("notification_count").toString());
                                        if (!item_user.get("friend_request_count").toString().isEmpty())
                                            us_Object.setFriend_request_count(item_user.get("friend_request_count").toString());
                                        else us_Object.setFriend_request_count("0");
                                        arrList.add(pr_Object);
                                        arrList1.add(pr_Object1);

                                        QTSRun.setBadge(getApplicationContext(), Integer.parseInt(us_Object.getNotification_count()));
                                        ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(us_Object.getNotification_count()));
                                        QTSRun.setFr_request(getApplicationContext(), Integer.parseInt(us_Object.getFriend_request_count()));
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelableArrayList("data_arr",
                                                (ArrayList<? extends Parcelable>) arrList);
                                        bundle.putParcelableArrayList("data_arr1",
                                                (ArrayList<? extends Parcelable>) arrList1);
                                        bundle.putParcelableArrayList("data_friend",
                                                (ArrayList<? extends Parcelable>) arrayParents);
                                        i.putExtras(bundle);
                                        i.putExtra("user_object", us_Object);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        QTSRun.showToast(getApplicationContext(), json.getString("message"));
                                    }
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }else {
                            Log.e("LoginActivity", "Excaption:" +e.getMessage());
                            QTSRun.showToast(getApplicationContext(), "Cannot connect to server, please try later!");
                        }
                    }
                });
        getNavMenu();
    }

//    class GetData extends AsyncTask<String, Void, String> {
//        String token_hash = "";
//        String token = "";
//        String secret = "";
//        String login_token = "";
//        String message = "";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(LoginActivity.this);
//            pDialog.setMessage("Logging you in...");
//            pDialog.show();
//            pDialog.setCancelable(false);
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String status = "";
//            HashMap<String, String> params = new HashMap<>();
//            params.put("email", email);
//            params.put("password", password);
//            params.put("facebook_access_token", facebook_access_token);
//            params.put("no-details", "false");
//            params.put("device", "Android");
//            params.put("secret", "1");
//            params.put("android_registration_id", android_device_id);
//            params.put("longitude", longitude);
//            params.put("latitude", latitude);
//            params.put("localtime", localtime);
//            params.put("timezone", QTSRun.getTimezone(getApplicationContext()));
////            Log.e("fbtoken :", facebook_access_token);
//            try {
//                json = jsonParser.makeHttpRequest(QTSConst.URL_LOGIN, "POST", params);
//                if (json != null) {
//                    Log.d("Result", json.toString());
//                    status = json.getString("status");
//                    if (status.equalsIgnoreCase("Success")) {
//                        arrList = new ArrayList<ParentObject>();
//                        arrList1 = new ArrayList<ParentObject>();
//                        arrayParents = new ArrayList<FriendObjParent>();
//                        ParentObject pr_Object = new ParentObject();
//                        ParentObject pr_Object1 = new ParentObject();
//                        pr_Object.setStatus(json.getString("status"));
//                        pr_Object.setBase_url(json.getString("base_url"));
//                        pr_Object.setNotifications_url(json.getString("notifications_url"));
//                        pr_Object.setFriend_requests_url(json.getString("friend_requests_url"));
//                        pr_Object.setSecret(json.getString("secret"));
//                        pr_Object.setToken(json.getString("token"));
//                        pr_Object.setLogin_token(json.getString("login_token"));
//                        pr_Object1.setStatus(json.getString("status"));
//                        pr_Object1.setBase_url(json.getString("base_url"));
//                        pr_Object1.setNotifications_url(json.getString("notifications_url"));
//                        pr_Object1.setFriend_requests_url(json.getString("friend_requests_url"));
//                        pr_Object1.setSecret(json.getString("secret"));
//                        pr_Object1.setToken(json.getString("token"));
//                        pr_Object1.setLogin_token(json.getString("login_token"));
//                        login_token = json.getString("login_token");
//                        token = json.getString("token");
//                        token_hash = json.getString("secret") + json.getString("token");
//                        secret = json.getString("secret");
////                        Log.e("get token_hash",json.getString("secret")+"//"+json.getString("token")+"//"+token_hash);
//                        if (json.toString().contains("friend_requests")) {
//                            JSONArray arr_parents = json.getJSONArray("friend_requests");
//                            if (arr_parents.length() > 0) {
//                                FriendObjParent parentObj_Fr = new FriendObjParent();
//                                parentObj_Fr.setmTitle("Friend Requests");
//                                parentObj_Fr.setIsRequest(1);
//                                ArrayList<FrRequestObj> arrayChildren = new ArrayList<FrRequestObj>();
//                                for (int m = 0; m < arr_parents.length(); m++) {
//                                    JSONObject item_Child = arr_parents.getJSONObject(m);
//                                    FrRequestObj objChild = new FrRequestObj();
//                                    objChild.setName(item_Child.getString("name"));
//                                    objChild.setPicture_url(item_Child.getString("picture"));
//                                    objChild.setFr_id(item_Child.getString("fr_id"));
//                                    objChild.setGuid(item_Child.getString("guid"));
//                                    objChild.setRequest_html(item_Child.getString("request_html"));
////                                    objChild.setIsRequest(1);
//                                    arrayChildren.add(objChild);
//                                }
//                                parentObj_Fr.setmArrayChildren(arrayChildren);
//                                arrayParents.add(parentObj_Fr);
//                            }
//                        }
//                        if (json.toString().contains("friend_suggestions")) {
//                            JSONArray arr_parents = json.getJSONArray("friend_suggestions");
//                            if (arr_parents.length() > 0) {
//                                FriendObjParent parentObj_Fr = new FriendObjParent();
//                                parentObj_Fr.setmTitle("Friend Suggestions");
//                                parentObj_Fr.setIsRequest(0);
//                                ArrayList<FrRequestObj> arrayChildren = new ArrayList<FrRequestObj>();
//                                for (int n = 0; n < arr_parents.length(); n++) {
//                                    JSONObject item_Child = arr_parents.getJSONObject(n);
//                                    FrRequestObj objChild = new FrRequestObj();
//                                    objChild.setName(item_Child.getString("name"));
//                                    objChild.setPicture_url(item_Child.getString("picture"));
//                                    objChild.setFr_id(item_Child.getString("fr_id"));
//                                    objChild.setGuid(item_Child.getString("guid"));
//                                    objChild.setRequest_html(item_Child.getString("request_html"));
////                                    objChild.setIsRequest(0);
//                                    arrayChildren.add(objChild);
//                                }
//                                parentObj_Fr.setmArrayChildren(arrayChildren);
//                                arrayParents.add(parentObj_Fr);
//                            }
//                        }
//                        JSONArray arr_menu = json.getJSONArray("menu");
//                        menuList = new ArrayList<MenuObject>();
//                        menuList1 = new ArrayList<MenuObject>();
//                        for (int i = 0; i < arr_menu.length(); i++) {
//                            JSONObject item_menu = arr_menu.getJSONObject(i);
//                            MenuObject mn_Object = new MenuObject();
//                            MenuObject mn_Object1 = new MenuObject();
//                            mn_Object.setTs(item_menu.getString("ts"));
//                            mn_Object.setDay(item_menu.getString("day"));
//                            mn_Object.setDay_no_year(item_menu.getString("day_no_year"));
//                            mn_Object.setYmd(item_menu.getString("ymd"));
//                            mn_Object1.setTs(item_menu.getString("ts"));
//                            mn_Object1.setDay(item_menu.getString("day"));
//                            mn_Object1.setDay_no_year(item_menu.getString("day_no_year"));
//                            mn_Object1.setYmd(item_menu.getString("ymd"));
//
//                            if (item_menu.toString().contains("day_name")) {
//                                mn_Object.setDay_name(item_menu.getString("day_name") + "");
//                                mn_Object1.setDay_name(item_menu.getString("day_name") + "");
//                            } else {
//                                mn_Object.setDay_name("");
//                                mn_Object1.setDay_name("");
//                            }
//
//                            JSONArray arr_options = item_menu.getJSONArray("options");
//                            optionList = new ArrayList<OptionObject>();
//                            optionList1 = new ArrayList<OptionObject>();
//                            for (int j = 0; j < arr_options.length(); j++) {
//                                JSONObject item_options = arr_options.getJSONObject(j);
//                                OptionObject op_object = new OptionObject();
//                                op_object.setTitle(item_options.getString("title"));
//                                op_object.setDescription(item_options.getString("description"));
//                                op_object.setMenu_type(item_options.getString("menu_type") + "");
//                                if (item_options.toString().contains("name")) {
//                                    op_object.setName(item_options.getString("name"));
//                                }
//                                if (item_options.toString().contains("tags")) {
//                                    JSONArray arr_tags = item_options.getJSONArray("tags");
//                                    ArrayList<TagsObject> arrTags_list = new ArrayList<TagsObject>();
//                                    for (int x = 0; x < arr_tags.length(); x++) {
//                                        TagsObject objTag = new TagsObject();
//                                        objTag.setTag(arr_tags.getJSONObject(x).getString("tag"));
//                                        objTag.setType(arr_tags.getJSONObject(x).getString("type"));
//                                        if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("feature")) {
//                                            objTag.setMcolor(getResources().getColor(R.color.feature));
//                                        } else if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("warning")) {
//                                            objTag.setMcolor(getResources().getColor(R.color.warning));
//                                        } else if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("general")) {
//                                            objTag.setMcolor(getResources().getColor(R.color.general));
//                                        } else if (arr_tags.getJSONObject(x).getString("type").equalsIgnoreCase("pending")) {
//                                            objTag.setMcolor(getResources().getColor(R.color.pending));
//                                        }
//                                        arrTags_list.add(objTag);
//                                    }
//                                    op_object.setTags(arrTags_list);
//                                }
//                                if (item_options.toString().contains("profile_pic_url")) {
//                                    op_object.setProfile_pic_url(item_options.getString("profile_pic_url"));
//                                }
//                                if (item_options.toString().contains("url")) {
//                                    op_object.setUrl(item_options.getString("url") + "");
//                                }
//                                if (item_options.toString().contains("food_image_url")) {
//                                    op_object.setFood_image_url(item_options.getString("food_image_url") + "");
//                                } else {
//                                    op_object.setFood_image_url("");
//                                }
//                                if (item_options.toString().contains("ready_date")) {
//                                    op_object.setReady_date(item_options.getString("ready_date") + "");
//                                }
//                                if (item_options.toString().contains("ready_time")) {
//                                    op_object.setReady_time(item_options.getString("ready_time") + "");
//                                }
//                                if (item_options.toString().contains("ready_timestamp")) {
//                                    op_object.setReady_timestamp(item_options.getString("ready_timestamp") + "");
//                                }
//                                if (item_options.toString().contains("ready_timestamp_tz_offset")) {
//                                    op_object.setReady_timestamp_tz_offset(item_options.getString("ready_timestamp_tz_offset") + "");
//                                }
//                                if (item_options.toString().contains("lat_long")) {
//                                    op_object.setLat_long(item_options.getString("lat_long") + "");
//                                }
//                                if (item_options.toString().contains("distance")) {
//                                    op_object.setDistance(item_options.getString("distance") + "");
//                                }
//                                if (item_options.toString().contains("distance_unit")) {
//                                    op_object.setDistance_unit(item_options.getString("distance_unit") + "");
//                                }
//                                if (item_options.toString().contains("from_now_text")) {
//                                    op_object.setFrom_now_text(item_options.getString("from_now_text") + "");
//                                }
//                                if (item_options.toString().contains("i_am_making_this")) {
//                                    op_object.setI_am_making_this(item_options.getString("i_am_making_this") + "");
//                                }
//                                if (item_options.toString().contains("can_join")) {
//                                    op_object.setCan_join(item_options.getString("can_join") + "");
//                                } else {
//                                    op_object.setCan_join("");
//                                }
//                                if (item_options.toString().contains("bring_container")) {
//                                    op_object.setBring_container(item_options.getString("bring_container") + "");
//                                }
//                                if (item_options.toString().contains("eating")) {
//                                    op_object.setEating(item_options.getString("eating") + "");
//                                }
//                                if (item_options.toString().contains("eating_out")) {
//                                    op_object.setEating_out(item_options.getString("eating_out") + "");
//                                }
//                                if (item_options.toString().contains("available_portions")) {
//                                    op_object.setAvailable_portions(item_options.getString("available_portions") + "");
//                                }
//                                if (item_options.toString().contains("portions_im_eating")) {
//                                    op_object.setPortions_im_eating(item_options.getString("portions_im_eating") + "");
//                                }
//                                if (item_options.toString().contains("notify_text")) {
//                                    op_object.setNotify_text(item_options.getString("notify_text") + "");
//                                } else {
//                                    op_object.setNotify_text("");
//                                }
//                                if (item_options.toString().contains("mutual_friend_text")) {
//                                    op_object.setMutual_friend_text(item_options.getString("mutual_friend_text") + "");
//
//                                }
//                                if (item_options.toString().contains("mutual_friend_html")) {
//                                    op_object.setMutual_friend_html(item_options.getString("mutual_friend_html") + "");
//                                }
//                                if (item_options.toString().contains("restaurant")) {
//                                    op_object.setRestaurant(item_options.getString("restaurant"));
//                                }
//                                if (item_options.toString().contains("restaurant_profile_pic_url")) {
//                                    op_object.setRestaurant_profile_pic_url(item_options.getString("restaurant_profile_pic_url"));
//                                }
//
//                                if (item_options.toString().contains("comments")) {
//                                    if (item_options.getString("comments").toString().length() > 0) {
//                                        op_object.setComments(item_options.getString("comments"));
//                                    } else {
//                                        op_object.setComments("0");
//                                    }
//                                }
//
//                                optionList.add(op_object);
//                                if (op_object.getNotify_text().length() > 0) {//&& op_object.getI_am_making_this().equalsIgnoreCase("1")){
//                                    optionList1.add(op_object);
//                                }
//                            }
//                            mn_Object.setOptionObjectArrayList(optionList);
//                            menuList.add(mn_Object);
//                            if (optionList1.size() > 0) {
//                                mn_Object1.setOptionObjectArrayList(optionList1);
//                                menuList1.add(mn_Object1);
//                            }
//
//                        }
//                        pr_Object.setMenuObjectArrayList(menuList);
//                        pr_Object1.setMenuObjectArrayList(menuList1);
//                        JSONObject item_user = json.getJSONObject("user");
//                        us_Object = new UserObject();
//                        us_Object.setProfile_pic_url(item_user.getString("profile_pic_url"));
//                        us_Object.setNotification_count(item_user.get("notification_count").toString());
//                        if (!item_user.get("friend_request_count").toString().isEmpty())
//                            us_Object.setFriend_request_count(item_user.get("friend_request_count").toString());
//                        else us_Object.setFriend_request_count("0");
////                        Log.e("LoginActivity","notification_count:" +item_user.get("friend_request_count"));
//                        arrList.add(pr_Object);
//                        arrList1.add(pr_Object1);
//                    } else message = json.getString("message");
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return status;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            pDialog.dismiss();
//            pDialog.cancel();
//            if (s.equalsIgnoreCase("Success")) {
////                Log.e("token_hash",token_hash);
//                QTSRun.setSecret(getApplicationContext(), secret);
//                QTSRun.setTokenhash(getApplicationContext(), md5(token_hash));
//                QTSRun.setToken(getApplicationContext(), token);
//                QTSRun.SetLogin_token(getApplicationContext(), login_token);
//                QTSRun.setBadge(getApplicationContext(), Integer.parseInt(us_Object.getNotification_count()));
//                ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(us_Object.getNotification_count()));
//                QTSRun.setFr_request(getApplicationContext(), Integer.parseInt(us_Object.getFriend_request_count()));
////                Log.e("token_hash => MD5", md5(token_hash));
//                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("data_arr",
//                        (ArrayList<? extends Parcelable>) arrList);
//                bundle.putParcelableArrayList("data_arr1",
//                        (ArrayList<? extends Parcelable>) arrList1);
//                bundle.putParcelableArrayList("data_friend",
//                        (ArrayList<? extends Parcelable>) arrayParents);
//                i.putExtras(bundle);
//                i.putExtra("user_object", us_Object);
//                startActivity(i);
//                finish();
//            } else {
//                if (message.isEmpty()) {
//                    QTSRun.showToast(getApplicationContext(), "Login failed");
//                } else {
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }
//    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }else {
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // Logs 'install' and 'app activate' App Events.
//        AppEventsLogger.activateApp(this);
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // Logs 'app deactivate' App Event.
//        AppEventsLogger.deactivateApp(this);
//    }
private void getNavMenu(){
    Ion.with(LoginActivity.this)
            .load(QTSConst.URL_GET_MENU+"?token_hash="+QTSRun.getTokenhash(getApplicationContext())+"&get-nav=1")
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    Log.e("error",QTSConst.URL_GET_MENU+"?token_hash="+QTSRun.getTokenhash(getApplicationContext())+"&get-nav=1");
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
                                    QTSRun.setToken(getApplicationContext(), json.getString("token"));
                                    QTSRun.SetLogin_token(getApplicationContext(), json.getString("login_token"));
                                    QTSRun.setTokenhash(getApplicationContext(), md5(QTSRun.getSecret(getApplicationContext()) + json.getString("token")));
                                    Log.e("tokenhash login", md5(QTSRun.getSecret(getApplicationContext()) + json.getString("token")));
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
