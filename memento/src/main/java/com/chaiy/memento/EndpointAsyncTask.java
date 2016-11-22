package com.chaiy.memento;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaiy.memento.backend.mementoApi.MementoApi;
import com.chaiy.memento.backend.mementoApi.model.MementoBean;
import com.chaiy.memento.model.MementoModel;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Chaiy on 10/23/2016.
 */

public class EndpointAsyncTask extends AsyncTask<Void, Void, String> {

    private final Context context;
    private ProgressBar progressBar;
    private static MementoApi myApiService = null;
    private GetJokeTaskListener getJokeTaskListener;

    EndpointAsyncTask(Context context) {
        this.context = context;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public GetJokeTaskListener getGetJokeTaskListener() {
        return getJokeTaskListener;
    }

    public void setGetJokeTaskListener(GetJokeTaskListener getJokeTaskListener) {
        this.getJokeTaskListener = getJokeTaskListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MementoApi.Builder builder = new MementoApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            MementoBean mementoBean = myApiService.getEvents("2011-05-03", "37.8267", "-122.4233").execute();
            Log.d("test", "" + mementoBean.getSections().size());
            MementoModel mementoModel = mementoBean.getFactory().fromString(mementoBean.toString(), MementoModel.class);
            Log.d("test mementoModel", "" + mementoModel.getSections().size());
            return null;
        } catch (Exception e) {
            Log.e("test meml Exception ", e.getMessage(),e);
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (getJokeTaskListener != null) {
            getJokeTaskListener.onComplete(result);
        }
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    public static interface GetJokeTaskListener {
        public void onComplete(String joke);
    }
}
