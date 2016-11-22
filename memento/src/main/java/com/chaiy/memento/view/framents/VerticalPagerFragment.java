package com.chaiy.memento.view.framents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaiy.memento.R;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class VerticalPagerFragment extends Fragment {


    private static final String POSITION = "Position";
    private static final String SECTION_NAME = "SectionName";

    private int position;
    private String sectionName;

    public static VerticalPagerFragment newInstance(Integer position, String sectionName) {

        VerticalPagerFragment verticalPagerFragment = new VerticalPagerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putString(SECTION_NAME, sectionName);
        verticalPagerFragment.setArguments(bundle);
        verticalPagerFragment.setRetainInstance(true);

        return verticalPagerFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION);
            sectionName = getArguments().getString(SECTION_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vertical_movie_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
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
