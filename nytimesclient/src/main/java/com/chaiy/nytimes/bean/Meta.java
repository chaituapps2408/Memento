package com.chaiy.nytimes.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class Meta {

    @SerializedName("code")
    int code;

    @SerializedName("msg")
    String msg;

    @SerializedName("hits")
    int hits;

    @SerializedName("time")
    String time;

    @SerializedName("offset")
    String offset;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public int getHits() {
        return hits;
    }

    public String getTime() {
        return time;
    }

    public String getOffset() {
        return offset;
    }
}
