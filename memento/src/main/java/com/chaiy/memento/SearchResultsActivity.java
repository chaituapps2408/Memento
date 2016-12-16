package com.chaiy.memento;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.network.SearchAsyncTaskLoader;
import com.chaiy.memento.view.framents.SearchEventsFragment;

public class SearchResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MementoModel> {


    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    private static final String DATA = "DATA";
    private static final int LOADER_EVENTS = 10;

    FrameLayout fragmentContainer;
    ProgressBar progressBar;

    Toolbar toolbar;
    TextView dateTV, errorText;
    ImageView searchIV,backImage;
    FloatingActionButton favBtn;

    MementoModel mementoModel;
    String date;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " in onCreate ");
        if (getIntent() != null) {
            Log.d(TAG, " in onCreate getIntent NOT null");
            date = getIntent().getStringExtra(SelectDateActivity.SELECTED_DATE);
            latitude = getIntent().getStringExtra(SelectDateActivity.LATITUDE);
            longitude = getIntent().getStringExtra(SelectDateActivity.LONGITUDE);

            Log.d(TAG, " onCreate date : " + date);
            Log.d(TAG, " onCreate latitude : " + latitude);
            Log.d(TAG, " onCreate longitude : " + longitude);

        }
        setContentView(R.layout.activity_events_feed);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);


        // Get access to the date text view
        dateTV = (TextView) findViewById(R.id.date);
        dateTV.setText(date);

        errorText = (TextView) findViewById(R.id.errorText);
        errorText.setVisibility(View.GONE);

        searchIV = (ImageView) findViewById(R.id.search);
        searchIV.setVisibility(View.GONE);

        backImage = (ImageView) findViewById(R.id.leftImage);
        favBtn = (FloatingActionButton) findViewById(R.id.favouriteBtn);
        favBtn.setVisibility(View.GONE);
        setListeners();

        init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, " in onSaveInstanceState ");
        super.onSaveInstanceState(outState);
        if (outState != null)
            outState.putParcelable(DATA, mementoModel);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, " in onRestoreInstanceState ");
        if (savedInstanceState != null) {
            Log.d(TAG, " in onRestoreInstanceState savedInstanceState NOT null");
            mementoModel = savedInstanceState.getParcelable(DATA);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, " in onStart ");
        super.onStart();
        initLoader();
    }

    @Override
    protected void onStop() {
        getSupportLoaderManager().destroyLoader(LOADER_EVENTS);
        super.onStop();
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

    private void initLoader() {
        Log.d(TAG, " in initLoader");
        if (mementoModel == null) {
            //favBtn.setVisibility(View.GONE);
            Log.d(TAG, " in initLoader mementoModel null");
            getSupportLoaderManager().initLoader(LOADER_EVENTS, null, this).forceLoad();
        } //else
        //replaceFragment(date, mementoModel);
    }

    public void init(Bundle savedInstanceState) {
        Log.d(TAG, " in init ");
        if (savedInstanceState != null) {
            Log.d(TAG, " in init savedInstanceState NOT null");
            mementoModel = savedInstanceState.getParcelable(DATA);
        }
    }

    private void setListeners() {
        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void replaceFragment(String currentDate, final MementoModel mementoModel) {
        this.mementoModel = mementoModel;
        progressBar.setVisibility(View.GONE);
        //favBtn.setVisibility(View.VISIBLE);
        Log.d(TAG, " in replaceFragment ");
        Log.d(TAG, " date displayed : " + currentDate);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                SearchEventsFragment fragment = SearchEventsFragment.newInstance(mementoModel);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commitNow();
            }
        });

    }

    private void displayError() {
        progressBar.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
        errorText.setText(R.string.error_message);
    }

    @Override
    public Loader<MementoModel> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, " in onCreateLoader ");
        return new SearchAsyncTaskLoader(this, date, latitude, longitude, false);
    }

    @Override
    public void onLoadFinished(Loader<MementoModel> loader, MementoModel mementoModel) {
        Log.d(TAG, " in onLoadFinished ");
        if (mementoModel == null) {
            displayError();
            return;
        }
        replaceFragment(date, mementoModel);
    }

    @Override
    public void onLoaderReset(Loader<MementoModel> loader) {
        Log.d(TAG, " in onLoaderReset ");
    }


}
