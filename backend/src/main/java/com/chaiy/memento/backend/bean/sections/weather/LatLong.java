package com.chaiy.memento.backend.bean.sections.weather;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class LatLong {

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
}
