package com.chaiy.memento.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chaiy.memento.model.sections.movies.MoviesItemModel;
import com.chaiy.memento.model.sections.movies.MoviesSectionModel;
import com.chaiy.memento.view.framents.MovieItemFragment;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class MoviesVerticalPagerAdapter extends BaseVerticalPagerAdapter<MoviesSectionModel> {


    public MoviesVerticalPagerAdapter(FragmentManager fm, MoviesSectionModel sectionModel) {
        super(fm, sectionModel);
    }

    @Override
    public int getCount() {
        if (getData() != null && getData().getMoviesItemModelList() != null) {
            return getData().getMoviesItemModelList().size();
        }
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        MovieItemFragment movieItemFragment = MovieItemFragment.newInstance(getMovieItemData(position));

        return movieItemFragment;
    }

    private MoviesItemModel getMovieItemData(int position) {
        MoviesSectionModel moviesSectionModel = getData();

        if (moviesSectionModel != null && moviesSectionModel.getMoviesItemModelList() != null) {
            return moviesSectionModel.getMoviesItemModelList().get(position);
        }

        return null;
    }

}
