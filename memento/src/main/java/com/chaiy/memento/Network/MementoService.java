package com.chaiy.memento.network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.Loader;
import android.util.Log;

import com.chaiy.memento.EventsFeedActivity;
import com.chaiy.memento.R;
import com.chaiy.memento.events.DataFetchEvent;
import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.view.widget.MementoMoviesWidgetProvider;
import com.chaiy.memento.view.widget.MementoNewsWidgetProvider;
import com.chaiy.memento.view.widget.MementoWeatherWidgetProvider;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Chaiy on 12/5/2016.
 */

public class MementoService extends JobService implements Loader.OnLoadCompleteListener<MementoModel> {

    private static final String TAG = MementoService.class.getSimpleName();
    private static final int LOADER_EVENTS = 20;
    private static final int NOTIFICATION_ID = 10;

    SearchAsyncTaskLoader loader;
    String oneYearBackDate;
    JobParameters job;

    @Override
    public boolean onStartJob(final JobParameters job) {

        this.job = job;
        oneYearBackDate = MementoJobScheduler.getOneYearBackDate();
        Log.d(TAG, "onStartJob with oneYearBackDate : " + oneYearBackDate);

        String latitude = null;
        String longitude = null;

        Bundle bundle = job.getExtras();
        if (bundle != null) {
            latitude = bundle.getString("latitude", "-1");
            longitude = bundle.getString("longitude", "-1");
        }

        Log.d(TAG, "onStartJob with latitude : " + latitude);
        Log.d(TAG, "onStartJob with longitude : " + longitude);

        loader = new SearchAsyncTaskLoader(this, oneYearBackDate, latitude, longitude, true);
        loader.registerListener(LOADER_EVENTS, this);
        loader.forceLoad();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    @Override
    public void onLoadComplete(Loader<MementoModel> loader, MementoModel mementoModel) {
        Log.d(TAG, "onComplete with oneYearBackDate : " + oneYearBackDate);
        boolean isSuccess = false;

        if (mementoModel != null) {
            isSuccess = true;
            MementoJobScheduler.updateStoredCurrentDate(this, oneYearBackDate);
            Log.d(TAG, "onComplete Data fetch success : " + isSuccess);
            createNotification(NOTIFICATION_ID, R.mipmap.ic_launcher, getString(R.string.app_name)
                    , getString(R.string.notification_body));
            updateWidgets();
        }
        EventBus.getDefault().post(new DataFetchEvent(isSuccess, mementoModel));
        jobFinished(job, false);
    }

    private void createNotification(int nId, int iconRes, String title, String body) {

        // First let's define the intent to trigger when notification is selected
        // Start out by creating a normal intent (in this case to open an activity)
        Intent intent = new Intent(this, EventsFeedActivity.class);
        // Next, let's turn this into a PendingIntent using
        //   public static PendingIntent getActivity(Context context, int requestCode,
        //       Intent intent, int flags)
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());
    }

    private void updateWidgets() {

        updateMoviesWidgets();
        updateNewsWidgets();
        updateWeatherWidgets();

    }

    private void updateMoviesWidgets() {

        Intent intent = new Intent(this, MementoMoviesWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), MementoMoviesWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

    }

    private void updateNewsWidgets() {

        Intent intent = new Intent(this, MementoNewsWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), MementoNewsWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

    }

    private void updateWeatherWidgets() {

        Intent intent = new Intent(this, MementoWeatherWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), MementoWeatherWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

    }
}
