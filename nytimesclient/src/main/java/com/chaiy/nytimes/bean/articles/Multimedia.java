package com.chaiy.nytimes.bean.articles;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class Multimedia {

    @SerializedName("width")
    String width;

    @SerializedName("url")
    String url;

    @SerializedName("height")
    String height;

    @SerializedName("subtype")
    String subtype;

    public String getType() {
        return type;
    }

    @SerializedName("type")
    String type;

    public String getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }

    public String getHeight() {
        return height;
    }

    public String getSubtype() {
        return subtype;
    }
}
