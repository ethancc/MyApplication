package com.example.ethan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ethamhuang on 2015/7/30.
 */
public class UserInfo implements Parcelable {

    private int uin = 0;
    private String name = "";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uin);
        dest.writeString(this.name);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.uin = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
