package com.chaiy.memento.view.framents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.weather.DailyWeatherModel;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class WeatherItemFragment extends Fragment {


    private static final String WEATHER_ITEM_DATA = "WEATHER_ITEM_DATA";

    private DailyWeatherModel dailyWeatherModel;

    TextView summaryTV;

    public static WeatherItemFragment newInstance(DailyWeatherModel dailyWeatherModel) {

        WeatherItemFragment weatherItemFragment = new WeatherItemFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(WEATHER_ITEM_DATA, dailyWeatherModel);
        weatherItemFragment.setArguments(bundle);
        weatherItemFragment.setRetainInstance(true);

        return weatherItemFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dailyWeatherModel = getArguments().getParcelable(WEATHER_ITEM_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_weather_fragment, container, false);
        summaryTV = (TextView) view.findViewById(R.id.weatherSummary);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        summaryTV.setText(dailyWeatherModel.getSummary());
        super.onViewCreated(view, savedInstanceState);
        //final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        //txt.setText(sectionName + " Item : " + (position + 1));
        /*final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
        verticalInfiniteCycleViewPager.setAdapter(new MoviesVerticalPagerAdapter(getContext()));

        verticalInfiniteCycleViewPager.setScrollDuration(1000);
        verticalInfiniteCycleViewPager.startAutoScroll(true);*/
    }
}
