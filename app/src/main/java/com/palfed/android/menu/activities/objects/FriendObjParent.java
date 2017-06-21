package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/23/2016.
 */

public class FriendObjParent implements Parcelable {
     String mTitle;
     List<FrRequestObj> mArrayChildren;
     int isRequest;

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmArrayChildren(List<FrRequestObj> mArrayChildren) {
        this.mArrayChildren = mArrayChildren;
    }

    public List<FrRequestObj> getmArrayChildren() {
        return mArrayChildren;
    }

    public void setIsRequest(int isRequest) {
        this.isRequest = isRequest;
    }

    public int getIsRequest() {
        return isRequest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeList(mArrayChildren);
        parcel.writeInt(isRequest);

    }

    public FriendObjParent() {
    }

    public FriendObjParent(Parcel parcel) {
        mTitle = parcel.readString();
        mArrayChildren = new ArrayList<FrRequestObj>();
        mArrayChildren = parcel.readArrayList(FrRequestObj.class.getClassLoader());
        isRequest = parcel.readInt();
    }

    public static final Parcelable.Creator<FriendObjParent> CREATOR = new Parcelable.Creator<FriendObjParent>() {
        public FriendObjParent createFromParcel(Parcel in) {
            return new FriendObjParent(in);
        }

        public FriendObjParent[] newArray(int size) {
            return new FriendObjParent[size];
        }
    };
}
