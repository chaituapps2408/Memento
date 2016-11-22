package com.chaiy.memento.backend.bean.sections.movies;

import java.util.HashMap;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;


/**
 * Created by Chaiy on 11/13/2016.
 */

public class MovieApiHelper {

    private static TmdbApi tmdbApi;
    private static HashMap<Integer, String> tmdbGenreMap = new HashMap<>();

    public static TmdbApi getTMDBApi(String apiKey) {

        if (tmdbApi == null) {
            tmdbApi = new TmdbApi(apiKey);
            initMovieGenres();
        }
        return tmdbApi;
    }


    private static void initMovieGenres() {

        List<Genre> genreList = tmdbApi.getGenre().getGenreList("en-US");
        for (int i = 0; i < genreList.size(); i++) {
            Genre genre = genreList.get(i);
            tmdbGenreMap.put(genre.getId(), genre.getName());
        }
    }

    public static TmdbConfiguration getTMDBConfig(){
        return tmdbApi.getConfiguration();
    }
}
