package com.calvinlsliang.gridimagesearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cliang on 10/30/15.
 */
public class Image implements Parcelable {
    public String tbUrl;
    public String url;
    public String title;

    public Image(String tbUrl, String url, String title) {
        this.tbUrl = tbUrl;
        this.url = url;
        this.title = title;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    public void setUrl(String url) {
        this.url = url;
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
        dest.writeString(this.tbUrl);
        dest.writeString(this.url);
        dest.writeString(this.title);
    }

    protected Image(Parcel in) {
        this.tbUrl = in.readString();
        this.url = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
