package com.chaiy.memento.backend.bean.sections.weather;

import com.chaiy.memento.backend.bean.sections.BaseSectionBean;
import com.chaiy.memento.backend.bean.sections.Sections;

/**
 * Created by Chaiy on 11/6/2016.
 */

public class WeatherSectionBean extends BaseSectionBean {


    final DailyWeather dailyWeather;

    public WeatherSectionBean(DailyWeather dailyWeather) {
        super(Sections.WEATHER);
        this.dailyWeather = dailyWeather;
    }

    public DailyWeather getDailyWeather() {
        return dailyWeather;
    }
}
