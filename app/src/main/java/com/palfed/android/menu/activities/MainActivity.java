package com.palfed.android.menu.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.palfed.android.menu.activities.customviews.CircularImageView;
import com.palfed.android.menu.activities.objects.MenuObject;
import com.palfed.android.menu.activities.objects.OptionObject;
import com.palfed.android.menu.activities.objects.ParentObject;
import com.palfed.android.menu.R;
import com.palfed.android.menu.activities.adapter.ExpandListAdapter;
import com.palfed.android.menu.activities.adapter.ListDataAdapter;
import com.palfed.android.menu.activities.commonhelper.GPSTracker;
import com.palfed.android.menu.activities.commonhelper.ImageLoaderAvar;
import com.palfed.android.menu.activities.commonhelper.JSONParser;
import com.palfed.android.menu.activities.commonhelper.QTSConst;
import com.palfed.android.menu.activities.commonhelper.QTSRun;
import com.palfed.android.menu.activities.objects.ItemObject;
import com.palfed.android.menu.activities.objects.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends Activity implements View.OnClickListener{
    private ImageView ivLogo, ivNotifications, ivUsers, ivCalendar, ivHome, ivLogout;
    private CircularImageView ivAvatar;
    private TextView tvNoFound, tv_Notif;
    private Switch mSwitch;
    private ListView lv;
    private ExpandableListView expListView;
    private ExpandableListView expListView1;
    private LinearLayout rl_Group1, rl_Group2;
    private int w, h;
    private String localtime;
    public static int getBroadcast;
    JSONParser jsonParser = new JSONParser();
    JSONObject json = null;
    private ExpandListAdapter listAdapter;
    private ExpandListAdapter listAdapter1;
    List<ParentObject> listParent = null;
    List<OptionObject> listOptions = null;
    List<MenuObject> listMenu = null;
    HashMap<MenuObject, List<OptionObject>> listChild;
    List<OptionObject> listOptions1 = null;
    List<ParentObject> listParent1 = null;
    List <MenuObject> listMenu1 = null;
    HashMap<MenuObject, List<OptionObject>> listChild1 = null;

    boolean isClick = true;
    boolean isTickCal = false;
    private ImageLoaderAvar _imageLoader;
    private String longitude="";
    private String latitude="";

    private GPSTracker gps;

    JSONParser jsonParser1 = new JSONParser();
    JSONObject json1 = null;
    private ProgressDialog pDialog;

    UserObject us_Object1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        gps = new GPSTracker(this);
        rl_Group1 = (LinearLayout) findViewById(R.id.rl_group1);
        rl_Group2 = (LinearLayout) findViewById(R.id.rl_group2);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        ivUsers = (ImageView) findViewById(R.id.ivUsers);
        ivCalendar = (ImageView) findViewById(R.id.ivCalendar);
        ivHome = (ImageView) findViewById(R.id.ivHome);
        ivNotifications = (ImageView) findViewById(R.id.ivNotification);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        ivAvatar = (CircularImageView) findViewById(R.id.ivAvatar);
        mSwitch = (Switch) findViewById(R.id.switch_main);
        lv = (ListView) findViewById(R.id.listmain);
        tvNoFound = (TextView) findViewById(R.id.tvNoFound);
        tv_Notif = (TextView) findViewById(R.id.tv_Notif);
        expListView = (ExpandableListView) findViewById(R.id.lvExp1);
        expListView.setGroupIndicator(null);
        expListView1 = (ExpandableListView) findViewById(R.id.lvExp2);
        expListView1.setGroupIndicator(null);
        Log.e("number baged", QTSRun.getBadge(getApplication())+"");
        if (QTSRun.getBadge(getApplicationContext())>0){
            tv_Notif.setText(""+QTSRun.getBadge(getApplicationContext()));
            tv_Notif.setVisibility(View.VISIBLE);
        }else{
            tv_Notif.setVisibility(View.INVISIBLE);
        }


        _imageLoader = new ImageLoaderAvar(MainActivity.this);
        w = QTSRun.GetWidthDevice(getApplicationContext());
        h = QTSRun.GetHeightDevice(getApplicationContext());

        QTSRun.setFontTV(getApplicationContext(),tv_Notif,QTSConst.FONT_ARBUTUSSLAB_REGULAR);
        QTSRun.setFontTV(getApplicationContext(), tvNoFound, QTSConst.FONT_ARBUTUSSLAB_REGULAR);
        QTSRun.setLayoutView(ivLogo, w/4,w/4*143/190);
        QTSRun.setLayoutView(mSwitch, w/4,w/4*143/190);
        QTSRun.setLayoutView(ivUsers, w/9,w/9);
        QTSRun.setLayoutView(ivNotifications, w / 9, w / 9);
        QTSRun.setLayoutView(ivCalendar, w / 9, w / 9);
        QTSRun.setLayoutView(ivHome, w / 9, w / 9);
        QTSRun.setLayoutView(ivAvatar, w / 9, w / 9);
        QTSRun.setLayoutView(ivLogout, w / 9, w / 9);

        if(gps.canGetLocation() ){
            gps.getLocation();
            longitude = gps.getLongitude()+"";
            latitude = gps.getLatitude()+"";
        }else{
            longitude ="";
            latitude = "";
        }

        ivHome.setOnClickListener(this);
        ivLogout.setOnClickListener(this);
        ivNotifications.setOnClickListener(this);
        ivCalendar.setOnClickListener(this);
        ivUsers.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (buttonView.isChecked()) {
                    isClick = true;
                    rl_Group2.setVisibility(View.GONE);
                    rl_Group1.setVisibility(View.VISIBLE);
                    ivLogo.setBackgroundResource(R.drawable.switch2);

                } else {
                    isClick = false;
                    rl_Group1.setVisibility(View.GONE);
                    rl_Group2.setVisibility(View.VISIBLE);
                    ivLogo.setBackgroundResource(R.drawable.switch1);

                }
            }
        });


        listParent = new ArrayList<ParentObject>();
        listParent1 = new ArrayList<ParentObject>();
        listMenu1 = new ArrayList<MenuObject>();
        listChild1 = new HashMap<MenuObject,List<OptionObject>>();
        Log.e("token_hash1",QTSRun.getTokenhash(getApplicationContext()));

        if (QTSRun.getIsRegister(getApplicationContext())){
            Log.e("token_hash Main1", QTSRun.getTokenhash(getApplicationContext()));

            if (QTSRun.isNetworkAvailable(getApplicationContext())){
                localtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(Calendar.getInstance().getTime()).toString();
                new GetData().execute();
            }else{
                QTSRun.showToast(getApplicationContext(),"Network is disconnected");
                QTSRun.setIsRegister(getApplicationContext(), false);
                Intent intent = new Intent(this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            QTSRun.setIsRegister(getApplicationContext(),true);
            Bundle bundle = getIntent().getExtras();
//        Intent intent = getIntent();
            UserObject usersss = getIntent().getExtras().getParcelable("user_object");
            if(usersss != null){
                _imageLoader.DisplayImage(usersss.getProfile_pic_url(),ivAvatar);
                Log.e("image url",usersss.getProfile_pic_url().toString());
            }
            listParent = bundle.getParcelableArrayList("data_arr");
            listParent1 = bundle.getParcelableArrayList("data_arr1");
            listMenu = new ArrayList<MenuObject>();
            listChild = new HashMap<MenuObject,List<OptionObject>>();
            if (listParent.size() > 0){
                Log.e("token_hash Main", QTSRun.getTokenhash(getApplicationContext()));
                listMenu = (ArrayList<MenuObject>) listParent.get(0).getMenuObjectArrayList();
                for (int ix = 0; ix <listMenu.size(); ix ++){
                    listOptions = new ArrayList<OptionObject>();
                    listOptions = listMenu.get(ix).getOptionObjectArrayList();
                    listChild.put(listMenu.get(ix),listOptions);
                }
                listAdapter = new ExpandListAdapter(MainActivity.this,
                        listMenu, listChild);
                expListView.setAdapter(listAdapter);

                for (int i = 0; i < listMenu.size(); i++) {
                    expListView.expandGroup(i);
                }
//                if (QTSRun.isNetworkAvailable(this)){
//                    new SetLocation().execute();
////                    new RegistrationGCM().execute();
//                }
            }
            if (listParent1.size() > 0){
                listMenu1 = (ArrayList<MenuObject>) listParent1.get(0).getMenuObjectArrayList();
                for (int ix = 0; ix <listMenu1.size(); ix ++){
                    listOptions1 = new ArrayList<OptionObject>();
                    listOptions1 = listMenu1.get(ix).getOptionObjectArrayList();
//                    if (listOptions1.size()>0)
                        listChild1.put(listMenu1.get(ix),listOptions1);
                }
                listAdapter1 = new ExpandListAdapter(MainActivity.this,
                        listMenu1, listChild1);
                expListView1.setAdapter(listAdapter1);
            }

            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.e("url",listChild.get(listMenu.get(groupPosition)).get(childPosition).getUrl());
                    Intent intent = new Intent(MainActivity.this,WebBrowser.class);
                    intent.putExtra("url",listChild.get(listMenu.get(groupPosition)).get(childPosition).getUrl());
                    startActivity(intent);
                    return false;
                }
            });
            expListView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.e("url",listChild1.get(listMenu1.get(groupPosition)).get(childPosition).getUrl());
                    Intent intent = new Intent(MainActivity.this,WebBrowser.class);
                    intent.putExtra("url",listChild1.get(listMenu1.get(groupPosition)).get(childPosition).getUrl());
                    startActivity(intent);
                    return false;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivHome){
            Intent intent = new Intent(MainActivity.this,WebBrowser.class);
            intent.putExtra("url",listParent.get(0).getBase_url());
            startActivity(intent);
        }else if (v == ivAvatar){
            Log.e("my account",listParent.get(0).getBase_url());
//            if (Build.VERSION.SDK_INT != Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent(MainActivity.this,WebBrowser.class);
                intent.putExtra("url", listParent.get(0).getBase_url().toString() +"account");
                startActivity(intent);
//            }else{
//                String url ="";
//                if (listParent.get(0).getBase_url().toString().endsWith("?")){
//                    url = listParent.get(0).getBase_url().toString() +"account&login="+ QTSRun.GetLogin_token(getApplicationContext());
//                }else{
//                    url = listParent.get(0).getBase_url().toString() +"account?login="+ QTSRun.GetLogin_token(getApplicationContext());
//
//                }
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//            }


        }else if (v == ivNotifications){
            Intent intent = new Intent(MainActivity.this,WebBrowser.class);
            intent.putExtra("url",listParent.get(0).getNotifications_url());
            startActivity(intent);
//            tv_Notif.setVisibility(View.INVISIBLE);
            boolean success = ShortcutBadger.removeCount(MainActivity.this);
        }else if (v == ivUsers){
            Intent intent = new Intent(MainActivity.this,WebBrowser.class);
            intent.putExtra("url",listParent.get(0).getFriend_requests_url());
            startActivity(intent);
        }else if (v == ivLogout){
            ShowDialog();

        }else if (v == ivCalendar){
            if (!isTickCal){
                ivCalendar.setBackgroundResource(R.drawable.ic_calendar2);
                isTickCal = true;
                if (expListView1.getCount()>0){
                    for (int i = 0; i < listMenu1.size(); i++) {
                        expListView1.expandGroup(i);
                    }
                    tvNoFound.setVisibility(View.GONE);
                    expListView1.setVisibility(View.VISIBLE);
                    expListView.setVisibility(View.GONE);
                }else{
                    tvNoFound.setVisibility(View.VISIBLE);
                    expListView1.setVisibility(View.GONE);
                    expListView.setVisibility(View.GONE);
                }
//                if (listMenu1 != null && listMenu1.size()>0){
//                    tvNoFound.setVisibility(View.GONE);
//                    expListView1.setVisibility(View.VISIBLE);
//                    expListView.setVisibility(View.GONE);
//                    listAdapter1 = new ExpandListAdapter(MainActivity.this,
//                            listMenu1, listChild1, listParent1.get(0).getBase_url().toString());
//                    expListView1.setAdapter(listAdapter1);
//                }else{
//                    expListView1.setVisibility(View.GONE);
//                    expListView.setVisibility(View.GONE);
//                    tvNoFound.setVisibility(View.VISIBLE);
//                }
            }else{
                isTickCal = false;
                ivCalendar.setBackgroundResource(R.drawable.ic_calendar1);
                expListView.setVisibility(View.VISIBLE);
                expListView1.setVisibility(View.GONE);
//                if (listMenu != null && listMenu.size() > 0) {
//                    listAdapter = new ExpandListAdapter(MainActivity.this,
//                            listMenu, listChild,listParent.get(0).getBase_url().toString());
//                    expListView.setAdapter(listAdapter);
//                }
                if (expListView.getCount()>0){
                    for (int i = 0; i < listMenu.size(); i++) {
                        expListView.expandGroup(i);
                    }
                    expListView.setVisibility(View.VISIBLE);
                    tvNoFound.setVisibility(View.GONE);
                }else{
                    tvNoFound.setVisibility(View.VISIBLE);
                    expListView1.setVisibility(View.GONE);
                    expListView.setVisibility(View.GONE);
                }

            }
        }
    }

//    class SetLocation extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String status = "";
//            HashMap<String, String> params = new HashMap<>();
//            params.put("longitude", longitude);
//            params.put("latitude", latitude);
//            params.put("token_hash", QTSRun.getTokenhash(getApplicationContext()));
//            params.put("timezone", QTSRun.getTimezone(getApplicationContext()));
//            try {
//                json = jsonParser.makeHttpRequest(QTSConst.URL_SETLOCATION, "POST", params);
//                if (json != null) {
//                    Log.e("setLocation", json.toString());
//                    status = json.getString("status");
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
//
//        }
//    }
//    class RegistrationGCM extends AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String status = "";
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("token_hash", QTSRun.getTokenhash(getApplicationContext())));
//            params.add(new BasicNameValuePair("registration_id", QTSRun.getRegistrationId(getApplicationContext())));
//            try {
//                json = jsonParser.makeHttpRequest(QTSConst.URL_REGISTRATION, "POST", params);
//                if (json != null) {
//                    Log.e("registration", json.toString());
//                    status = json.getString("status");
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
//
//        }
//    }
    class GetData extends AsyncTask<String, Void, String> {
        String token_hash="";
        String login_token = "";
        String token = QTSRun.getToken(getApplicationContext());
        String secret = QTSRun.getSecret(getApplicationContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Refreshing menu...");
            pDialog.show();
            pDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            String status = "";

            HashMap<String, String> params = new HashMap<>();
            params.put("token_hash", QTSRun.getTokenhash(getApplicationContext()));
            params.put("longitude", longitude);
            params.put("latitude", latitude);
            params.put("localtime", localtime);
            params.put("timezone", QTSRun.getTimezone(getApplicationContext()));
            try {
                json = jsonParser.makeHttpRequest(QTSConst.URL_GET_MENU, "POST", params);
                if (json != null) {
                    Log.d("Result", json.toString());
                    status = json.getString("status");

                    if (status.equalsIgnoreCase("Success")) {
                        listParent = new ArrayList<ParentObject>();
                        ParentObject pr_Object = new ParentObject();
                        ParentObject pr_Object1 = new ParentObject();
                        pr_Object.setStatus(json.getString("status"));
                        pr_Object.setBase_url(json.getString("base_url"));
                        pr_Object.setNotifications_url(json.getString("notifications_url"));
                        pr_Object.setFriend_requests_url(json.getString("friend_requests_url"));
                        pr_Object.setSecret(secret);
                        pr_Object.setToken(json.getString("token"));
                        pr_Object.setLogin_token(json.getString("login_token"));
                        pr_Object1.setStatus(json.getString("status"));
                        pr_Object1.setBase_url(json.getString("base_url"));
                        pr_Object1.setNotifications_url(json.getString("notifications_url"));
                        pr_Object1.setFriend_requests_url(json.getString("friend_requests_url"));
                        pr_Object1.setSecret(secret);
                        pr_Object1.setToken(json.getString("token"));
                        pr_Object1.setLogin_token(json.getString("login_token"));
                        login_token = json.getString("login_token");
                        token_hash = secret+json.getString("token");
                        token = json.getString("token");
                        JSONArray arr_menu = json.getJSONArray("menu");
                        listMenu = new ArrayList<MenuObject>();
                        listChild = new HashMap<MenuObject,List<OptionObject>>();
                        listMenu1 = new ArrayList<MenuObject>();
                        listChild1 = new HashMap<MenuObject,List<OptionObject>>();
                        for (int i= 0; i <arr_menu.length(); i ++){
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
                            if (item_menu.toString().contains("day_name")){
                                mn_Object.setDay_name(item_menu.getString("day_name") + "");
                                mn_Object1.setDay_name(item_menu.getString("day_name") + "");
                            }else {
                                mn_Object.setDay_name("");
                                mn_Object1.setDay_name("");
                            }

                            JSONArray arr_options = item_menu.getJSONArray("options");
                            listOptions = new ArrayList<OptionObject>();
                            listOptions1 = new ArrayList<OptionObject>();
                            for (int j = 0 ; j< arr_options.length(); j++){
                                JSONObject item_options = arr_options.getJSONObject(j);
                                OptionObject op_object = new OptionObject();
                                op_object.setTitle(item_options.getString("title"));
                                op_object.setDescription(item_options.getString("description"));
                                op_object.setMenu_type(item_options.getString("menu_type") + "");
                                if (item_options.toString().contains("name")){
                                    op_object.setName(item_options.getString("name"));
                                }
                                if (item_options.toString().contains("profile_pic_url")){
                                    op_object.setProfile_pic_url(item_options.getString("profile_pic_url"));
                                }
                                if (item_options.toString().contains("url")){
                                    op_object.setUrl(item_options.getString("url") + "");
                                }
                                if (item_options.toString().contains("food_image_url")){
                                    op_object.setFood_image_url(item_options.getString("food_image_url") + "");
                                }else{
                                    op_object.setFood_image_url("");
                                }
                                if (item_options.toString().contains("ready_date")){
                                    op_object.setReady_date(item_options.getString("ready_date") + "");
                                }
                                if (item_options.toString().contains("ready_time")){
                                    op_object.setReady_time(item_options.getString("ready_time") + "");
                                }
                                if (item_options.toString().contains("ready_timestamp")){
                                    op_object.setReady_timestamp(item_options.getString("ready_timestamp") + "");
                                }
                                if (item_options.toString().contains("ready_timestamp_tz_offset")){
                                    op_object.setReady_timestamp_tz_offset(item_options.getString("ready_timestamp_tz_offset") + "");
                                }
                                if (item_options.toString().contains("lat_long")){
                                    op_object.setLat_long(item_options.getString("lat_long") + "");
                                }
                                if (item_options.toString().contains("distance")){
                                    op_object.setDistance(item_options.getString("distance") + "");
                                }
                                if(item_options.toString().contains("distance_unit")){
                                    op_object.setDistance_unit(item_options.getString("distance_unit") + "");
                                }
                                if (item_options.toString().contains("from_now_text")){
                                    op_object.setFrom_now_text(item_options.getString("from_now_text") + "");
                                }
                                if (item_options.toString().contains("i_am_making_this")){
                                    op_object.setI_am_making_this(item_options.getString("i_am_making_this") + "");
                                }
                                if (item_options.toString().contains("can_join")){
                                    op_object.setCan_join(item_options.getString("can_join") + "");
                                }else{
                                    op_object.setCan_join("");
                                }
                                if (item_options.toString().contains("bring_container")){
                                    op_object.setBring_container(item_options.getString("bring_container") + "");
                                }
                                if (item_options.toString().contains("eating")){
                                    op_object.setEating(item_options.getString("eating") + "");
                                }
                                if (item_options.toString().contains("eating_out")){
                                    op_object.setEating_out(item_options.getString("eating_out") + "");
                                }
                                if (item_options.toString().contains("available_portions")){
                                    op_object.setAvailable_portions(item_options.getString("available_portions") + "");
                                }
                                if (item_options.toString().contains("portions_im_eating")){
                                    op_object.setPortions_im_eating(item_options.getString("portions_im_eating") + "");
                                }
                                if (item_options.toString().contains("notify_text")){
                                    op_object.setNotify_text(item_options.getString("notify_text") + "");
                                }else{
                                    op_object.setNotify_text("");
                                }
                                if (item_options.toString().contains("mutual_friend_text")){
                                    op_object.setMutual_friend_text(item_options.getString("mutual_friend_text") + "");

                                }
                                if (item_options.toString().contains("mutual_friend_html")){
                                    op_object.setMutual_friend_html(item_options.getString("mutual_friend_html") + "");
                                }
                                if (item_options.toString().contains("restaurant")){
                                    op_object.setRestaurant(item_options.getString("restaurant"));
                                }
                                if (item_options.toString().contains("restaurant_profile_pic_url")){
                                    op_object.setRestaurant_profile_pic_url(item_options.getString("restaurant_profile_pic_url"));
                                }

                                if (item_options.toString().contains("comments")){
                                    if (item_options.getString("comments").toString().length() > 0){
                                        op_object.setComments(item_options.getString("comments"));
                                    }else{
                                        op_object.setComments("0");
                                    }
                                }
                                listOptions.add(op_object);
                                if (op_object.getNotify_text().length()>0 && op_object.getI_am_making_this().equalsIgnoreCase("1")){
                                    listOptions1.add(op_object);
                                }
                            }
                            mn_Object.setOptionObjectArrayList(listOptions);
                            mn_Object1.setOptionObjectArrayList(listOptions1);
                            listMenu.add(mn_Object);
                            listChild.put(listMenu.get(i),listOptions);
                            if (listOptions1.size()>0){
                                listMenu1.add(mn_Object1);
//                            if (listOptions1.size()>0)
                                listChild1.put(listMenu1.get(i),listOptions1);
                            }

                        }
                        pr_Object.setMenuObjectArrayList(listMenu);
                        pr_Object1.setMenuObjectArrayList(listMenu1);
                        JSONObject item_user = json.getJSONObject("user");
                        us_Object1 = new UserObject();
                        us_Object1.setProfile_pic_url(item_user.getString("profile_pic_url"));
                        us_Object1.setNotification_count(item_user.getString("notification_count"));
                        us_Object1.setFriend_request_count(item_user.getString("friend_request_count"));
//                        pr_Object.set_userObjects(us_Object);
                        Log.e("GetMain","Notification Count:"+item_user.getString("notification_count"));
                        listParent.add(pr_Object);
                        listParent1.add(pr_Object1);
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
            pDialog.cancel();

            if (s.equalsIgnoreCase("Success")) {
                QTSRun.setBadge(getApplicationContext(),Integer.parseInt(us_Object1.getNotification_count()));
                QTSRun.setToken(getApplicationContext(), token);
                QTSRun.setTokenhash(getApplicationContext(), md5(token_hash));
                QTSRun.SetLogin_token(getApplicationContext(), login_token);
                _imageLoader.DisplayImage(us_Object1.getProfile_pic_url(), ivAvatar);
                ShortcutBadger.applyCount(getApplicationContext(), Integer.parseInt(us_Object1.getNotification_count()));
                if (Integer.parseInt(us_Object1.getNotification_count())>0){
                    tv_Notif.setText(""+us_Object1.getNotification_count());
                    tv_Notif.setVisibility(View.VISIBLE);
                }else{
                    tv_Notif.setVisibility(View.INVISIBLE);
                }

                listAdapter = new ExpandListAdapter(MainActivity.this,
                        listMenu, listChild);
                expListView.setAdapter(listAdapter);
                listAdapter1 = new ExpandListAdapter(MainActivity.this,
                        listMenu1, listChild1);
                expListView1.setAdapter(listAdapter1);
                if (expListView.getCount()>0){
                    for (int i = 0; i < listMenu.size(); i++) {
                        expListView.expandGroup(i);
                    }
                    expListView.setVisibility(View.VISIBLE);
                    tvNoFound.setVisibility(View.GONE);
                }else{
                    tvNoFound.setVisibility(View.VISIBLE);
                    expListView.setVisibility(View.GONE);
                    expListView1.setVisibility(View.GONE);
                }
//                if (QTSRun.isNetworkAvailable(getApplicationContext())){
//                    new SetLocation().execute();
////                    new RegistrationGCM().execute();
//                }
//                if (QTSRun.Getweb(getApplicationContext())) {
//                    QTSRun.Setweb(getApplicationContext(), false);
//                    if (QTSRun.getDestination(getApplicationContext()).toString().trim().length() != 0) {
//                        Intent intent = new Intent(MainActivity.this, WebBrowser.class);
//                        intent.putExtra("url", QTSRun.getDestination(getApplicationContext()));
//                        startActivity(intent);
//                    }
//                }
            } else {
                QTSRun.showToast(getApplicationContext(), "Login failed");
                QTSRun.setIsRegister(getApplicationContext(), false);
                Intent intent = new Intent(MainActivity.this,
                        LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
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

//    @Override
//    protected void onPause() {
//        super.onPause();
//    }

    @Override
    protected void onRestart() {
        if (!QTSRun.getIsCheck(getApplicationContext())){
//            Log.e("onRestart ","running");
            if (QTSRun.getBadge(getApplicationContext())>0){
                tv_Notif.setText(""+QTSRun.getBadge(getApplicationContext()));
                tv_Notif.setVisibility(View.VISIBLE);
            }else{
                tv_Notif.setVisibility(View.INVISIBLE);
            }

            if (QTSRun.isNetworkAvailable(getApplicationContext())){
                isTickCal = false;
                ivCalendar.setBackgroundResource(R.drawable.ic_calendar1);
                localtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(Calendar.getInstance().getTime()).toString();
                new GetData().execute();
            }else{
                QTSRun.showToast(getApplicationContext(),"Network is disconnected");
            }
        }else{
            QTSRun.setIsCheck(getApplicationContext(),false);
        }

        super.onRestart();
    }
     public void ShowDialog() {
     AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
     builder.setTitle(getResources().getString(R.string.app_name));
     builder.setMessage("Do you want to logout?").setCancelable(false)
             .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     if (QTSRun.getIsFBLogin(getApplicationContext())){
                         QTSRun.setIsFBLogin(getApplicationContext(),false);

                         LoginManager.getInstance().logOut();
                     }
                     Intent intent = new Intent(MainActivity.this,
                             LoginActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                     QTSRun.setIsRegister(getApplicationContext(),false);
                     finish();
                 }
             })
     .setNegativeButton("No", new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
             return;
         }

     });
     AlertDialog alert = builder.create();
     alert.show();
     }
    @Override
    public void onResume() {
        super.onResume();

            try {
                //        if (MainActivity.getBroadcast == 1){
                if (QTSRun.getNotifMsg(getApplicationContext())){
                    QTSRun.setNotifMsg(getApplicationContext(),false);
                    IntentFilter ifi = new IntentFilter();
                    ifi.addAction(QTSConst.ACTION_BROADCAST);
                    MainActivity.this.registerReceiver(bReceivers, ifi);
                }
//            Log.e("number baged on resum", QTSRun.getBadge(getApplication()) + ": broadcast");
//            MainActivity.getBroadcast = 0;
//        }
            }catch (Exception ex){
                ex.printStackTrace();
            }

    }

    private BroadcastReceiver bReceivers = new BroadcastReceiver() {

        @SuppressLint("LongLogTag")
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int numerBadge = arg1.getIntExtra("badge", 0);
            String url = "";
            url = arg1.getStringExtra("click_destination");
//            Log.e("number baged in broadcast",QTSRun.getBadge(getApplication())+": broadcast");
            if (numerBadge > 0){
                tv_Notif.setText(numerBadge+"");
                tv_Notif.setVisibility(View.VISIBLE);
            }else {
                if (QTSRun.getBadge(getApplication()) > 0) {
                    tv_Notif.setText(QTSRun.getBadge(getApplication()) + "");
                } else {
                    tv_Notif.setVisibility(View.INVISIBLE);
                }

            }
            if (url.toString().trim().length()!=0){
                QTSRun.setDestination(getApplicationContext(),url);
//                Intent intent = new Intent(MainActivity.this, WebBrowser.class);
//                intent.putExtra("url", url);
//                startActivity(intent);
            }
        }
    };

}
