package com.chaiy.memento.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.chaiy.memento.backend.mementoApi.MementoApi;
import com.chaiy.memento.backend.mementoApi.model.MementoBean;
import com.chaiy.memento.database.DBUtil;
import com.chaiy.memento.database.MementoProvider;
import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.model.sections.movies.MoviesSectionModel;
import com.chaiy.memento.model.sections.news.NewsSectionModel;
import com.chaiy.memento.model.sections.weather.WeatherSectionModel;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

/**
 * Created by Chaiy on 12/5/2016.
 */

public class MementoBgService extends IntentService {

    private static MementoApi myApiService = null;


    public MementoBgService() {
        super("MementoBgService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        fetchEvents();

    }

    private void fetchEvents() {
        MementoModel model = null;
        if (myApiService == null) {  // Only do this once
            MementoApi.Builder builder = new MementoApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            MementoBean mementoBean = myApiService.getEvents("2013-05-03", "37.8267", "-122.4233").execute();
            if (mementoBean != null) {

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(mementoBean.toString()).getAsJsonObject();

                Gson gson = new Gson();
                model = gson.fromJson(jsonObject, MementoModel.class);
                // MementoModel model = new MementoModel(mementoBean.getSectionOrder());

                for (int i = 0; i < model.getSectionOrder().size(); i++) {
                    int sectionVal = model.getSectionOrder().get(i);

                    switch (sectionVal) {
                        case MementoModel.MOVIES_SECTION:
                            JsonObject moviesJson = jsonObject.get("sections").getAsJsonObject().get(Integer.toString(sectionVal)).getAsJsonObject();
                            MoviesSectionModel moviesSectionModel = gson.fromJson(moviesJson, MoviesSectionModel.class);
                            getContentResolver().delete(MementoProvider.Movies.CONTENT_URI, null, null);
                            getContentResolver().bulkInsert(MementoProvider.Movies.CONTENT_URI
                                    , DBUtil.getMovieContentValues(moviesSectionModel));
                            model.addSection(sectionVal, moviesSectionModel);
                            break;
                        case MementoModel.NEWS_SECTION:
                            JsonObject newsJson = jsonObject.get("sections").getAsJsonObject().get(Integer.toString(sectionVal)).getAsJsonObject();
                            NewsSectionModel newsSectionModel = gson.fromJson(newsJson, NewsSectionModel.class);
                            getContentResolver().delete(MementoProvider.News.CONTENT_URI, null, null);
                            getContentResolver().bulkInsert(MementoProvider.News.CONTENT_URI
                                    , DBUtil.getNewsContentValues(newsSectionModel));
                            model.addSection(sectionVal, newsSectionModel);
                            break;
                        case MementoModel.WEATHER_SECTION:
                            JsonObject weatherJson = jsonObject.get("sections").getAsJsonObject().get(Integer.toString(sectionVal)).getAsJsonObject();
                            WeatherSectionModel weatherSectionModel = gson.fromJson(weatherJson, WeatherSectionModel.class);
                            getContentResolver().delete(MementoProvider.Weather.CONTENT_URI, null, null);
                            getContentResolver().insert(MementoProvider.Weather.CONTENT_URI
                                    , DBUtil.getWeatherContentValues(weatherSectionModel));
                            model.addSection(sectionVal, weatherSectionModel);
                            break;
                    }
                }
            }
            Log.d("test", "" + model.getSections().size());

            stopSelf();

        } catch (Exception e) {
            Log.e("test meml Exception ", e.getMessage(), e);
            stopSelf();
        }
    }
}
