package com.chaiy.memento.view.framents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.weather.DailyWeatherModel;
import com.chaiy.memento.view.custom.WeatherIconView;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class WeatherItemFragment extends BaseFragment {


    private static final String WEATHER_ITEM_DATA = "WEATHER_ITEM_DATA";

    private DailyWeatherModel dailyWeatherModel;

    TextView summaryTV, maxTemp, minTemp, precipitation;
    WeatherIconView weatherIconView;

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
        maxTemp = (TextView) view.findViewById(R.id.maxTempValue);
        minTemp = (TextView) view.findViewById(R.id.minTempValue);
        weatherIconView = (WeatherIconView) view.findViewById(R.id.my_weather_icon);
        precipitation = (TextView) view.findViewById(R.id.precipitationValue);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        summaryTV.setText(dailyWeatherModel.getSummary());
        maxTemp.setText(formatTemperature(getContext(), dailyWeatherModel.getTemperatureMax()));
        minTemp.setText(formatTemperature(getContext(), dailyWeatherModel.getTemperatureMin()));
        weatherIconView.setIconResource(getIconResource(dailyWeatherModel.getIcon()));
        weatherIconView.setContentDescription(dailyWeatherModel.getIcon());
        if (precipitation != null) {
            precipitation.setText(dailyWeatherModel.getPrecipType());
        }
        super.onViewCreated(view, savedInstanceState);

    }

    public static String formatTemperature(Context context, String temperature) {
        // Data stored in Celsius by default.  If user prefers to see in Fahrenheit, convert
        // the values here.
       /* if (!isMetric(context)) {
            temperature = (temperature * 1.8) + 32;
        }*/
        double tempDouble = 0;
        try {
            tempDouble = Double.parseDouble(temperature);
        } catch (NumberFormatException ex) {
            return temperature;
        }
        // For presentation, assume the user doesn't care about tenths of a degree.
        return String.format(context.getString(R.string.format_temperature), tempDouble);
    }

    /* public static boolean isMetric(Context context) {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
         return prefs.getString(context.getString(R.string.pref_units_key),
                 context.getString(R.string.pref_units_metric))
                 .equals(context.getString(R.string.pref_units_metric));
     }*/
    private String getIconResource(String icon) {


        if (null == icon) {
            return "";
        }
        icon = icon.replace("\"", "");
        if (icon.equalsIgnoreCase("clear-day")) {
            return getString(R.string.wi_forecast_io_clear_day);
        } else if (icon.equalsIgnoreCase("clear-night")) {
            return getString(R.string.wi_forecast_io_clear_night);
        } else if (icon.equalsIgnoreCase("partly-cloudy-day")) {
            return getString(R.string.wi_forecast_io_partly_cloudy_day);
        } else if (icon.equalsIgnoreCase("partly-cloudy-night")) {
            return getString(R.string.wi_forecast_io_partly_cloudy_night);
        } else if (icon.equalsIgnoreCase("cloudy")) {
            return getString(R.string.wi_forecast_io_cloudy);
        } else if (icon.equalsIgnoreCase("rain")) {
            return getString(R.string.wi_forecast_io_rain);
        } else if (icon.equalsIgnoreCase("sleet")) {
            return getString(R.string.wi_forecast_io_sleet);
        } else if (icon.equalsIgnoreCase("snow")) {
            return getString(R.string.wi_forecast_io_snow);
        } else if (icon.equalsIgnoreCase("wind")) {
            return getString(R.string.wi_forecast_io_wind);
        } else if (icon.equalsIgnoreCase("fog")) {
            return getString(R.string.wi_forecast_io_fog);
        } else if (icon.equalsIgnoreCase("hail")) {
            return getString(R.string.wi_forecast_io_hail);
        } else if (icon.equalsIgnoreCase("fog")) {
            return getString(R.string.wi_forecast_io_thunderstorm);
        } else if (icon.equalsIgnoreCase("tornado")) {
            return getString(R.string.wi_forecast_io_tornado);
        } else
            return "";
    }
}
