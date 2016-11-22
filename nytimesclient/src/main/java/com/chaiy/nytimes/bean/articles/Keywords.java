package com.chaiy.nytimes.bean.articles;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class Keywords {

    @SerializedName("rank")
    String rank;

    @SerializedName("is_major")
    String isMajor;

    @SerializedName("name")
    String name;

    @SerializedName("value")
    String value;

    public String getRank() {
        return rank;
    }

    public String getIsMajor() {
        return isMajor;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
