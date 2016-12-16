package com.chaiy.memento.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.SparseArray;

import com.chaiy.memento.database.columns.MovieColumns;
import com.chaiy.memento.database.columns.NewsColumns;
import com.chaiy.memento.database.columns.WeatherColumns;
import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.movies.MoviesItemModel;
import com.chaiy.memento.model.sections.movies.MoviesSectionModel;
import com.chaiy.memento.model.sections.news.NewsItemModel;
import com.chaiy.memento.model.sections.news.NewsSectionModel;
import com.chaiy.memento.model.sections.weather.DailyWeatherModel;
import com.chaiy.memento.model.sections.weather.WeatherSectionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaiy on 12/4/2016.
 */

public final class DBUtil {


    public static ContentValues[] getMovieContentValues(MoviesSectionModel moviesSectionModel) {

        ContentValues[] values = new ContentValues[moviesSectionModel.getMoviesItemModelList().size()];

        int i = 0;
        for (MoviesItemModel movieItem :
                moviesSectionModel.getMoviesItemModelList()) {

            ContentValues value = new ContentValues();
            value.put(MovieColumns._id, Long.toString(movieItem.get_id()));
            value.put(MovieColumns.backdropPath, movieItem.getBackdropPath());
            value.put(MovieColumns.language, movieItem.getLanguage());
            value.put(MovieColumns.movieName, movieItem.getMovieName());
            value.put(MovieColumns.overview, movieItem.getOverview());
            value.put(MovieColumns.posterPath, movieItem.getPosterPath());
            value.put(MovieColumns.rating, movieItem.getRating());
            value.put(MovieColumns.ratingSource, movieItem.getRatingSource());
            value.put(MovieColumns.releaseDate, movieItem.getReleaseDate());

            values[i] = value;

            i++;
        }
        return values;
    }

    public static ContentValues[] getNewsContentValues(NewsSectionModel newsSectionModel) {

        ContentValues[] values = new ContentValues[newsSectionModel.getNewsItemModelList().size()];

        int i = 0;
        for (NewsItemModel newsItem :
                newsSectionModel.getNewsItemModelList()) {

            ContentValues value = new ContentValues();
            value.put(NewsColumns.headLine, newsItem.getHeadLine());
            value.put(NewsColumns.imagePath, newsItem.getImagePath());
            value.put(NewsColumns.publishedDate, newsItem.getPublishedDate());
            value.put(NewsColumns.snippet, newsItem.getSnippet());
            value.put(NewsColumns.source, newsItem.getSource());
            value.put(NewsColumns.url, newsItem.getUrl());

            values[i] = value;
            i++;
        }
        return values;
    }

    public static ContentValues getWeatherContentValues(WeatherSectionModel weatherSectionModel) {

        ContentValues value = new ContentValues();

        DailyWeatherModel dailyWeatherModel = weatherSectionModel.getDailyWeather();

        value.put(WeatherColumns.summary, dailyWeatherModel.getSummary());
        value.put(WeatherColumns.humidity, dailyWeatherModel.getHumidity());
        value.put(WeatherColumns.icon, dailyWeatherModel.getIcon());
        value.put(WeatherColumns.precipType, dailyWeatherModel.getPrecipType());
        value.put(WeatherColumns.temperatureMax, dailyWeatherModel.getTemperatureMax());
        value.put(WeatherColumns.temperatureMin, dailyWeatherModel.getTemperatureMin());
        value.put(WeatherColumns.windSpeed, dailyWeatherModel.getWindSpeed());

        return value;
    }


    public static MementoModel getMementoData(SparseArray<Cursor> cursorList) {

        List<Integer> temp = new ArrayList<>(3);
        if (cursorList.get(MementoModel.MOVIES_SECTION) != null)
            temp.add(MementoModel.MOVIES_SECTION);
        if (cursorList.get(MementoModel.NEWS_SECTION) != null)
            temp.add(MementoModel.NEWS_SECTION);
        if (cursorList.get(MementoModel.WEATHER_SECTION) != null)
            temp.add(MementoModel.WEATHER_SECTION);

        MementoModel model = new MementoModel(temp);


        for (int i = 0; i < cursorList.size(); i++) {

            BaseSectionModel baseSectionModel = getSectionModel(cursorList.keyAt(i), cursorList.valueAt(i));

            if (baseSectionModel != null) {
                model.addSection(cursorList.keyAt(i), baseSectionModel);
            }

        }

        return model;
    }

    private static BaseSectionModel getSectionModel(int position, Cursor cursor) {
        switch (position) {
            case MementoModel.MOVIES_SECTION:
                return getMoviesSectionModel(cursor);

            case MementoModel.NEWS_SECTION:
                return getNewsSectionModel(cursor);

            case MementoModel.WEATHER_SECTION:
                return getWeatherSectionModel(cursor);

            default:
                return null;
        }

    }

    private static MoviesSectionModel getMoviesSectionModel(Cursor cursor) {

        List<MoviesItemModel> moviesItemModelList = new ArrayList<>();

        while (cursor.moveToNext()) {
            MoviesItemModel moviesItemModel = new MoviesItemModel.Builder()
                    ._id(Long.parseLong(cursor.getString(cursor.getColumnIndex(MovieColumns._id))))
                    .backdropPath(cursor.getString(cursor.getColumnIndex(MovieColumns.backdropPath)))
                    .language(cursor.getString(cursor.getColumnIndex(MovieColumns.language)))
                    .movieName(cursor.getString(cursor.getColumnIndex(MovieColumns.movieName)))
                    .posterPath(cursor.getString(cursor.getColumnIndex(MovieColumns.posterPath)))
                    .rating(cursor.getString(cursor.getColumnIndex(MovieColumns.rating)))
                    .ratingSource(cursor.getString(cursor.getColumnIndex(MovieColumns.ratingSource)))
                    .releaseDate(cursor.getString(cursor.getColumnIndex(MovieColumns.releaseDate)))
                    .overview(cursor.getString(cursor.getColumnIndex(MovieColumns.overview)))
                    .build();

            moviesItemModelList.add(moviesItemModel);
        }

        cursor.close();
        MoviesSectionModel moviesSectionModel = new MoviesSectionModel(null, moviesItemModelList);
        return moviesSectionModel;
    }

    private static NewsSectionModel getNewsSectionModel(Cursor cursor) {

        List<NewsItemModel> newsItemModelsList = new ArrayList<>();

        while (cursor.moveToNext()) {
            NewsItemModel newsItemModel = new NewsItemModel.Builder()
                    .headLine(cursor.getString(cursor.getColumnIndex(NewsColumns.headLine)))
                    .imagePath(cursor.getString(cursor.getColumnIndex(NewsColumns.imagePath)))
                    .publishedDate(cursor.getString(cursor.getColumnIndex(NewsColumns.publishedDate)))
                    .snippet(cursor.getString(cursor.getColumnIndex(NewsColumns.snippet)))
                    .source(cursor.getString(cursor.getColumnIndex(NewsColumns.source)))
                    .url(cursor.getString(cursor.getColumnIndex(NewsColumns.url)))
                    .build();

            newsItemModelsList.add(newsItemModel);
        }

        cursor.close();
        NewsSectionModel newsSectionModel = new NewsSectionModel(newsItemModelsList);
        return newsSectionModel;
    }

    private static WeatherSectionModel getWeatherSectionModel(Cursor cursor) {

        DailyWeatherModel dailyWeatherModel = null;
        while (cursor.moveToNext()) {
            dailyWeatherModel = new DailyWeatherModel.Builder()
                    .summary(cursor.getString(cursor.getColumnIndex(WeatherColumns.summary)))
                    .icon(cursor.getString(cursor.getColumnIndex(WeatherColumns.icon)))
                    .precipType(cursor.getString(cursor.getColumnIndex(WeatherColumns.precipType)))
                    .temperatureMin(cursor.getString(cursor.getColumnIndex(WeatherColumns.temperatureMin)))
                    .temperatureMax(cursor.getString(cursor.getColumnIndex(WeatherColumns.temperatureMax)))
                    .windSpeed(cursor.getString(cursor.getColumnIndex(WeatherColumns.windSpeed)))
                    .humidity(cursor.getString(cursor.getColumnIndex(WeatherColumns.humidity)))
                    .build();

        }

        cursor.close();
        WeatherSectionModel weatherSectionModel = new WeatherSectionModel(dailyWeatherModel);
        return weatherSectionModel;
    }
}
