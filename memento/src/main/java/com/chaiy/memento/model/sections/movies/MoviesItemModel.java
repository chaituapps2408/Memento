package com.chaiy.memento.model.sections.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MoviesItemModel implements Parcelable {

    public static final String RATING_SOURCE_TMDB = "TMDB";
    public static final String RATING_SOURCE_IMDB = "IMDB";

    String movieName;
    String releaseDate;
    String overview;
    String language;
    String rating;
    String ratingSource;
    String posterPath;
    String backdropPath;
    long _id;

    private MoviesItemModel(Builder builder) {
        movieName = builder.movieName;
        releaseDate = builder.releaseDate;
        overview = builder.overview;
        language = builder.language;
        rating = builder.rating;
        ratingSource = builder.ratingSource;
        posterPath = builder.posterPath;
        backdropPath = builder.backdropPath;
        _id = builder._id;
    }

    public String getLanguage() {
        return language;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    public String getOverview() {
        return overview;
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
        private String overview;
        private String backgroundPath;
        private String language;
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

        public Builder overview(String val) {
            overview = val;
            return this;
        }

        public Builder backgroundPath(String val) {
            backgroundPath = val;
            return this;
        }

        public Builder language(String val) {
            language = val;
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

        public MoviesItemModel build() {
            return new MoviesItemModel(this);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieName);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.language);
        dest.writeString(this.rating);
        dest.writeString(this.ratingSource);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeLong(this._id);
    }

    protected MoviesItemModel(Parcel in) {
        this.movieName = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.language = in.readString();
        this.rating = in.readString();
        this.ratingSource = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this._id = in.readLong();
    }

    public static final Creator<MoviesItemModel> CREATOR = new Creator<MoviesItemModel>() {
        @Override
        public MoviesItemModel createFromParcel(Parcel source) {
            return new MoviesItemModel(source);
        }

        @Override
        public MoviesItemModel[] newArray(int size) {
            return new MoviesItemModel[size];
        }
    };
}
