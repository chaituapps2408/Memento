package com.chaiy.nytimes.services.clients.httpclient;


import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.chaiy.nytimes.services.utilities.NYTimesApiConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class SimpleHttpClient {

    //HttpURLConnection conn = null;
    String apiPathURL;

    public SimpleHttpClient(String apiPath) {
        apiPathURL = NYTimesApiConstants.NY_TIMES_BASE_URL + apiPath;
    }


    public ArticleSearchResponse getArticleListResponse(HashMap<String, String> params) {
        ArticleSearchResponse response = null;

        String urlWithParams = null;
        try {
            urlWithParams = apiPathURL + getQueryString(params);
            URL url = new URL(urlWithParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn.setDoOutput(true);
            // conn.setRequestMethod("GET");

            int respCode = conn.getResponseCode();  // New items get NOT_FOUND on PUT
            if (respCode == HttpURLConnection.HTTP_OK) {

                Reader reader = new InputStreamReader(conn.getInputStream());
                GsonBuilder b = new GsonBuilder();
                Gson gson = b.registerTypeAdapter(ArticleSearchResponse.class,new ArticleJsonDeserializer()).create();
                response = gson.fromJson(reader, ArticleSearchResponse.class);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getQueryString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                result.append("?");
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}
