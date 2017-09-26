package com.palfed.android.menu.activities.objects;

/**
 * Created by MyPC on 25/09/2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang.builder.ToStringBuilder;

public class NavMenuObject implements Parcelable {
    int id;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("title")
    @Expose
    private String title;

    public NavMenuObject(Parcel in) {
        action = in.readString();
        title = in.readString();
    }

    public NavMenuObject(int id, String action, String title) {
        this.id = id;
        this.action = action;
        this.title = title;
    }

    public static final Creator<NavMenuObject> CREATOR = new Creator<NavMenuObject>() {
        @Override
        public NavMenuObject createFromParcel(Parcel in) {
            return new NavMenuObject(in);
        }

        @Override
        public NavMenuObject[] newArray(int size) {
            return new NavMenuObject[size];
        }
    };

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeString(title);
    }

    @Override
    public String toString() {
        return "NavMenuObject{" +
                "action='" + action + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NavMenuObject() {
    }

    //    String title;
//
//    public NavMenuObject(String title) {
//        this.title = title;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    @SerializedName("https://design.palfed.com/")
//    @Expose
//    private String httpsDesignPalfedCom;
//    @SerializedName("https://design.palfed.com/food/add")
//    @Expose
//    private String httpsDesignPalfedComFoodAdd;
//    @SerializedName("https://design.palfed.com/friends/invite")
//    @Expose
//    private String httpsDesignPalfedComFriendsInvite;
//    @SerializedName("https://design.palfed.com/friends")
//    @Expose
//    private String httpsDesignPalfedComFriends;
//    @SerializedName("https://design.palfed.com/account")
//    @Expose
//    private String httpsDesignPalfedComAccount;
//    @SerializedName("https://design.palfed.com/notifications")
//    @Expose
//    private String httpsDesignPalfedComNotifications;
//    @SerializedName("https://design.palfed.com/leaderboard")
//    @Expose
//    private String httpsDesignPalfedComLeaderboard;
//    @SerializedName("app:only-show-eating")
//    @Expose
//    private String appOnlyShowEating;
//    @SerializedName("app:logout")
//    @Expose
//    private String appLogout;
//    @SerializedName("app:close-menu")
//    @Expose
//    private String appCloseMenu;
//
//    public NavMenuObject() {
//    }
//
//    public NavMenuObject(Parcel in) {
//        httpsDesignPalfedCom = in.readString();
//        httpsDesignPalfedComFoodAdd = in.readString();
//        httpsDesignPalfedComFriendsInvite = in.readString();
//        httpsDesignPalfedComFriends = in.readString();
//        httpsDesignPalfedComAccount = in.readString();
//        httpsDesignPalfedComNotifications = in.readString();
//        httpsDesignPalfedComLeaderboard = in.readString();
//        appOnlyShowEating = in.readString();
//        appLogout = in.readString();
//        appCloseMenu = in.readString();
//    }
//
//    public static final Creator<NavMenuObject> CREATOR = new Creator<NavMenuObject>() {
//        @Override
//        public NavMenuObject createFromParcel(Parcel in) {
//            return new NavMenuObject(in);
//        }
//
//        @Override
//        public NavMenuObject[] newArray(int size) {
//            return new NavMenuObject[size];
//        }
//    };
//
//    public String getHttpsDesignPalfedCom() {
//        return httpsDesignPalfedCom;
//    }
//
//    public void setHttpsDesignPalfedCom(String httpsDesignPalfedCom) {
//        this.httpsDesignPalfedCom = httpsDesignPalfedCom;
//    }
//
//    public String getHttpsDesignPalfedComFoodAdd() {
//        return httpsDesignPalfedComFoodAdd;
//    }
//
//    public void setHttpsDesignPalfedComFoodAdd(String httpsDesignPalfedComFoodAdd) {
//        this.httpsDesignPalfedComFoodAdd = httpsDesignPalfedComFoodAdd;
//    }
//
//    public String getHttpsDesignPalfedComFriendsInvite() {
//        return httpsDesignPalfedComFriendsInvite;
//    }
//
//    public void setHttpsDesignPalfedComFriendsInvite(String httpsDesignPalfedComFriendsInvite) {
//        this.httpsDesignPalfedComFriendsInvite = httpsDesignPalfedComFriendsInvite;
//    }
//
//    public String getHttpsDesignPalfedComFriends() {
//        return httpsDesignPalfedComFriends;
//    }
//
//    public void setHttpsDesignPalfedComFriends(String httpsDesignPalfedComFriends) {
//        this.httpsDesignPalfedComFriends = httpsDesignPalfedComFriends;
//    }
//
//    public String getHttpsDesignPalfedComAccount() {
//        return httpsDesignPalfedComAccount;
//    }
//
//    public void setHttpsDesignPalfedComAccount(String httpsDesignPalfedComAccount) {
//        this.httpsDesignPalfedComAccount = httpsDesignPalfedComAccount;
//    }
//
//    public String getHttpsDesignPalfedComNotifications() {
//        return httpsDesignPalfedComNotifications;
//    }
//
//    public void setHttpsDesignPalfedComNotifications(String httpsDesignPalfedComNotifications) {
//        this.httpsDesignPalfedComNotifications = httpsDesignPalfedComNotifications;
//    }
//
//    public String getHttpsDesignPalfedComLeaderboard() {
//        return httpsDesignPalfedComLeaderboard;
//    }
//
//    public void setHttpsDesignPalfedComLeaderboard(String httpsDesignPalfedComLeaderboard) {
//        this.httpsDesignPalfedComLeaderboard = httpsDesignPalfedComLeaderboard;
//    }
//
//    public String getAppOnlyShowEating() {
//        return appOnlyShowEating;
//    }
//
//    public void setAppOnlyShowEating(String appOnlyShowEating) {
//        this.appOnlyShowEating = appOnlyShowEating;
//    }
//
//    public String getAppLogout() {
//        return appLogout;
//    }
//
//    public void setAppLogout(String appLogout) {
//        this.appLogout = appLogout;
//    }
//
//    public String getAppCloseMenu() {
//        return appCloseMenu;
//    }
//
//    public void setAppCloseMenu(String appCloseMenu) {
//        this.appCloseMenu = appCloseMenu;
//    }
//
//    @Override
//    public String toString() {
//        return "NavMenuObject{" +
//                "httpsDesignPalfedCom='" + httpsDesignPalfedCom + '\'' +
//                ", httpsDesignPalfedComFoodAdd='" + httpsDesignPalfedComFoodAdd + '\'' +
//                ", httpsDesignPalfedComFriendsInvite='" + httpsDesignPalfedComFriendsInvite + '\'' +
//                ", httpsDesignPalfedComFriends='" + httpsDesignPalfedComFriends + '\'' +
//                ", httpsDesignPalfedComAccount='" + httpsDesignPalfedComAccount + '\'' +
//                ", httpsDesignPalfedComNotifications='" + httpsDesignPalfedComNotifications + '\'' +
//                ", httpsDesignPalfedComLeaderboard='" + httpsDesignPalfedComLeaderboard + '\'' +
//                ", appOnlyShowEating='" + appOnlyShowEating + '\'' +
//                ", appLogout='" + appLogout + '\'' +
//                ", appCloseMenu='" + appCloseMenu + '\'' +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int flags) {
//        parcel.writeString(httpsDesignPalfedCom);
//        parcel.writeString(httpsDesignPalfedComFoodAdd);
//        parcel.writeString(httpsDesignPalfedComFriendsInvite);
//        parcel.writeString(httpsDesignPalfedComFriends);
//        parcel.writeString(httpsDesignPalfedComAccount);
//        parcel.writeString(httpsDesignPalfedComNotifications);
//        parcel.writeString(httpsDesignPalfedComLeaderboard);
//        parcel.writeString(appOnlyShowEating);
//        parcel.writeString(appLogout);
//        parcel.writeString(appCloseMenu);
//    }
}
