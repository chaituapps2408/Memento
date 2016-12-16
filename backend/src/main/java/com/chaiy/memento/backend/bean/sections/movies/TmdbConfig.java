package com.chaiy.memento.backend.bean.sections.movies;

import java.util.List;

import info.movito.themoviedbapi.model.config.TmdbConfiguration;

/**
 * Created by Chaiy on 11/13/2016.
 */

public class TmdbConfig {

    private static TmdbConfig tmdbConfig;

    public static TmdbConfig getInstance() {
        if (tmdbConfig == null) {
            tmdbConfig = getTmdbConfig();
        }

        return tmdbConfig;
    }


    private static TmdbConfig getTmdbConfig() {
        TmdbConfiguration configuration = MovieApiHelper.getTMDBConfig();
        if (configuration != null) {

            TmdbConfig config = new Builder()
                    .baseUrl(configuration.getBaseUrl())
                    .secureBaseUrl(configuration.getSecureBaseUrl())
                    .posterSizes(configuration.getPosterSizes())
                    .backdropSizes(configuration.getBackdropSizes())
                    .profileSizes(configuration.getProfileSizes())
                    .logoSizes(configuration.getLogoSizes())
                    .build();

            return config;
        }

        TmdbConfig config = new Builder()
                .baseUrl("http://image.tmdb.org/t/p/")
                .secureBaseUrl("https://image.tmdb.org/t/p/")
                .build();

        return config;

    }

    private String baseUrl;
    private String secureBaseUrl;
    private List<String> posterSizes;
    private List<String> backdropSizes;
    private List<String> profileSizes;
    private List<String> logoSizes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public List<String> getPosterSizes() {
        return posterSizes;
    }

    public List<String> getBackdropSizes() {
        return backdropSizes;
    }

    public List<String> getProfileSizes() {
        return profileSizes;
    }

    public List<String> getLogoSizes() {
        return logoSizes;
    }

    private TmdbConfig(Builder builder) {
        baseUrl = builder.baseUrl;
        secureBaseUrl = builder.secureBaseUrl;
        posterSizes = builder.posterSizes;
        backdropSizes = builder.backdropSizes;
        profileSizes = builder.profileSizes;
        logoSizes = builder.logoSizes;
    }


    public static final class Builder {
        private String baseUrl;
        private String secureBaseUrl;
        private List<String> posterSizes;
        private List<String> backdropSizes;
        private List<String> profileSizes;
        private List<String> logoSizes;

        public Builder() {
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public Builder secureBaseUrl(String val) {
            secureBaseUrl = val;
            return this;
        }

        public Builder posterSizes(List<String> val) {
            posterSizes = val;
            return this;
        }

        public Builder backdropSizes(List<String> val) {
            backdropSizes = val;
            return this;
        }

        public Builder profileSizes(List<String> val) {
            profileSizes = val;
            return this;
        }

        public Builder logoSizes(List<String> val) {
            logoSizes = val;
            return this;
        }

        public TmdbConfig build() {
            return new TmdbConfig(this);
        }
    }
}
