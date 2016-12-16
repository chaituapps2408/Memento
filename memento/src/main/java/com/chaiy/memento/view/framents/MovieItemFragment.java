package com.chaiy.memento.view.framents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.movies.MoviesItemModel;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class MovieItemFragment extends BaseFragment {

    public static final String TAG = MovieItemFragment.class.getSimpleName();


    private static final String MOVIE_ITEM_DATA = "MOVIE_ITEM_DATA";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_WIDTH_PORTRAIT = "w300";
    private static final String IMAGE_WIDTH_LANDSCAPE = "w92";
    private static final String TMDB_BASE_URL = "https://www.themoviedb.org/movie/";

    private MoviesItemModel moviesItemModel;
    TextView movieNameTV, ratingValueTV, overviewValueTV, readMoreTV;
    ImageView imageView;

    public static MovieItemFragment newInstance(MoviesItemModel moviesItemModel) {

        MovieItemFragment verticalPagerFragment = new MovieItemFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_ITEM_DATA, moviesItemModel);
        verticalPagerFragment.setArguments(bundle);
        verticalPagerFragment.setRetainInstance(true);

        return verticalPagerFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moviesItemModel = getArguments().getParcelable(MOVIE_ITEM_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_movie_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.movieImage);

        movieNameTV = (TextView) view.findViewById(R.id.movieName);
        ratingValueTV = (TextView) view.findViewById(R.id.ratingValue);
        overviewValueTV = (TextView) view.findViewById(R.id.overviewValue);
        readMoreTV = (TextView) view.findViewById(R.id.readMore);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {

        Log.d(TAG, getPosterPath());
        Glide.with(this).load(getPosterPath()).into(imageView);

        imageView.setContentDescription(moviesItemModel.getMovieName());
        movieNameTV.setText(moviesItemModel.getMovieName());
        ratingValueTV.setText(moviesItemModel.getRating());
        if (!TextUtils.isEmpty(moviesItemModel.getOverview()))
            overviewValueTV.setText(String.format("%s%s", getString(R.string.overview), moviesItemModel.getOverview()));

        setListeners();
        super.onViewCreated(view, savedInstanceState);

    }

    private void setListeners() {

        if (isLandscape()) {
            readMoreTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openMovieDetails(getTMDBUrl());
                }
            });
            return;
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMovieDetails(getTMDBUrl());
            }
        });
    }


    private String getPosterPath() {
        String url = IMAGE_BASE_URL;
        if (isLandscape()) {
            url = url + IMAGE_WIDTH_LANDSCAPE + moviesItemModel.getPosterPath();
        } else
            url = url + IMAGE_WIDTH_PORTRAIT + moviesItemModel.getPosterPath();
        return url;
    }

    private String getTMDBUrl() {
        return TMDB_BASE_URL + moviesItemModel.get_id();
    }

    private void openMovieDetails(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
