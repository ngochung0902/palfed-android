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

    public NavMenuObject( String action, String title) {
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


    public NavMenuObject() {
    }
}
