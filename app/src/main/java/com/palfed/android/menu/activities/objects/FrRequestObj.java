package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 11/23/2016.
 */

public class FrRequestObj implements Parcelable{
    String name;
    String picture_url;
    String fr_id;
    String guid;
    String request_html;
//    int isRequest;


//    public void setIsRequest(int isRequest) {
//        this.isRequest = isRequest;
//    }
//
//    public int getIsRequest() {
//        return isRequest;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setFr_id(String fr_id) {
        this.fr_id = fr_id;
    }

    public String getFr_id() {
        return fr_id;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public void setRequest_html(String request_html) {
        this.request_html = request_html;
    }

    public String getRequest_html() {
        return request_html;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(picture_url);
        parcel.writeString(fr_id);
        parcel.writeString(guid);
        parcel.writeString(request_html);
//        parcel.writeInt(isRequest);
    }

    public FrRequestObj() {
    }

    public FrRequestObj(Parcel parcel) {
        name = parcel.readString();
        picture_url = parcel.readString();
        fr_id = parcel.readString();
        guid = parcel.readString();
        request_html = parcel.readString();
//        isRequest = parcel.readInt();
    }

    public static final Parcelable.Creator<FrRequestObj> CREATOR = new Parcelable.Creator<FrRequestObj>() {
        public FrRequestObj createFromParcel(Parcel in) {
            return new FrRequestObj(in);
        }

        public FrRequestObj[] newArray(int size) {
            return new FrRequestObj[size];
        }
    };
}
