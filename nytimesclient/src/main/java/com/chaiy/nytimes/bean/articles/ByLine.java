package com.chaiy.nytimes.bean.articles;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class ByLine {

    @SerializedName("contributor")
    String contributor;

    @SerializedName("person")
    Person person;

    @SerializedName("original")
    String original;

    public String getContributor() {
        return contributor;
    }

    public Person getPerson() {
        return person;
    }

    public String getOriginal() {
        return original;
    }
}
