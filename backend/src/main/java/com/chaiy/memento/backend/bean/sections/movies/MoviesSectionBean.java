package com.chaiy.memento.backend.bean.sections.movies;

import com.chaiy.memento.backend.bean.sections.BaseSectionBean;
import com.chaiy.memento.backend.bean.sections.Sections;

import java.util.List;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MoviesSectionBean extends BaseSectionBean {

    private final TmdbConfig tmdbConfig;

    private final List<MoviesItemBean> moviesItemBeanList;

    public MoviesSectionBean(TmdbConfig tmdbConfig, List<MoviesItemBean> moviesItemBeanList) {
        super(Sections.MOVIES);
        this.tmdbConfig = tmdbConfig;
        this.moviesItemBeanList = moviesItemBeanList;
    }

    public List<MoviesItemBean> getMoviesItemBeanList() {
        return moviesItemBeanList;
    }

    public TmdbConfig getTmdbConfig() {
        return tmdbConfig;
    }
}
