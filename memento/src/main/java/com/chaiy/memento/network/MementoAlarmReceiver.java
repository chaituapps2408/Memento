package com.chaiy.memento.network;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Chaiy on 12/7/2016.
 */

public class MementoAlarmReceiver extends BroadcastReceiver implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.chaiy.memento.network.MementoAlarmReceiver";
    private static final String TAG = MementoAlarmReceiver.class.getSimpleName();
    GoogleApiClient googleApiClient;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, " onReceive ");
        this.context = context;

        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        getLastKnownLocation();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        getLastKnownLocation();
    }

    private void getLastKnownLocation() {
        String latitude = null;
        String longitude = null;

        int hasLocationPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
            if (mLastLocation != null) {
                latitude = String.valueOf(mLastLocation.getLatitude());
                longitude = String.valueOf(mLastLocation.getLongitude());
            }
        }
        MementoJobScheduler.scheduleJobNow(context, latitude, longitude);
    }
}