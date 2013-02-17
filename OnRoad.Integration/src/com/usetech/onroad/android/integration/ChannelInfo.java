package com.usetech.onroad.android.integration;

import android.os.Parcel;
import android.os.Parcelable;

public final class ChannelInfo implements Parcelable {
    private int id;
    private String name;

    public static final Parcelable.Creator<ChannelInfo> CREATOR = new
            Parcelable.Creator<ChannelInfo>() {
                public ChannelInfo createFromParcel(Parcel in) {
                    return new ChannelInfo(in);
                }

                public ChannelInfo[] newArray(int size) {
                    return new ChannelInfo[size];
                }
            };

    public ChannelInfo() {
    }

    private ChannelInfo(Parcel in) {
        readFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
    }

    private void readFromParcel(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
    }


}
