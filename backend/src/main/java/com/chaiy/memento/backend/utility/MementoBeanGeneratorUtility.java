package com.chaiy.memento.backend.utility;

import com.chaiy.memento.backend.bean.sections.movies.MoviesItemBean;
import com.chaiy.memento.backend.bean.sections.movies.MoviesSectionBean;
import com.chaiy.memento.backend.bean.sections.movies.TmdbConfig;
import com.chaiy.memento.backend.bean.sections.news.NewsItemBean;
import com.chaiy.memento.backend.bean.sections.news.NewsSectionBean;
import com.chaiy.memento.backend.bean.sections.weather.DailyWeather;
import com.chaiy.memento.backend.bean.sections.weather.WeatherSectionBean;
import com.chaiy.nytimes.bean.ArticleResponse;
import com.chaiy.nytimes.bean.articles.Article;
import com.chaiy.nytimes.bean.articles.HeadLine;
import com.chaiy.nytimes.bean.articles.Multimedia;
import com.chaiy.nytimes.response.ArticleSearchResponse;
import com.github.dvdme.ForecastIOLib.FIODataPoint;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MementoBeanGeneratorUtility {


    public static NewsSectionBean generateNewsSectionBean(ArticleSearchResponse articleSearchResponse) {

        if (articleSearchResponse == null
                || articleSearchResponse.getArticleResponse() == null
                || articleSearchResponse.getArticleResponse().getArticleList() == null
                || articleSearchResponse.getArticleResponse().getArticleList().size() == 0)
            return null;

        ArticleResponse articleResponse = articleSearchResponse.getArticleResponse();

        List<NewsItemBean> newsItemBeanList = new ArrayList<>();

        for (Article article :
                articleResponse.getArticleList()) {
            NewsItemBean newsItemBean = new NewsItemBean();
            newsItemBean.setSnippet(article.getSnippet());
            newsItemBean.setPublishedDate(article.getPubDate());
            newsItemBean.setSource(article.getSource());
            newsItemBean.setUrl(article.getWebUrl());

            if (article.getMultimedia() != null && article.getMultimedia().length > 0) {
                Multimedia multimedia = article.getMultimedia()[0];
                if (multimedia != null) {
                    newsItemBean.setImagePath(multimedia.getUrl());
                }
            }

            if (article.getHeadline() != null && article.getHeadline().size() > 0) {

                HeadLine headLine = article.getHeadline().get(0);
                if (StringUtil.isEmptyOrWhitespace(headLine.getMain())) {
                    newsItemBean.setHeadLine(headLine.getMain());
                } else {
                    newsItemBean.setHeadLine(headLine.getPrintHeadline());
                }

            }

            newsItemBeanList.add(newsItemBean);
        }
        NewsSectionBean newsSectionBean = new NewsSectionBean(newsItemBeanList);

        return newsSectionBean;

    }


    public static WeatherSectionBean generateWeatherSectionBean(FIODataPoint fioDataPoint) {

        //In case there is no daily data available


        DailyWeather dailyWeather = new DailyWeather.Builder()
                .apparentTemperatureMax(doubleToString(fioDataPoint.apparentTemperatureMax()))
                .apparentTemperatureMaxTime(fioDataPoint.apparentTemperatureMaxTime())
                .apparentTemperatureMin(doubleToString(fioDataPoint.apparentTemperatureMin()))
                .apparentTemperatureMinTime(fioDataPoint.apparentTemperatureMinTime())
                .cloudCover(doubleToString(fioDataPoint.cloudCover()))
                .dewPoint(doubleToString(fioDataPoint.dewPoint()))
                .humidity(doubleToString(fioDataPoint.humidity()))
                .icon(fioDataPoint.icon())
                .moonPhase(doubleToString(fioDataPoint.moonPhase()))
                .precipType(fioDataPoint.precipType())
                .pressure(doubleToString(fioDataPoint.pressure()))
                .summary(fioDataPoint.summary())
                .sunriseTime(fioDataPoint.sunriseTime())
                .sunsetTime(fioDataPoint.sunsetTime())
                .temperatureMax(doubleToString(fioDataPoint.temperatureMax()))
                .temperatureMaxTime(fioDataPoint.temperatureMaxTime())
                .temperatureMin(doubleToString(fioDataPoint.temperatureMin()))
                .temperatureMinTime(fioDataPoint.temperatureMinTime())
                .time(fioDataPoint.time())
                .visibility(doubleToString(fioDataPoint.visibility()))
                .windBearing(doubleToString(fioDataPoint.windBearing()))
                .windSpeed(doubleToString(fioDataPoint.windSpeed()))
                .build();

        return new WeatherSectionBean(dailyWeather);

    }

    private static String doubleToString(Double value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static MoviesSectionBean generateMoviesSectionBean(List<MovieDb> movieDbList) {

        //In case there is no daily data available

        List<MoviesItemBean> moviesItemBeanList = new ArrayList<>();
        for (MovieDb movieDB :
                movieDbList) {

            MoviesItemBean moviesItemBean = new MoviesItemBean.Builder()
                    ._id(movieDB.getId())
                    .movieName(movieDB.getTitle())
                    .rating(Float.toString(movieDB.getVoteAverage()))
                    .ratingSource(MoviesItemBean.RATING_SOURCE_TMDB)
                    .releaseDate(movieDB.getReleaseDate())
                    .posterPath(movieDB.getPosterPath())
                    .backdropPath(movieDB.getBackdropPath())
                    .tmdbConfig(getTmdbConfig())
                    .build();

            moviesItemBeanList.add(moviesItemBean);

        }

        MoviesSectionBean moviesItemBean = new MoviesSectionBean(moviesItemBeanList);

        return moviesItemBean;

    }

    public static TmdbConfig getTmdbConfig() {
        return TmdbConfig.getInstance();
    }

}
