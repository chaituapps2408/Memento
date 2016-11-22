package com.chaiy.memento.model.sections.weather;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class LatLong implements Parcelable {

    private final String latitude;
    private final String longitude;

    public LatLong(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected LatLong(Parcel in) {
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator<LatLong> CREATOR = new Parcelable.Creator<LatLong>() {
        @Override
        public LatLong createFromParcel(Parcel source) {
            return new LatLong(source);
        }

        @Override
        public LatLong[] newArray(int size) {
            return new LatLong[size];
        }
    };
}
