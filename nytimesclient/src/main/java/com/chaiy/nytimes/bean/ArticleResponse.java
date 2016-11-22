package com.chaiy.nytimes.bean;


import com.chaiy.nytimes.bean.articles.Article;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class ArticleResponse {

    @SerializedName("docs")
    ArrayList<Article> articleList;

    @SerializedName("status")
    String status;

    @SerializedName("meta")
    Meta meta;

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    public String getStatus() {
        return status;
    }

    public Meta getMeta() {
        return meta;
    }
}
