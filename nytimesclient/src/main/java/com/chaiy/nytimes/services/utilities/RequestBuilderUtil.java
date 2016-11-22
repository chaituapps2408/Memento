package com.chaiy.nytimes.services.utilities;


import com.chaiy.nytimes.requests.ArticleSearchRequest;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;


/**
 * Created by Chaiy on 11/5/2016.
 */

public class RequestBuilderUtil {


    public static HashMap<String, String> buildArticleSearchRequest(String apiKey, ArticleSearchRequest articleSearchRequest) {

        HashMap<String, String> queryMap = new HashMap<>();

        queryMap.put("api-key", apiKey);

        if (articleSearchRequest == null) {
            return queryMap;
        }

        if (null != articleSearchRequest.getSearchQueryList()) {
            queryMap.put("q", getStringWithCommas(articleSearchRequest.getSearchQueryList()));
        }

        if (null != articleSearchRequest.getFilteredQueryList()) {
            queryMap.put("fq", getStringWithCommas(articleSearchRequest.getFilteredQueryList()));
        }

        if (!StringUtils.isEmpty(articleSearchRequest.getBeginDate())) {
            queryMap.put("begin_date", articleSearchRequest.getBeginDate());
        }

        if (!StringUtils.isEmpty(articleSearchRequest.getEndDate())) {
            queryMap.put("end_date", articleSearchRequest.getEndDate());
        }

        if (!StringUtils.isEmpty(articleSearchRequest.getSortType())) {
            queryMap.put("sort", articleSearchRequest.getSortType());
        }

        if (null != articleSearchRequest.getFilterFields()) {
            queryMap.put("fl", getStringWithCommas(articleSearchRequest.getFilterFields()));
        }

        queryMap.put("page", Integer.toString(articleSearchRequest.getPage()));


        return queryMap;
    }

    private static String getStringWithCommas(String[] values) {

        String finalResult = StringUtils.join(values, ",");

        return finalResult;
    }


}
