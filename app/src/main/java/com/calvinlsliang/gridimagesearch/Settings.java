package com.calvinlsliang.gridimagesearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cliang on 11/1/15.
 */
public class Settings implements Parcelable {
    private String imageSize;
    private String colorFilter;
    private String imageType;
    private String siteFilter;

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public Settings(String imageSize, String colorFilter, String imageType, String siteFilter) {
        this.imageSize = imageSize;
        this.colorFilter = colorFilter;
        this.imageType = imageType;
        this.siteFilter = siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    public Settings() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSize);
        dest.writeString(this.colorFilter);
        dest.writeString(this.imageType);
        dest.writeString(this.siteFilter);
    }

    protected Settings(Parcel in) {
        this.imageSize = in.readString();
        this.colorFilter = in.readString();
        this.imageType = in.readString();
        this.siteFilter = in.readString();
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        public Settings createFromParcel(Parcel source) {
            return new Settings(source);
        }

        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}
