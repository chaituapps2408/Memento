package com.chaiy.memento.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chaiy.memento.model.sections.weather.WeatherSectionModel;
import com.chaiy.memento.view.framents.WeatherItemFragment;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class WeatherVerticalPagerAdapter extends BaseVerticalPagerAdapter<WeatherSectionModel> {


    public WeatherVerticalPagerAdapter(FragmentManager fm, WeatherSectionModel sectionModel) {
        super(fm, sectionModel);
    }

    @Override
    public int getCount() {
        if (getData() == null || getData().getDailyWeather() == null)
            return 0;

        return 1;
    }

    @Override
    public Fragment getItem(int position) {
        WeatherItemFragment weatherItemFragment = WeatherItemFragment.newInstance(getData().getDailyWeather());

        return weatherItemFragment;
    }


}
