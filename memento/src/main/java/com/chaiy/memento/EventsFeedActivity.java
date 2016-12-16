package com.chaiy.memento;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaiy.memento.events.DataFetchEvent;
import com.chaiy.memento.network.MementoJobScheduler;
import com.chaiy.memento.view.framents.EventsFeedFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.chaiy.memento.network.MementoAlarmReceiver.REQUEST_CODE;

public class EventsFeedActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = EventsFeedActivity.class.getSimpleName();
    private static final int REQUEST_CHECK_SETTINGS = 1002;
    private static final String FRAGMENT_TAG = "EventsFeedFragment";

    FrameLayout fragmentContainer;
    ProgressBar progressBar;

    Toolbar toolbar;
    TextView dateTV, errorText;
    ImageView leftImage;
    FloatingActionButton searchBtn;

    GoogleApiClient googleApiClient;
    String latitude, longitude;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_events_feed);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        searchBtn = (FloatingActionButton) findViewById(R.id.favouriteBtn);
        searchBtn.setVisibility(View.GONE);

        // Get access to the date text view
        dateTV = (TextView) toolbar.findViewById(R.id.date);

        leftImage = (ImageView) findViewById(R.id.leftImage);
        leftImage.setImageResource(R.mipmap.ic_launcher);

        errorText = (TextView) findViewById(R.id.errorText);
        errorText.setVisibility(View.GONE);

        setListeners();

        //init();

        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        try {
            super.onDestroy();
        } catch (NullPointerException npe) {
            Log.e(TAG, "NPE: Bug workaround");
        }
    }

    private void setListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelectDateActivity();
            }
        });
    }

    private void startSelectDateActivity() {
        Intent intent = new Intent(this, SelectDateActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(String currentDate) {
        progressBar.setVisibility(View.GONE);
        //searchIV.setVisibility(View.VISIBLE);
        searchBtn.setVisibility(View.VISIBLE);
        Log.d(TAG, " date displayed : " + currentDate);
        dateTV.setText(currentDate);


        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        final EventsFeedFragment fragment = (EventsFeedFragment) fm.findFragmentByTag(FRAGMENT_TAG);

        // create the fragment and data the first time
        if (fragment == null) {
            // add the fragment
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    EventsFeedFragment eventsFeedFragment = EventsFeedFragment.newInstance(null);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, eventsFeedFragment, FRAGMENT_TAG)
                            .commitNow();
                }
            });

        }

    }


    public void init() {

        if (!MementoJobScheduler.isRecurringAlarmScheduled(this)) {
            Log.d(TAG, "Alarm Not scheduled");
            MementoJobScheduler.scheduleRecurringAlarm(this);
        }
        if (MementoJobScheduler.shouldScheduleJobImmediate(this)) {
            Log.d(TAG, "scheduleJobNow true");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            } else
                getLastKnownLocation();
        } else {
            Log.d(TAG, "scheduleJobNow false");
            replaceFragment(MementoJobScheduler.getStoredCurrentDate(this));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DataFetchEvent event) {
        if (!event.isSuccess()) {
            displayError();
            return;
        }
        replaceFragment(MementoJobScheduler.getStoredCurrentDate(this));
    }

    private void displayError() {
        progressBar.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(R.string.error_message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                    case Activity.RESULT_CANCELED:
                        getLastKnownLocation();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        init();
    }

    @Override
    public void onConnectionSuspended(int i) {
        init();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    createLocationRequest();
                } else {
                    // Permission Denied
                    Toast.makeText(EventsFeedActivity.this, R.string.location_error_msg, Toast.LENGTH_SHORT)
                            .show();
                    getLastKnownLocation();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkLocationPermission() {
        int hasLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

                showMessageOKCancel(getString(R.string.permission_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
            return;
        }
        createLocationRequest();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EventsFeedActivity.this)
                .setMessage(message)
                .setPositiveButton(R.string.ok_string, okListener)
                //.setNegativeButton(R.string.cancel_string, null)
                .create()
                .show();
    }

    private void getLastKnownLocation() {
        int hasLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
            if (mLastLocation != null) {
                latitude = String.valueOf(mLastLocation.getLatitude());
                longitude = String.valueOf(mLastLocation.getLongitude());
            }
        }

        MementoJobScheduler.scheduleJobNow(this, latitude, longitude);
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates locationSettingsStates = locationSettingsResult.getLocationSettingsStates();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLastKnownLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    EventsFeedActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        getLastKnownLocation();
                        break;
                }
            }
        });

    }

}
