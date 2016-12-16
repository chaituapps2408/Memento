package com.chaiy.memento.network;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.content.AsyncTaskLoader;
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
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Chaiy on 12/11/2016.
 */

public class SearchAsyncTaskLoader extends AsyncTaskLoader<MementoModel> {

    private static final String TAG = SearchAsyncTaskLoader.class.getSimpleName();


    //Default to local host to support emulator
    private static String SERVER_URL = "http://10.0.2.2:8080/_ah/api/";

    private final String searchDate;
    private final String latString;
    private final String lngString;
    private final boolean isFromService;
    private Gson gson = new Gson();

    private static MementoApi myApiService = null;

    public SearchAsyncTaskLoader(Context context, String searchDate, String latString, String lngString, boolean isFromService) {
        super(context);
        this.searchDate = searchDate;
        if (latString == null || "".equalsIgnoreCase(latString)) {
            latString = "-1";
        }
        if (lngString == null || "".equalsIgnoreCase(lngString)) {
            lngString = "-1";
        }
        this.latString = latString;
        this.lngString = lngString;
        this.isFromService = isFromService;

        Properties properties = getMyProperties("config.properties");
        String url = (String) properties.get("SERVER_URL");
        if (url != null) {
            SERVER_URL = url;
        }
    }

    public Properties getMyProperties(String file) {
        Properties properties = new Properties();

        try {
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream = assetManager.open(file);
            properties.load(inputStream);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return properties;
    }

    @Override
    public MementoModel loadInBackground() {
        MementoModel model = null;
        if (myApiService == null) {  // Only do this once
            MementoApi.Builder builder = new MementoApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 192.168.137.1 is my local machine ip address
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(SERVER_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            Log.d(TAG, " connecting to server: " + SERVER_URL);
            myApiService = builder.build();
        }

        try {
            Log.d(TAG, " fetching events for date :" + searchDate);

            Log.d(TAG, " fetching weather for latitude :" + latString);
            Log.d(TAG, " fetching weather for longitude :" + lngString);
            MementoBean mementoBean = myApiService.getEvents(searchDate, latString, lngString).execute();
            if (mementoBean != null) {
                Log.d(TAG, " fetching events for date :" + searchDate + " success ");

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(mementoBean.toString()).getAsJsonObject();

                model = gson.fromJson(jsonObject, MementoModel.class);
                // MementoModel model = new MementoModel(mementoBean.getSectionOrder());

                for (int i = 0; i < model.getSectionOrder().size(); i++) {
                    int sectionVal = model.getSectionOrder().get(i);

                    switch (sectionVal) {
                        case MementoModel.MOVIES_SECTION:
                            addMoviesSection(jsonObject, model, sectionVal);
                            break;
                        case MementoModel.NEWS_SECTION:
                            addNewsSection(jsonObject, model, sectionVal);
                            break;
                        case MementoModel.WEATHER_SECTION:
                            addWeatherSection(jsonObject, model, sectionVal);
                            break;
                    }
                }

                Log.d(TAG, "Data Fetch Success " + model.getSections().size());
            } else {
                Log.d(TAG, " fetching events for date :" + searchDate + " failed mementoModel null ");
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "fetching events for date :" + searchDate + " failed with exception : " + e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, "fetching events for date :" + searchDate + " failed with exception : " + e.getMessage(), e);
            e.printStackTrace();
        }

        return model;
    }


    private void addMoviesSection(JsonObject jsonObject, MementoModel model, int sectionVal) {
        try {
            JsonObject moviesJson = jsonObject.get("sections").getAsJsonObject().get(Integer.toString(sectionVal)).getAsJsonObject();
            MoviesSectionModel moviesSectionModel = gson.fromJson(moviesJson, MoviesSectionModel.class);
            model.addSection(sectionVal, moviesSectionModel);
            if (isFromService) {
                saveMoviesDataToDB(moviesSectionModel);
            }
        } catch (IllegalStateException | NullPointerException ex) {
            Log.e(TAG, "parsing movies failed for date :" + searchDate + " failed with exception : " + ex.getMessage(), ex);
            if (model.getSectionOrder().size() >= 1)
                model.getSectionOrder().remove(0);
        }

    }

    private void addNewsSection(JsonObject jsonObject, MementoModel model, int sectionVal) {
        try {
            JsonObject newsJson = jsonObject.get("sections").getAsJsonObject().get(Integer.toString(sectionVal)).getAsJsonObject();
            NewsSectionModel newsSectionModel = gson.fromJson(newsJson, NewsSectionModel.class);
            model.addSection(sectionVal, newsSectionModel);
            if (isFromService) {
                saveNewsDataToDB(newsSectionModel);
            }
        } catch (IllegalStateException | NullPointerException ex) {
            Log.e(TAG, "parsing News failed for date :" + searchDate + " failed with exception : " + ex.getMessage(), ex);
            if (model.getSectionOrder().size() >= 2)
                model.getSectionOrder().remove(1);
        }

    }

    private void addWeatherSection(JsonObject jsonObject, MementoModel model, int sectionVal) {
        try {
            JsonObject weatherJson = jsonObject.get("sections").getAsJsonObject().get(Integer.toString(sectionVal)).getAsJsonObject();
            WeatherSectionModel weatherSectionModel = gson.fromJson(weatherJson, WeatherSectionModel.class);
            model.addSection(sectionVal, weatherSectionModel);
            if (isFromService) {
                saveWeatherDataToDB(weatherSectionModel);
            }
        } catch (IllegalStateException | NullPointerException ex) {
            Log.e(TAG, "parsing Weather failed for date :" + searchDate + " and lat :" + latString + " lng : " + lngString + "failed with exception : " + ex.getMessage(), ex);
            if (model.getSectionOrder().size() >= 3)
                model.getSectionOrder().remove(2);
        }

    }

    private void saveMoviesDataToDB(MoviesSectionModel moviesSectionModel) {
        getContext().getContentResolver().delete(MementoProvider.Movies.CONTENT_URI, null, null);
        getContext().getContentResolver().bulkInsert(MementoProvider.Movies.CONTENT_URI
                , DBUtil.getMovieContentValues(moviesSectionModel));
    }

    private void saveNewsDataToDB(NewsSectionModel newsSectionModel) {
        getContext().getContentResolver().delete(MementoProvider.News.CONTENT_URI, null, null);
        getContext().getContentResolver().bulkInsert(MementoProvider.News.CONTENT_URI
                , DBUtil.getNewsContentValues(newsSectionModel));
    }

    private void saveWeatherDataToDB(WeatherSectionModel weatherSectionModel) {
        getContext().getContentResolver().delete(MementoProvider.Weather.CONTENT_URI, null, null);
        getContext().getContentResolver().insert(MementoProvider.Weather.CONTENT_URI
                , DBUtil.getWeatherContentValues(weatherSectionModel));

    }
}
