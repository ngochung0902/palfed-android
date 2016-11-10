
package com.palfed.android.menu.activities.commonhelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author QTS
 * 
 */
public class QTSRun {
	private static ProgressDialog progressDialog;
	private static String LOG_TAG = "PalFed";
	private static boolean debug = true;
	// Flag
	public final static int FLAG_OFF = 0;
	public final static int FLAG_ON = 1;
	public static void setLayoutView(View view, int width, int height) {
		view.getLayoutParams().width = width;
		view.getLayoutParams().height = height;
	}
	public static void createDialog(Context context, String title,
			boolean isShow) {
		if (isShow) {
			if (progressDialog != null)
				progressDialog.dismiss();
			progressDialog = ProgressDialog.show(context, title, "Loading...");
		} else
			progressDialog.dismiss();
	}
	///////// Set Font
	public static void setFontTV(Context context,TextView tv, String font) {
		try {
			Typeface face = Typeface.createFromAsset(context
					.getAssets(), font);
			tv.setTypeface(face);
		} catch (Exception e) {
			Log.d("ERROR set FONTS", e.getMessage());
		}
	}
	public static void setFontEditText(Context context,EditText tv, String font) {
		try {
			Typeface face = Typeface.createFromAsset(context
					.getAssets(), font);
			tv.setTypeface(face);
		} catch (Exception e) {
			Log.d("ERROR set FONTS", e.getMessage());
		}
	}
	public static void setFontButton(Context context,Button tv, String font) {
		try {
			Typeface face = Typeface.createFromAsset(context
					.getAssets(), font);
			tv.setTypeface(face);
		} catch (Exception e) {
			Log.d("ERROR set FONTS", e.getMessage());
		}
	}
	// Show Toast
	public static void showToast(Context context, String str_message) {
		Toast toast = Toast.makeText(context, str_message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 50);
		toast.setDuration(QTSConst.TIME_WAIT);
		toast.show();
	}
	// -------- share app ---------
	public static void shareApp(Context context, String text) {
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		share.putExtra(Intent.EXTRA_SUBJECT, "Share from Prestart");
		share.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(share, "Share"));
	}

	// Send E-mail
	public static void sendFeedback(Context context, String emailAddress,
			String title) {
		final Intent emailIntent = new Intent(
				Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);

		emailIntent.putExtra(Intent.EXTRA_TEXT, "");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
		try {
			context.startActivity(Intent.createChooser(emailIntent,
					"Complete action using"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(null, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	// Share via E-mail
	public static void shareViaEmail(Context context, String content,
			String title) {
		final Intent emailIntent = new Intent(
				Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
		emailIntent.putExtra(Intent.EXTRA_TEXT, content);
		try {
			context.startActivity(Intent.createChooser(emailIntent,
					"Complete action using"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(null, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}
	}

	// Share via SMS
	public static void shareViaSMS(Context context, String content) {
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.setData(Uri.parse("sms:"));
		sendIntent.putExtra("sms_body", content);
		context.startActivity(sendIntent);
	}

	// Share via MMS
	public static void sendMMS(Context context, String patch) {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setClassName("com.android.mms",
				"com.android.mms.ui.ComposeMessageActivity");
		sendIntent.putExtra("sms_body", "Content");
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(patch));
		sendIntent.setType("image/png");
		context.startActivity(sendIntent);
	}

	// Check INTERNET connection
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Go to web page
	public static void goWebPage(Activity context, String URL) {
		Intent browse = new Intent(Intent.ACTION_VIEW);
		browse.setData(Uri.parse(URL));
		context.startActivity(browse);
	}

	// Go to Google Play store
	public static void goGooglePlayStore(Context context, String packageName) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + packageName));
			context.startActivity(intent);
		} catch (android.content.ActivityNotFoundException anfe) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri
					.parse("http://play.google.com/store/apps/details?id="
							+ packageName));
			context.startActivity(intent);
		}
	}

	// Go to Amazon appstore
	public static void goAmazonAppStore(Context context, String packageName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.amazon.com/gp/mas/dl/android?p="
				+ packageName));
		context.startActivity(intent);
	}

	// Check status password
	public static boolean Getprotectstatus(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("protect", false);
	}

	public static void Setprotectstatus(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("protect", num);
		editor.commit();
	}
	public static boolean Getweb(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isOpen", false);
	}

	public static void Setweb(Context context, Boolean isOpen) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isOpen", isOpen);
		editor.commit();
	}
	// Set password
	// /////////////////////////
	public static String Getpass(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("pass", "");
	}

	public static void Setpass(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("pass", content);
		editor.commit();
	}
	public static String GetLogin_token(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("Login_token", "");
	}

	public static void SetLogin_token(Context context, String Login_token) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("Login_token", Login_token);
		editor.commit();
	}
	public static String getSecret(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("secret", "");
	}

	public static void setSecret(Context context, String secret) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("secret", secret);
		editor.commit();
	}
	public static String getToken(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("token", "");
	}

	public static void setToken(Context context, String token) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("token", token);
		editor.commit();
	}
	public static String getTokenhash(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("tokenhash", "");
	}

	public static void setTokenhash(Context context, String tokenhash) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("tokenhash", tokenhash);
		editor.commit();
	}
	public static String getTimezone(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("timezone", "");
	}

	public static void setTimezone(Context context, String timezone) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("timezone", timezone);
		editor.commit();
	}

	// //////////////////////////
	public static Bitmap getBitmapfromURL(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) (new URL(url))
					.openConnection();
			conn.connect();
			return BitmapFactory.decodeStream(conn.getInputStream());
		} catch (Exception ex) {
			return null;
		}
	}

	// ///////////
	public static void SetRemind(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("remind", num);
		editor.commit();
	}

	public static boolean GetRemind(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("remind", false);
	}
	
	// ///////////set shake or not
	public static void setShake(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("SHAKE", num);
		editor.commit();
	}

	public static boolean getShake(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("SHAKE", false);
	}

	// //////////////////
	public static void SetNorate(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("norate", num);
		editor.commit();
	}

	public static boolean GetNorate(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("norate", false);
	}

	// ///////////////////////////
	public static int Getnum(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getInt("numrate", 0);
	}

	public static void Setnum(Context context, int num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("numrate", num);
		editor.commit();
	}

	///////////////// get push ///////////////////////
	public static int Getpush(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getInt("newpush", 0);
	}

	public static void Setpush(Context context, int push) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("newpush", push);
		editor.commit();
	}
	// /////////////////////////// Height device
	public static int GetHeightDevice(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getInt("height_device", 800);
	}

	public static void SetHeightDevice(Context context, int num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("height_device", num);
		editor.commit();
	}

	// /////////////////////////// Width device
	public static int GetWidthDevice(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getInt("width_device", 480);
	}

	public static void SetWidthDevice(Context context, int num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putInt("width_device", num);
		editor.commit();
	}

	// ///////////////////////////If this is the first time the app open
	public static void SetIsFirstTimeOpenApp(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("first_time_open", num);
		editor.commit();
	}

	public static boolean GetIsFirstTimeOpenApp(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("first_time_open", true);
	}

	// ///////////////////////////If upgrade the app to FULL version
	public static void setIsPurchasedFullVersion(Context context, Boolean num) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("is_purchased_full_version", num);
		editor.commit();
	}

	public static boolean getIsPurchasedFullVersion(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("is_purchased_full_version", false);
	}
	public static String GetPositionFail(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("positionFail", "");
	}

	public static void SetPositionFail(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("positionFail", content);
		editor.commit();
	}
	public static String GetDefectFail(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("defectFail", "");
	}

	public static void SetDefectFail(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("defectFail", content);
		editor.commit();
	}
	//// save base 64 of image
	public static String GetImagebase64(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("Imagebase64", "");
	}

	public static void SetImagebase64(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("Imagebase64", content);
		editor.commit();
	}
	// ////////////////////////////////////////////////
	// Set purchase message err
	// /////////////////////////
	public static String GetErrorMsg(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getString("purchaseerr", "");
	}

	public static void SetErrorMsg(Context context, String content) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putString("purchaseerr", content);
		editor.commit();
	}
	/////////////////// check notification ///////////////////
	public static boolean getNotifMsg(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("is_msgnew", false);
	}

	public static void setNotifMsg(Context context, Boolean status) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("is_msgnew", status);
		editor.commit();
	}
	//////////////////check email /////////////////
	public  static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}
	public static boolean checkEmailCorrect(String Email) {
        String pttn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(Email);

        if (m.matches()) {
               return true;
        }
        return false;
 }
	////////////get Date ///////////////
	public static Date getDate(String dt) {		
		try  {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.parse(dt);
		} catch (Exception e) {
			return new Date();
		}
	}
	
	public static String getDate(Date dt) {		
		try  {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			return formatter.format(dt);
		} catch (Exception e) {
			return "";
		}
	}
	//////////////////////save find everything////////////////
	public static int getBadge(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getInt("Badge", 0);
	}
	public static void setBadge(Context context, int Badge){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putInt("Badge", Badge);
		editor.commit();
	}
	public static String getFind(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("find", "");
	}
	public static void setFind(Context context, String find){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("find", find);
		editor.commit();
	}

	public static boolean getIsShow(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("IsShow", false);
	}

	public static void setIsShow(Context context, boolean IsShow) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("IsShow", IsShow);
		editor.commit();
	}
	////////////////////save form information ///////////////
	public static String getDeviceID(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("DeviceID", "");
	}
	public static void setDeviceID(Context context, String DeviceID){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("DeviceID", DeviceID);
		editor.commit();
	}
	public static String getDeviceTokenID(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("DeviceTokenID", "");
	}
	public static void setDeviceTokenID(Context context, String DeviceTokenID){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("DeviceTokenID", DeviceTokenID);
		editor.commit();
	}
	public static String getUsername(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Username", "");
	}
	public static void setUsername(Context context, String Username){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Username", Username);
		editor.commit();
	}
	public static String getUserObjectid(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Objectid", "");
	}
	public static void setUserObjectid(Context context, String Objectid){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Objectid", Objectid);
		editor.commit();
	}
	public static String getRegistrationId(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("RegistrationId", "");
	}
	public static void setRegistrationId(Context context, String RegistrationId){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("RegistrationId", RegistrationId);
		editor.commit();
	}
	public static String getReport(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Report", "");
	}
	public static void setReport(Context context, String Report){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Report", Report);
		editor.commit();
	}
	public static String getLname(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Lname", "");
	}
	public static void setLname(Context context, String lname){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Lname", lname);
		editor.commit();
	}
	
	public static String getemail(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Email", "");
	}
	public static void setemail(Context context, String email){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Email", email);
		editor.commit();
	}
	public static String getDestination(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("Destination", "");
	}
	public static void setDestination(Context context, String Destination){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("Destination", Destination);
		editor.commit();
	}

	//////////////////////////////////////
	public static String getLonglat(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("longlat", "");
	}
	public static void setLonglat(Context context, String longlat){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("longlat", longlat);
		editor.commit();
	}
	public static String getLatlong(Context context){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		return sharedPre.getString("latlong", "");
	}
	public static void setLatlong(Context context, String latlong){
		SharedPreferences sharedPre = context.getSharedPreferences(
				QTSConst.PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.putString("latlong", latlong);
		editor.commit();
	}
	// /////////////////////////
		public static boolean getIsRegister(Context context) {
			int mode = Activity.MODE_PRIVATE;
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					QTSConst.PREFERENCES, mode);
			return sharedPreferences.getBoolean("isRegister", false);
		}

		public static void setIsRegister(Context context, boolean isRegister) {
			SharedPreferences sharedPreferences = context.getSharedPreferences(
					QTSConst.PREFERENCES, 0);
			Editor editor = sharedPreferences.edit();
			editor.putBoolean("isRegister", isRegister);
			editor.commit();
		}
		// /////////////////////////
				public static boolean getIsCheck(Context context) {
					int mode = Activity.MODE_PRIVATE;
					SharedPreferences sharedPreferences = context.getSharedPreferences(
							QTSConst.PREFERENCES, mode);
					return sharedPreferences.getBoolean("ischeck", false);
				}

				public static void setIsCheck(Context context, boolean ischeck) {
					SharedPreferences sharedPreferences = context.getSharedPreferences(
							QTSConst.PREFERENCES, 0);
					Editor editor = sharedPreferences.edit();
					editor.putBoolean("ischeck", ischeck);
					editor.commit();
				}
	//===========login with facebook ==========
	public static boolean getIsFBLogin(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("islogin", false);
	}

	public static void setIsFBLogin(Context context, boolean islogin) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("islogin", islogin);
		editor.commit();
	}
	public static boolean getIsService(Context context) {
		int mode = Activity.MODE_PRIVATE;
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, mode);
		return sharedPreferences.getBoolean("isService", false);
	}

	public static void setIsService(Context context, boolean isService) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				QTSConst.PREFERENCES, 0);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean("isService", isService);
		editor.commit();
	}
}
