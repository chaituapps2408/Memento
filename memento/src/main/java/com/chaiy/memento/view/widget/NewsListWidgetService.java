package com.chaiy.memento.view.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.chaiy.memento.R;
import com.chaiy.memento.database.MementoProvider;
import com.chaiy.memento.database.columns.NewsColumns;

/**
 * Created by Chaiy on 12/14/2016.
 */

public class NewsListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class NewsListRemoteViewsFactory implements RemoteViewsFactory {

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_WIDTH_PORTRAIT = "w300";

    Cursor newsCursor;
    private Context mContext;
    private int mAppWidgetId;

    public NewsListRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        newsCursor = mContext.getContentResolver().query(MementoProvider.News.CONTENT_URI, null, null, null, null);
    }

    public void onDestroy() {
        if (newsCursor != null) {
            newsCursor.close();
        }
    }

    public int getCount() {
        if (newsCursor != null) {
            return newsCursor.getCount();
        }
        return 0;
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_news_item);

        newsCursor.moveToPosition(position);

        String text = newsCursor.getString(newsCursor.getColumnIndex(NewsColumns.headLine));
        rv.setTextViewText(R.id.widgetNewsName, text);

        // store the buzz ID in the extras so the main activity can use it
        Bundle extras = new Bundle();
        //extras.putString(EventsFeedActivity.EXTRA_ID, buzz.id);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widgetNewsItem, fillInIntent);


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