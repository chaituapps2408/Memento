package com.chaiy.nytimes;


import com.chaiy.nytimes.requests.ArticleSearchRequest;
import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.chaiy.nytimes.services.NYTimesAipServices;
import com.chaiy.nytimes.services.NYTimesApiServiceImpl;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Chaiy on 11/5/2016.
 */


public final class NYTimesApi {

    private static NYTimesApi nyTimesApi;
    private static String API_KEY = null;

    NYTimesApi(String api_key) {
        API_KEY = api_key;
    }

    public static NYTimesApi init(String apiKey) {
        if (nyTimesApi == null)
            nyTimesApi = new NYTimesApi(apiKey);
        return nyTimesApi;
    }

    public ArticleSearchResponse getArticles(ArticleSearchRequest articleSearchRequest) throws IllegalStateException {

        if (StringUtils.isEmpty(API_KEY)) {
            throw new IllegalStateException("API_KEY is empty. Please provide a valid api key");
        }

        NYTimesAipServices impl = NYTimesApiServiceImpl.fetchServiceClient(API_KEY);
        ArticleSearchResponse response = impl.getArticles(articleSearchRequest);

        return response;

    }

}
