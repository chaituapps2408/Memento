package com.chaiy.memento.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.chaiy.memento.EventsFeedActivity;
import com.chaiy.memento.R;

/**
 * Created by Chaiy on 12/12/2016.
 */

public class MementoMoviesWidgetProvider extends AppWidgetProvider {

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.memento_widget_movies);

            // set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, MovieStackWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.movieStack, serviceIntent);
            remoteViews.setEmptyView(R.id.movieStack, R.id.stackWidgetEmptyView);

            // set intent for item click (opens main activity)
            Intent viewIntent = new Intent(context, EventsFeedActivity.class);
            viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
            remoteViews.setPendingIntentTemplate(R.id.movieStack, viewPendingIntent);

            // update widget
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
