package com.chaiy.nytimes.services.clients.httpclient;


import com.chaiy.nytimes.requests.ArticleSearchRequest;
import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.chaiy.nytimes.services.NYTimesAipServices;
import com.chaiy.nytimes.services.utilities.RequestBuilderUtil;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class SimpleHttpServiceImpl implements NYTimesAipServices {


    final String apiKey;

    public SimpleHttpServiceImpl(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public ArticleSearchResponse getArticles(ArticleSearchRequest articleSearchRequest) {
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient("search/v2/articlesearch.json");
        ArticleSearchResponse response = simpleHttpClient.getArticleListResponse(RequestBuilderUtil.buildArticleSearchRequest(apiKey, articleSearchRequest));
        return response;
    }


}
