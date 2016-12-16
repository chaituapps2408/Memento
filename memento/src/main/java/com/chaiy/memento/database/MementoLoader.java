package com.chaiy.memento.database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.SparseArray;

import com.chaiy.memento.model.MementoModel;

/**
 * Created by Chaiy on 12/5/2016.
 */

public class MementoLoader extends AsyncTaskLoader<SparseArray<Cursor>> {

    Context context;
    SparseArray<Cursor> cursorList = new SparseArray<>();

    public MementoLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public SparseArray<Cursor> loadInBackground() {

        Cursor movieCursor = context.getContentResolver().query(MementoProvider.Movies.CONTENT_URI, null, null, null, null);
        Cursor newsCursor = context.getContentResolver().query(MementoProvider.News.CONTENT_URI, null, null, null, null);
        Cursor weatherCursor = context.getContentResolver().query(MementoProvider.Weather.CONTENT_URI, null, null, null, null);

        if (movieCursor != null && movieCursor.getCount() > 0)
            cursorList.put(MementoModel.MOVIES_SECTION, movieCursor);
        if (newsCursor != null && newsCursor.getCount() > 0)
            cursorList.put(MementoModel.NEWS_SECTION, newsCursor);
        if (weatherCursor != null && weatherCursor.getCount() > 0)
            cursorList.put(MementoModel.WEATHER_SECTION, weatherCursor);

        return cursorList;
    }
}
