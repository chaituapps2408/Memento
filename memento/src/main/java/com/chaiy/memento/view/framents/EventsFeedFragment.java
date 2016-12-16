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

public class EventsFeedFragment extends Fragment implements LoaderManager.LoaderCallbacks<SparseArray<Cursor>> {


    private static final String TAG = EventsFeedFragment.class.getSimpleName();

    private static final String MEMENTO_MODEL = "memento_model";
    private static final String SELECTED_INDEX = "SELECTED_INDEX";
    private static final int LOADER_EVENTS = 20;

    private MementoModel mementoModel;
    HorizontalPagerAdapter horizontalPagerAdapter;
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;

    int selectedIndex = -1;

    public static EventsFeedFragment newInstance(MementoModel mementoModel) {

        EventsFeedFragment eventsFeedFragment = new EventsFeedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MEMENTO_MODEL, mementoModel);
        eventsFeedFragment.setArguments(bundle);
        return eventsFeedFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mementoModel = getArguments().getParcelable(MEMENTO_MODEL);
        }
        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
        }
        horizontalPagerAdapter = new HorizontalPagerAdapter(getActivity().getSupportFragmentManager());
        if (mementoModel == null) {
            getLoaderManager().initLoader(LOADER_EVENTS, null, this).forceLoad();
        } else {
            horizontalPagerAdapter.setData(mementoModel);
        }
        //setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        horizontalPagerAdapter.notifyDataSetChanged();
        if (selectedIndex > -1) {
            horizontalInfiniteCycleViewPager.setCurrentItem(selectedIndex);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putInt(SELECTED_INDEX, selectedIndex);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_feed, container, false);

        horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(horizontalPagerAdapter);

        return view;
    }

    private void bindDataToView(MementoModel data) {

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
        if (selectedIndex > -1) {
            horizontalInfiniteCycleViewPager.setCurrentItem(selectedIndex);
        }
    }

    @Override
    public void onLoaderReset(Loader<SparseArray<Cursor>> loader) {
        Log.d(TAG, "onLoaderReset");
    }
}
