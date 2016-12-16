package com.chaiy.memento.network;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chaiy on 12/7/2016.
 */

public class MementoJobScheduler {

    private static final String TAG = MementoJobScheduler.class.getSimpleName();

    private static final String RECURRING_ALARM_SET = "RECURRING_ALARM_SET";
    private static final String STORED_EVENT_DATE = "STORED_EVENT_DATE";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static void scheduleJobNow(Context context, String latitude, String longitude) {

        Log.d(TAG, " scheduleJobNow ");
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        if (latitude == null || "".equalsIgnoreCase(latitude)) {
            latitude = "-1";
        }
        if (longitude == null || "".equalsIgnoreCase(longitude)) {
            longitude = "-1";
        }
        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString("latitude", latitude);
        myExtrasBundle.putString("longitude", longitude);

        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MementoService.class)
                // uniquely identifies the job
                .setTag("MementoService")
                // one-off job
                .setRecurring(false)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.NOW)
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
               /* .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_UNMETERED_NETWORK,
                        // only run when the device is charging
                        Constraint.DEVICE_CHARGING
                )*/
                .setExtras(myExtrasBundle)
                .build();

        dispatcher.mustSchedule(myJob);

    }

    public static void scheduleRecurringAlarm(Context context) {

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(context, MementoAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, MementoAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 37);


        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        /*alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HALF_HOUR, pIntent);*/

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pIntent);

        registerRecurringAlarmScheduled(context);

    }

    public static boolean shouldScheduleJobImmediate(Context context) {

        String date = getStoredCurrentDate(context);

        if (getOneYearBackDate().equalsIgnoreCase(date))
            return false;

        return true;
    }


    public static boolean isRecurringAlarmScheduled(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE);
        return sharedPreferences.getBoolean(RECURRING_ALARM_SET, false);

    }

    public static void registerRecurringAlarmScheduled(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(RECURRING_ALARM_SET, true);
        editor.apply();

    }

    public static String getOneYearBackDate() {

        Calendar cal = Calendar.getInstance(Locale.US);
        cal.add(Calendar.YEAR, -1); // to get previous year add -1
        Date previousYear = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        String formattedDate = df.format(previousYear);

        Log.d(TAG, "getOneYearBackDate :" + formattedDate);
        return df.format(previousYear);
    }

    public static void updateStoredCurrentDate(Context context, String date) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(STORED_EVENT_DATE, date);
        editor.apply();

    }

    public static String getStoredCurrentDate(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE);
        String date = sharedPreferences.getString(STORED_EVENT_DATE, null);
        Log.d(TAG, "getStoredCurrentDate :" + date);

        return date;
    }


}
