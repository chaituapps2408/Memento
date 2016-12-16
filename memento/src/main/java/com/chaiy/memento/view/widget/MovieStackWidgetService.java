package com.chaiy.memento.view.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.request.target.AppWidgetTarget;
import com.chaiy.memento.R;
import com.chaiy.memento.database.MementoProvider;
import com.chaiy.memento.database.columns.MovieColumns;

/**
 * Created by Chaiy on 12/12/2016.
 */

public class MovieStackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_WIDTH_PORTRAIT = "w300";

    Cursor moviesCursor;
    private Context mContext;
    private int mAppWidgetId;
    Intent intent;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        this.intent = intent;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        moviesCursor = mContext.getContentResolver().query(MementoProvider.Movies.CONTENT_URI, null, null, null, null);
    }

    public void onDestroy() {
        if (moviesCursor != null) {
            moviesCursor.close();
        }
    }

    public int getCount() {
        if (moviesCursor != null) {
            return moviesCursor.getCount();
        }
        return 0;
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_movie_item);

        moviesCursor.moveToPosition(position);
        AppWidgetTarget appWidgetTarget = new AppWidgetTarget(mContext, rv, R.id.movieImage, mAppWidgetId);


       /* Glide
                .with(mContext.getApplicationContext()) // safer!
                .load(getPosterPath(moviesCursor.getColumnName(moviesCursor.getColumnIndex(MovieColumns.posterPath))))
                .asBitmap()
                .into(appWidgetTarget);*/

        String text = moviesCursor.getString(moviesCursor.getColumnIndex(MovieColumns.movieName));
        rv.setTextViewText(R.id.widgetmovieName, text);

        // store the buzz ID in the extras so the main activity can use it
        Bundle extras = new Bundle();
        //extras.putString(EventsFeedActivity.EXTRA_ID, buzz.id);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widgetMovieItem, fillInIntent);


        return rv;
    }

    private String getPosterPath(String path) {
        String url = IMAGE_BASE_URL;
        url = url + IMAGE_WIDTH_PORTRAIT + path;
        return url;
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
