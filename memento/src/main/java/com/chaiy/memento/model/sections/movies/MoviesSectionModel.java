package com.chaiy.memento.model.sections.movies;


import android.os.Parcel;

import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.Sections;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class MoviesSectionModel extends BaseSectionModel {

    @SerializedName("moviesItemBeanList")
    private final List<MoviesItemModel> moviesItemModelList;

    private  TmdbConfig tmdbConfig;

    public MoviesSectionModel(TmdbConfig tmdbConfig, List<MoviesItemModel> moviesItemModelList) {
        super(Sections.MOVIES);
        this.tmdbConfig = tmdbConfig;
        this.moviesItemModelList = moviesItemModelList;
    }

    public List<MoviesItemModel> getMoviesItemModelList() {
        return moviesItemModelList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.moviesItemModelList);
        dest.writeParcelable(this.tmdbConfig, flags);
    }

    protected MoviesSectionModel(Parcel in) {
        super(in);
        this.moviesItemModelList = in.createTypedArrayList(MoviesItemModel.CREATOR);
        this.tmdbConfig = in.readParcelable(TmdbConfig.class.getClassLoader());
    }

    public static final Creator<MoviesSectionModel> CREATOR = new Creator<MoviesSectionModel>() {
        @Override
        public MoviesSectionModel createFromParcel(Parcel source) {
            return new MoviesSectionModel(source);
        }

        @Override
        public MoviesSectionModel[] newArray(int size) {
            return new MoviesSectionModel[size];
        }
    };
}
