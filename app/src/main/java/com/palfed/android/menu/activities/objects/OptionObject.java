package com.palfed.android.menu.activities.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android QTS on 1/5/2016.
 */
public class OptionObject implements Parcelable {
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("name")
    String name;
    @SerializedName("profile_pic_url")
    String profile_pic_url;
    @SerializedName("ready_date")
    String ready_date;
    @SerializedName("ready_time")
    String ready_time;
    @SerializedName("ready_timestamp")
    String ready_timestamp;
    @SerializedName("ready_timestamp_tz_offset")
    String ready_timestamp_tz_offset;
    @SerializedName("from_now_text")
    String from_now_text;
    @SerializedName("url")
    String url;
    @SerializedName("i_am_making_this")
    String i_am_making_this;
    @SerializedName("distance")
    String distance;
    @SerializedName("distance_unit")
    String distance_unit;
    @SerializedName("lat_long")
    String lat_long;
    @SerializedName("title")
    String can_join;
    @SerializedName("bring_container")
    String bring_container;
    @SerializedName("eating_out")
    String eating_out;
    @SerializedName("available_portions")
    String available_portions;
    @SerializedName("eating")
    String eating;
    @SerializedName("portions_im_eating")
    String portions_im_eating;
    @SerializedName("notify_text")
    String notify_text;
    @SerializedName("menu_type")
    String menu_type;
    @SerializedName("comments")
    String comments;
    @SerializedName("mutual_friend_text")
    String mutual_friend_text;
    @SerializedName("mutual_friend_html")
    String mutual_friend_html;
    @SerializedName("restaurant")
    String restaurant;
    @SerializedName("restaurant_profile_pic_url")
    String restaurant_profile_pic_url;
    @SerializedName("food_image_url")
    String food_image_url;
    @SerializedName("tags")
    List<TagsObject> tags;

    public void setTags(List<TagsObject> tags) {
        this.tags = tags;
    }

    public List<TagsObject> getTags() {
        return tags;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setRestaurant_profile_pic_url(String restaurant_profile_pic_url) {
        this.restaurant_profile_pic_url = restaurant_profile_pic_url;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getRestaurant_profile_pic_url() {
        return restaurant_profile_pic_url;
    }

    public void setMutual_friend_html(String mutual_friend_html) {
        this.mutual_friend_html = mutual_friend_html;
    }

    public String getMutual_friend_html() {
        return mutual_friend_html;
    }

    public void setMutual_friend_text(String mutual_friend_text) {
        this.mutual_friend_text = mutual_friend_text;
    }

    public String getMutual_friend_text() {
        return mutual_friend_text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public void setReady_date(String ready_date) {
        this.ready_date = ready_date;
    }

    public void setReady_time(String ready_time) {
        this.ready_time = ready_time;
    }

    public void setReady_timestamp(String ready_timestamp) {
        this.ready_timestamp = ready_timestamp;
    }

    public void setReady_timestamp_tz_offset(String ready_timestamp_tz_offset) {
        this.ready_timestamp_tz_offset = ready_timestamp_tz_offset;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDistance_unit(String distance_unit) {
        this.distance_unit = distance_unit;
    }

    public void setFrom_now_text(String from_now_text) {
        this.from_now_text = from_now_text;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setI_am_making_this(String i_am_making_this) {
        this.i_am_making_this = i_am_making_this;
    }

    public void setPortions_im_eating(String portions_im_eating) {
        this.portions_im_eating = portions_im_eating;
    }

    public void setAvailable_portions(String available_portions) {
        this.available_portions = available_portions;
    }

    public void setBring_container(String bring_container) {
        this.bring_container = bring_container;
    }

    public void setCan_join(String can_join) {
        this.can_join = can_join;
    }

    public void setEating(String eating) {
        this.eating = eating;
    }

    public void setEating_out(String eating_out) {
        this.eating_out = eating_out;
    }

    public void setLat_long(String lat_long) {
        this.lat_long = lat_long;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public void setNotify_text(String notify_text) {
        this.notify_text = notify_text;
    }

    public String getReady_timestamp_tz_offset() {
        return ready_timestamp_tz_offset;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public String getI_am_making_this() {
        return i_am_making_this;
    }

    public String getAvailable_portions() {
        return available_portions;
    }

    public String getBring_container() {
        return bring_container;
    }

    public String getCan_join() {
        return can_join;
    }

    public String getDistance() {
        return distance;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public String getEating() {
        return eating;
    }

    public String getEating_out() {
        return eating_out;
    }

    public String getFrom_now_text() {
        return from_now_text;
    }

    public String getLat_long() {
        return lat_long;
    }

    public String getNotify_text() {
        return notify_text;
    }

    public String getPortions_im_eating() {
        return portions_im_eating;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public String getReady_date() {
        return ready_date;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public String getReady_time() {
        return ready_time;
    }

    public String getReady_timestamp() {
        return ready_timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setFood_image_url(String food_image_url) {
        this.food_image_url = food_image_url;
    }

    public String getFood_image_url() {
        return food_image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeString(profile_pic_url);
        parcel.writeString(ready_date);
        parcel.writeString(ready_time);
        parcel.writeString(ready_timestamp);
        parcel.writeString(ready_timestamp_tz_offset);
        parcel.writeString(from_now_text);
        parcel.writeString(url);
        parcel.writeString(i_am_making_this);
        parcel.writeString(distance);
        parcel.writeString(distance_unit);
        parcel.writeString(lat_long);
        parcel.writeString(can_join);
        parcel.writeString(bring_container);
        parcel.writeString(eating_out);
        parcel.writeString(available_portions);
        parcel.writeString(eating);
        parcel.writeString(portions_im_eating);
        parcel.writeString(notify_text);
        parcel.writeString(comments);
        parcel.writeString(menu_type);
        parcel.writeString(mutual_friend_text);
        parcel.writeString(mutual_friend_html);
        parcel.writeString(restaurant);
        parcel.writeString(restaurant_profile_pic_url);
        parcel.writeString(food_image_url);
        parcel.writeList(tags);
    }

    public OptionObject() {
    }

    public OptionObject(Parcel parcel) {
        title = parcel.readString();
        description = parcel.readString();
        name = parcel.readString();
        profile_pic_url = parcel.readString();
        ready_date = parcel.readString();
        ready_time = parcel.readString();
        ready_timestamp = parcel.readString();
        ready_timestamp_tz_offset = parcel.readString();
        from_now_text = parcel.readString();
        url = parcel.readString();
        i_am_making_this = parcel.readString();
        distance = parcel.readString();
        distance_unit = parcel.readString();
        lat_long = parcel.readString();
        can_join = parcel.readString();
        bring_container = parcel.readString();
        eating_out = parcel.readString();
        available_portions = parcel.readString();
        eating = parcel.readString();
        portions_im_eating = parcel.readString();
        notify_text = parcel.readString();
        comments = parcel.readString();
        menu_type = parcel.readString();
        mutual_friend_text = parcel.readString();
        mutual_friend_html = parcel.readString();
        restaurant = parcel.readString();
        restaurant_profile_pic_url = parcel.readString();
        food_image_url = parcel.readString();
        tags = new ArrayList<TagsObject>();
        tags = parcel.readArrayList(TagsObject.class.getClassLoader());
    }

    public static final Parcelable.Creator<OptionObject> CREATOR = new Parcelable.Creator<OptionObject>() {
        public OptionObject createFromParcel(Parcel in) {
            return new OptionObject(in);
        }

        public OptionObject[] newArray(int size) {
            return new OptionObject[size];
        }
    };
}
