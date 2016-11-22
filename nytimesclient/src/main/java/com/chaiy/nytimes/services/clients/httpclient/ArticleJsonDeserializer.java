package com.chaiy.nytimes.services.clients.httpclient;

import com.chaiy.nytimes.bean.articles.HeadLine;
import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class ArticleJsonDeserializer implements JsonDeserializer<ArticleSearchResponse> {
    @Override
    public ArticleSearchResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject mainObject = json.getAsJsonObject();
        Gson g = new Gson();
        // Construct an apple (this shouldn't try to parse the seeds stuff
        ArticleSearchResponse articleSearchResponse = g.fromJson(json, ArticleSearchResponse.class);
        List<HeadLine> headLineList = new ArrayList<>();
        // Check to see if we were given a list or a single seed
        JsonElement docsList = mainObject.get("response").getAsJsonObject().get("docs");
        if (docsList.isJsonArray()) {
            JsonArray array = (JsonArray) mainObject.get("response").getAsJsonObject().get("docs");
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).getAsJsonObject().get("headline").isJsonArray()) {
                    headLineList = g.fromJson(array.get(i).getAsJsonObject().get("headline"),
                            new TypeToken<List<HeadLine>>() {
                            }.getType());
                } else {
                    // otherwise, parse the single seed,
                    // and add it to the list
                    HeadLine single = g.fromJson(array.get(i).getAsJsonObject().get("headline"), HeadLine.class);
                    headLineList = new ArrayList<>();
                    headLineList.add(single);
                }
                articleSearchResponse.getArticleResponse().getArticleList().get(i).setHeadline(headLineList);
            }
        }
        return articleSearchResponse;
    }
}