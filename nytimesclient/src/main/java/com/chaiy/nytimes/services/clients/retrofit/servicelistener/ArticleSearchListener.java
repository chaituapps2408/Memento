package com.chaiy.nytimes.services.clients.retrofit.servicelistener;


import com.chaiy.nytimes.response.ArticleSearchResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Chaiy on 11/5/2016.
 */

public interface  ArticleSearchListener {

    @GET("search/v2/articlesearch.json")
    Call<ArticleSearchResponse> getArticles(@QueryMap Map<String, String> options);

}
