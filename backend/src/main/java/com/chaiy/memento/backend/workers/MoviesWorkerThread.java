package com.chaiy.memento.backend.workers;

import com.chaiy.memento.backend.bean.MementoBean;
import com.chaiy.memento.backend.bean.sections.movies.MovieApiHelper;
import com.chaiy.memento.backend.utility.MementoBeanGeneratorUtility;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Discover;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class MoviesWorkerThread implements Runnable {

    private static final String API_KEY = "12a5dbe2ed53efb4952f94325977f4cd";
    private final CountDownLatch latch;
    private final MementoBean mementoBean;
    private final String date;

    public MoviesWorkerThread(CountDownLatch latch, MementoBean mementoBean, String date) {
        this.latch = latch;
        this.mementoBean = mementoBean;
        this.date = date;
    }

    @Override
    public void run() {
        System.out.println("Date : Movies : "+ (date));
        TmdbApi tmdbApi = MovieApiHelper.getTMDBApi(API_KEY);
        Discover discover = new Discover()
                .releaseDateGte(date)
                .releaseDateLte(date).page(1)
                .sortBy("vote_average.desc");
        MovieResultsPage movieResultsPage = tmdbApi.getDiscover().getDiscover(discover);
        List<MovieDb> movieDbList = movieResultsPage.getResults();

        if (movieDbList != null) {
            mementoBean.addSection(MementoBeanGeneratorUtility.generateMoviesSectionBean(movieDbList));
        }

        latch.countDown();
    }
}
