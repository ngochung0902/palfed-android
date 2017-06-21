package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android QTS on 1/5/2016.
 */
public class UserObject implements Parcelable{
    @SerializedName("friend_request_count")
    String friend_request_count;
    @SerializedName("notification_count")
    String notification_count;
    @SerializedName("profile_pic_url")
    String profile_pic_url;

    public void setFriend_request_count(String friend_request_count) {
        this.friend_request_count = friend_request_count;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setNotification_count(String notification_count) {
        this.notification_count = notification_count;
    }

    public String getFriend_request_count() {
        return friend_request_count;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getNotification_count() {
        return notification_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(friend_request_count);
        parcel.writeString(notification_count);
        parcel.writeString(profile_pic_url);
    }
    public static final Parcelable.Creator<UserObject> CREATOR = new Parcelable.Creator<UserObject>() {
        public UserObject createFromParcel(Parcel in) {
            return new UserObject(in);
        }

        public UserObject[] newArray(int size) {
            return new UserObject[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    public UserObject(){}
    public UserObject(Parcel in) {
        friend_request_count = in.readString();
        notification_count = in.readString();
        profile_pic_url = in.readString();
    }
}
