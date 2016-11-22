package com.chaiy.nytimes.bean.articles;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class HeadLine {

    @SerializedName("main")
    String main;

    @SerializedName("print_headline")
    String printHeadline;

    public String getMain() {
        return main;
    }

    public String getPrintHeadline() {
        return printHeadline;
    }
}
