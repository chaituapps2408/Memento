package com.chaiy.nytimes.services;


import com.chaiy.nytimes.requests.ArticleSearchRequest;
import com.chaiy.nytimes.response.ArticleSearchResponse;

/**
 * Created by Chaiy on 11/5/2016.
 */

public interface NYTimesAipServices {

    ArticleSearchResponse getArticles(ArticleSearchRequest articleSearchRequest);

}
