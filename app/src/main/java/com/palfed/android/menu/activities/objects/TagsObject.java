package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 11/24/2016.
 */

public class TagsObject implements Parcelable {
    String tag;
    String type;
    int mcolor;

    public void setMcolor(int mcolor) {
        this.mcolor = mcolor;
    }

    public int getMcolor() {
        return mcolor;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tag);
        parcel.writeString(type);
        parcel.writeInt(mcolor);
    }

    public TagsObject() {
    }

    public TagsObject(Parcel parcel) {
        tag = parcel.readString();
        type = parcel.readString();
        mcolor = parcel.readInt();
    }

    public static final Parcelable.Creator<TagsObject> CREATOR = new Parcelable.Creator<TagsObject>() {
        public TagsObject createFromParcel(Parcel in) {
            return new TagsObject(in);
        }

        public TagsObject[] newArray(int size) {
            return new TagsObject[size];
        }
    };
}
