package com.chaiy.nytimes.bean.articles;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class Person {

    @SerializedName("firstname")
    String firstName;

    @SerializedName("middlename")
    String middleName;

    @SerializedName("lastname")
    String lastName;

    @SerializedName("rank")
    String rank;

    @SerializedName("role")
    String role;

    @SerializedName("organization")
    String organization;


    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRank() {
        return rank;
    }

    public String getRole() {
        return role;
    }

    public String getOrganization() {
        return organization;
    }
}
