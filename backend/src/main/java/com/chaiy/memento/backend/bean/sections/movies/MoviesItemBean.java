package com.chaiy.memento.backend.bean.sections.movies;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MoviesItemBean {

    public static final String RATING_SOURCE_TMDB = "TMDB";
    public static final String RATING_SOURCE_IMDB = "IMDB";

    String movieName;
    String releaseDate;
    TmdbConfig tmdbConfig;
    String rating;
    String ratingSource;
    String posterPath;
    String backdropPath;
    long _id;

    private MoviesItemBean(Builder builder) {
        movieName = builder.movieName;
        releaseDate = builder.releaseDate;
        tmdbConfig = builder.tmdbConfig;
        rating = builder.rating;
        ratingSource = builder.ratingSource;
        posterPath = builder.posterPath;
        backdropPath = builder.backdropPath;
        _id = builder._id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public TmdbConfig getTmdbConfig() {
        return tmdbConfig;
    }

    public String getRating() {
        return rating;
    }

    public String getRatingSource() {
        return ratingSource;
    }

    public long get_id() {
        return _id;
    }

    public static final class Builder {
        private String movieName;
        private String releaseDate;
        private TmdbConfig tmdbConfig;
        private String rating;
        private String ratingSource;
        private long _id;
        private String posterPath;
        private String backdropPath;

        public Builder() {
        }

        public Builder movieName(String val) {
            movieName = val;
            return this;
        }

        public Builder releaseDate(String val) {
            releaseDate = val;
            return this;
        }

        public Builder tmdbConfig(TmdbConfig val) {
            tmdbConfig = val;
            return this;
        }

        public Builder rating(String val) {
            rating = val;
            return this;
        }

        public Builder ratingSource(String val) {
            ratingSource = val;
            return this;
        }

        public Builder _id(long val) {
            _id = val;
            return this;
        }

        public MoviesItemBean build() {
            return new MoviesItemBean(this);
        }

        public Builder posterPath(String val) {
            posterPath = val;
            return this;
        }

        public Builder backdropPath(String val) {
            backdropPath = val;
            return this;
        }
    }
}
