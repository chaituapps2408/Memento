package com.chaiy.nytimes.services.clients.retrofit;


import com.chaiy.nytimes.requests.ArticleSearchRequest;
import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.chaiy.nytimes.services.NYTimesAipServices;
import com.chaiy.nytimes.services.clients.retrofit.servicelistener.ArticleSearchListener;
import com.chaiy.nytimes.services.utilities.NYTimesApiConstants;
import com.chaiy.nytimes.services.utilities.RequestBuilderUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Chaiy on 11/5/2016.
 */

public class RetrofitServiceImpl implements NYTimesAipServices {

    Retrofit retrofit;
    String apiKey;

    public RetrofitServiceImpl(String apiKey) {

        this.apiKey = apiKey;
        retrofit = new Retrofit.Builder()
                .baseUrl(NYTimesApiConstants.NY_TIMES_BASE_URL)
                .build();

    }

    @Override
    public ArticleSearchResponse getArticles(ArticleSearchRequest articleSearchRequest) {

        ArticleSearchResponse resp = null;
        ArticleSearchListener articleSearchListener = retrofit.create(ArticleSearchListener.class);

        Call<ArticleSearchResponse> response = articleSearchListener.getArticles(RequestBuilderUtil.buildArticleSearchRequest(apiKey, articleSearchRequest));
        try {
            Response<ArticleSearchResponse> response1 = response.execute();
            resp = response1.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(resp);
        return resp;
    }


}
