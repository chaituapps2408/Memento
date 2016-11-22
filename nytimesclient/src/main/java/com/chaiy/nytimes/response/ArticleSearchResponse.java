package com.chaiy.nytimes.response;


import com.chaiy.nytimes.bean.ArticleResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class ArticleSearchResponse {


    @SerializedName("response")
    ArticleResponse articleResponse;

    public ArticleResponse getArticleResponse() {
        return articleResponse;
    }
}
