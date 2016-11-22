package com.chaiy.memento.view.framents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.movies.MoviesItemModel;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class MovieItemFragment extends Fragment {


    private static final String MOVIE_ITEM_DATA = "MOVIE_ITEM_DATA";

    private MoviesItemModel moviesItemModel;
    TextView movieNameTV;

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
        movieNameTV = (TextView) view.findViewById(R.id.movieName);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        movieNameTV.setText(moviesItemModel.getMovieName());
        super.onViewCreated(view, savedInstanceState);
        //final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        //txt.setText(sectionName + " Item : " + (position + 1));
        /*final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
        verticalInfiniteCycleViewPager.setAdapter(new MoviesVerticalPagerAdapter(getContext()));

        verticalInfiniteCycleViewPager.setScrollDuration(1000);
        verticalInfiniteCycleViewPager.startAutoScroll(true);*/
    }
}
