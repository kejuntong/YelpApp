package com.kejuntong.yelpapp.model.data;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

public class Center implements Serializable
{
    @JsonGetter("latitude")
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    double latitude;

    @JsonGetter("longitude")
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    double longitude;
}
