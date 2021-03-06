package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android QTS on 1/5/2016.
 */
public class ParentObject implements Parcelable {
    @SerializedName("status")
    String status;
    @SerializedName("base_url")
    String base_url;
    @SerializedName("notifications_url")
    String notifications_url;
    @SerializedName("friend_requests_url")
    String friend_requests_url;
    @SerializedName("secret")
    String secret;
    @SerializedName("token")
    String token;
    @SerializedName("login_token")
    String login_token;
    @SerializedName("menu")
    List<MenuObject> menuObjectArrayList;
    @SerializedName("user")
    UserObject user;
    @SerializedName("friend_suggestions")
    List<FrRequestObj> friend_suggestions;
    @SerializedName("friend_requests")
    List<FriendObjParent> friend_requests;

    public void setFriend_suggestions(List<FrRequestObj> friend_suggestions) {
        this.friend_suggestions = friend_suggestions;
    }

    public List<FriendObjParent> getFriend_requests() {
        return friend_requests;
    }

    public void setFriend_requests(List<FriendObjParent> friend_requests) {
        this.friend_requests = friend_requests;
    }

    public List<FrRequestObj> getFriend_suggestions() {
        return friend_suggestions;
    }

    public UserObject getUser() {
        return user;
    }

    public void setUser(UserObject user) {
        this.user = user;
    }
    //    UserObject _userObjects ;

    public void setMenuObjectArrayList(List<MenuObject> menuObjectArrayList) {
        this.menuObjectArrayList = menuObjectArrayList;
    }

    public List<MenuObject> getMenuObjectArrayList() {
        return menuObjectArrayList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setNotifications_url(String notifications_url) {
        this.notifications_url = notifications_url;
    }

    public String getNotifications_url() {
        return notifications_url;
    }

    public void setFriend_requests_url(String friend_requests_url) {
        this.friend_requests_url = friend_requests_url;
    }

    public String getFriend_requests_url() {
        return friend_requests_url;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    public String getLogin_token() {
        return login_token;
    }


//    public void set_userObjects(UserObject _userObjects) {
//        this._userObjects = _userObjects;
//    }
//
//    public UserObject get_userObjects() {
//        return _userObjects;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(base_url);
        parcel.writeString(notifications_url);
        parcel.writeString(friend_requests_url);
        parcel.writeString(secret);
        parcel.writeString(token);
        parcel.writeString(login_token);
//        parcel.writeParcelable((UserObject) _userObjects, i);
        parcel.writeList(menuObjectArrayList);
    }
    public ParentObject(){}
    public ParentObject(Parcel parcel){
        status = parcel.readString();
        base_url = parcel.readString();
        notifications_url = parcel.readString();
        friend_requests_url = parcel.readString();
        secret = parcel.readString();
        token = parcel.readString();
        login_token = parcel.readString();
//        _userObjects = parcel.readParcelable(_userObjects.getClass().getClassLoader());
        menuObjectArrayList = new ArrayList<MenuObject>();
        //ERROR -> list = (ArrayList<IA>) in.readSerializable
        menuObjectArrayList = parcel.readArrayList(MenuObject.class.getClassLoader());
//        parcel.readList(menuObjectArrayList,null);
    }
    public static final Parcelable.Creator<ParentObject> CREATOR = new Parcelable.Creator<ParentObject>() {
        public ParentObject createFromParcel(Parcel in) {
            return new ParentObject(in);
        }

        public ParentObject[] newArray(int size) {
            return new ParentObject[size];
        }
    };
}
