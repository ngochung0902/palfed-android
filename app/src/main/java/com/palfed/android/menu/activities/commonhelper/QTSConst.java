/**
 * Project    : Tingle
 * Author     : TuanCuong
 * Date        : Dec 8, 2012
 **/
package com.palfed.android.menu.activities.commonhelper;

import com.palfed.android.menu.activities.objects.LVNav;
import com.palfed.android.menu.activities.objects.NavMenuObject;

import java.util.ArrayList;

/**
 * @author QTS
 * 
 */
public class QTSConst {

	public static boolean checklogin = false;
	public static boolean closewebview = false;
	public static ArrayList<LVNav> arrtitle = new ArrayList<LVNav>();
	public static ArrayList<NavMenuObject> arrListaction = new ArrayList<NavMenuObject>();

	public static ArrayList<LVNav> arr = new ArrayList<LVNav>();
	public static ArrayList<NavMenuObject> arrList = null;
	public static int iTab = 0;
	public static int TIME_WAIT = 6;
	public static String PREFERENCES = "PALFED";

	public static String ACTION_BROADCAST = "com.palfed.USER_ACTION";
	public static String ACTION_BROADCAST_WEB = "com.palfed.web";
	public static String ACTION_BROADCAST_WEB_NOTIF = "com.palfed.webnotification";
	// Fonts
	public static String FONT_ROBOTOSLAB_LIGHT = "fonts/RobotoSlab-Light.ttf";
	public static String FONT_ROBOTOSLAB_REGULAR = "fonts/RobotoSlab-Regular.ttf";
	public static String FONT_ROBOTOSLAB_BOLD = "fonts/RobotoSlab-Bold.ttf";
	public static String FONT_ROBOTOSLAB_THIN = "fonts/RobotoSlab-Thin.ttf";
	public static String FONT_ARBUTUSSLAB_REGULAR = "fonts/ArbutusSlab-Regular.ttf";

	//DEVELOPMENT
//	public static String URL_MAKE = "https://design.palfed.com/food/add";
//	public static String URL_FRIEND_FINDER = "https://design.palfed.com/friends/finder";
//	public static String URL_INVITE_FRIENDS = "https://design.palfed.com/friends/invite";
//	public static String URL_GET_MENU = "https://design.palfed.com/api/get-menu";
//	public static String URL_LOGIN = "https://design.palfed.com/api/login";
//	public static String URL_SETLOCATION = "https://design.palfed.com/api/set-location";
//	public static String URL_REGISTRATION = "https://design.palfed.com/api/set-android-registration-id?registration_id";
// 	public static String URL_FORGOTTEN_PASSWORD = "https://www.palfed.com/login/forgotten-password";
//	public static String URL_LOGINTOKEN = "https://design.palfed.com/api/get-login-token";
//	public static String URL_FRIEND_REQUEST = "https://design.palfed.com/api/friend-request-response";
//	public static String BASE_URL = "https://design.palfed.com/";

//	//DISTRIBUTION
	public static String URL_MAKE = "https://www.palfed.com/food/add";
	public static String URL_FRIEND_FINDER = "https://www.palfed.com/friends/finder";
	public static String URL_INVITE_FRIENDS = "https://www.palfed.com/friends/invite";
	public static String URL_GET_MENU = "https://www.palfed.com/api/get-menu";
	public static String URL_LOGIN = "https://www.palfed.com/api/login";
	public static String URL_SETLOCATION = "https://www.palfed.com/api/set-location";
	public static String URL_REGISTRATION = "https://www.palfed.com/api/set-android-registration-id?registration_id";
	public static String URL_FORGOTTEN_PASSWORD = "https://www.palfed.com/login/forgotten-password";
	public static String URL_LOGINTOKEN = "https://www.palfed.com/api/get-login-token";
	public static String URL_FRIEND_REQUEST = "https://www.palfed.com/api/friend-request-response";
  	public static String BASE_URL = "https://www.palfed.com/";
}
