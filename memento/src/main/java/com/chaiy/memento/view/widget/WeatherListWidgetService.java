package com.chaiy.memento.view.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chaiy.memento.R;
import com.chaiy.memento.database.MementoProvider;
import com.chaiy.memento.database.columns.WeatherColumns;

/**
 * Created by Chaiy on 12/14/2016.
 */

public class WeatherListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WeatherListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class WeatherListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_WIDTH_PORTRAIT = "w300";

    Cursor weatherCursor;
    private Context mContext;
    private int mAppWidgetId;

    public WeatherListRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        weatherCursor = mContext.getContentResolver().query(MementoProvider.Weather.CONTENT_URI, null, null, null, null);
    }

    public void onDestroy() {
        if (weatherCursor != null) {
            weatherCursor.close();
        }
    }

    public int getCount() {
        if (weatherCursor != null) {
            return weatherCursor.getCount();
        }
        return 0;
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_weather_item);

        weatherCursor.moveToPosition(position);

        String text = weatherCursor.getString(weatherCursor.getColumnIndex(WeatherColumns.summary));
        String maxTemp = weatherCursor.getString(weatherCursor.getColumnIndex(WeatherColumns.temperatureMax));
        String minTemp = weatherCursor.getString(weatherCursor.getColumnIndex(WeatherColumns.temperatureMin));
        rv.setTextViewText(R.id.weatherSummary, text);
        rv.setTextViewText(R.id.maxTempValue, formatTemperature(mContext, maxTemp));
        rv.setTextViewText(R.id.minTempValue, formatTemperature(mContext, minTemp));

        // store the buzz ID in the extras so the main activity can use it
        Bundle extras = new Bundle();
        //extras.putString(EventsFeedActivity.EXTRA_ID, buzz.id);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widgetWeatherItem, fillInIntent);


        return rv;
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

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
    }
}
