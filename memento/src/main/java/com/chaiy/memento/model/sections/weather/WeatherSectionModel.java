package com.chaiy.memento.model.sections.weather;

import android.os.Parcel;

import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.Sections;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class WeatherSectionModel extends BaseSectionModel {


    final DailyWeatherModel dailyWeather;

    public WeatherSectionModel(DailyWeatherModel dailyWeather) {
        super(Sections.WEATHER);
        this.dailyWeather = dailyWeather;
    }

    public DailyWeatherModel getDailyWeather() {
        return dailyWeather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.dailyWeather, flags);
    }

    protected WeatherSectionModel(Parcel in) {
        super(in);
        this.dailyWeather = in.readParcelable(DailyWeatherModel.class.getClassLoader());
    }

    public static final Creator<WeatherSectionModel> CREATOR = new Creator<WeatherSectionModel>() {
        @Override
        public WeatherSectionModel createFromParcel(Parcel source) {
            return new WeatherSectionModel(source);
        }

        @Override
        public WeatherSectionModel[] newArray(int size) {
            return new WeatherSectionModel[size];
        }
    };
}
