package com.chaiy.memento.view.framents;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaiy.memento.R;
import com.chaiy.memento.database.DBUtil;
import com.chaiy.memento.database.MementoLoader;
import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.view.adapter.HorizontalPagerAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class SearchEventsFragment extends Fragment implements LoaderManager.LoaderCallbacks<SparseArray<Cursor>> {


    private static final String MEMENTO_MODEL = "memento_model";

    private static final int LOADER_EVENTS = 20;

    private MementoModel mementoModel;
    HorizontalPagerAdapter horizontalPagerAdapter;
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;

    public static SearchEventsFragment newInstance(MementoModel mementoModel) {

        SearchEventsFragment searchEventsFragment = new SearchEventsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MEMENTO_MODEL, mementoModel);
        searchEventsFragment.setArguments(bundle);
        return searchEventsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mementoModel = getArguments().getParcelable(MEMENTO_MODEL);
        }
        horizontalPagerAdapter = new HorizontalPagerAdapter(getActivity().getSupportFragmentManager());
        //setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mementoModel == null) {
            getLoaderManager().initLoader(LOADER_EVENTS, null, this).forceLoad();
        } else {
            horizontalPagerAdapter.setData(mementoModel);
            horizontalPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_feed, container, false);

        horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(horizontalPagerAdapter);
       // if(model.getSectionOrder().size()>2){
            //horizontalInfiniteCycleViewPager.setCurrentItem(2, true);
       // }
        return view;
    }


    @Override
    public Loader<SparseArray<Cursor>> onCreateLoader(int id, Bundle args) {
        return new MementoLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<SparseArray<Cursor>> loader, SparseArray<Cursor> data) {
        MementoModel model = DBUtil.getMementoData(data);
        horizontalPagerAdapter.setData(model);
        horizontalPagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<SparseArray<Cursor>> loader) {
        Log.d(TAG, "onLoaderReset");
    }
}
