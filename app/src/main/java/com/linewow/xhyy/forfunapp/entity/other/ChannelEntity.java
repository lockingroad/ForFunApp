package com.linewow.xhyy.forfunapp.entity.other;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LXR on 2017/1/17.
 */

public class ChannelEntity implements Parcelable {
    private String type;
    private String id;
    private String title;

    public ChannelEntity(String type, String id, String title) {
        this.type = type;
        this.id = id;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.title);
    }

    protected ChannelEntity(Parcel in) {
        this.type = in.readString();
        this.id = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<ChannelEntity> CREATOR = new Parcelable.Creator<ChannelEntity>() {
        @Override
        public ChannelEntity createFromParcel(Parcel source) {
            return new ChannelEntity(source);
        }

        @Override
        public ChannelEntity[] newArray(int size) {
            return new ChannelEntity[size];
        }
    };
}
