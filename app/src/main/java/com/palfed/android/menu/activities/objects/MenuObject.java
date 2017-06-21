package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android QTS on 1/5/2016.
 */
public class MenuObject implements Parcelable {
    @SerializedName("ts")
    String ts;
    @SerializedName("day")
    String day;
    @SerializedName("ymd")
    String ymd;
    @SerializedName("day_no_timezone")
    String day_no_timezone;
    @SerializedName("day_no_year")
    String day_no_year;
    @SerializedName("day_name")
    String day_name;
    @SerializedName("message_text")
    String message_text;
    @SerializedName("message_html")
    String message_html;
    @SerializedName("options")
    List<OptionObject> optionObjectArrayList;

    public void setOptionObjectArrayList(List<OptionObject> optionObjectArrayList) {
        this.optionObjectArrayList =  optionObjectArrayList;
    }

    public List<OptionObject> getOptionObjectArrayList() {
        return optionObjectArrayList;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getTs() {
        return ts;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_no_year(String day_no_year) {
        this.day_no_year = day_no_year;
    }

    public String getDay_no_year() {
        return day_no_year;
    }

    public void setMessage_html(String message_html) {
        this.message_html = message_html;
    }

    public String getMessage_html() {
        return message_html;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getYmd() {
        return ymd;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ts);
        parcel.writeString(day);
        parcel.writeString(day_name);
        parcel.writeString(day_no_year);
        parcel.writeString(ymd);
        parcel.writeString(message_text);
        parcel.writeString(message_html);
        parcel.writeList(optionObjectArrayList);
    }
    public MenuObject(){}
    public MenuObject(Parcel parcel){
        ts = parcel.readString();
        day = parcel.readString();
        day_name = parcel.readString();
        day_no_year = parcel.readString();
        ymd = parcel.readString();
        message_text = parcel.readString();
        message_html = parcel.readString();
        optionObjectArrayList = new ArrayList<OptionObject>();
        optionObjectArrayList = parcel.readArrayList(OptionObject.class.getClassLoader());
    }
    public static final Parcelable.Creator<MenuObject> CREATOR = new Parcelable.Creator<MenuObject>() {
        public MenuObject createFromParcel(Parcel in) {
            return new MenuObject(in);
        }

        public MenuObject[] newArray(int size) {
            return new MenuObject[size];
        }
    };
}
