package com.chaiy.memento.view.framents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.BaseSectionModel;
import com.chaiy.memento.model.sections.Sections;
import com.chaiy.memento.model.sections.movies.MoviesSectionModel;
import com.chaiy.memento.model.sections.news.NewsSectionModel;
import com.chaiy.memento.model.sections.weather.WeatherSectionModel;
import com.chaiy.memento.view.adapter.BaseVerticalPagerAdapter;
import com.chaiy.memento.view.adapter.MoviesVerticalPagerAdapter;
import com.chaiy.memento.view.adapter.NewsVerticalPagerAdapter;
import com.chaiy.memento.view.adapter.WeatherVerticalPagerAdapter;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;


public class HorizontalPagerFragment extends BaseFragment {


    private static final String SECTION_DATA = "SECTION_DATA";

    private BaseSectionModel sectionData;

    TextView sectionNameTV;

    public static HorizontalPagerFragment newInstance(BaseSectionModel baseSectionModel) {

        HorizontalPagerFragment horizontalPagerFragment = new HorizontalPagerFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(SECTION_DATA, baseSectionModel);
        horizontalPagerFragment.setArguments(bundle);
        horizontalPagerFragment.setRetainInstance(true);

        return horizontalPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionData = getArguments().getParcelable(SECTION_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.horizontal_fragment, container, false);
        sectionNameTV = (TextView) view.findViewById(R.id.sectionName);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sectionNameTV.setText(sectionData.getSectionName());

        final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);

        verticalInfiniteCycleViewPager.setAdapter(getSectionAdapter(sectionData.getSection()));


//        horizontalInfiniteCycleViewPager.setScrollDuration(400);
//        horizontalInfiniteCycleViewPager.setInterpolator(
//                AnimationUtils.loadInterpolator(getContext(), android.R.anim.overshoot_interpolator)
//        );
//        horizontalInfiniteCycleViewPager.setMediumScaled(false);
//        horizontalInfiniteCycleViewPager.setMaxPageScale(0.8F);
//        horizontalInfiniteCycleViewPager.setMinPageScale(0.5F);
//        horizontalInfiniteCycleViewPager.setCenterPageScaleOffset(30.0F);
//        horizontalInfiniteCycleViewPager.setMinPageScaleOffset(5.0F);
//        horizontalInfiniteCycleViewPager.setOnInfiniteCyclePageTransformListener();

//        horizontalInfiniteCycleViewPager.setCurrentItem(
//                horizontalInfiniteCycleViewPager.getRealItem() + 1
//        );
    }


    private BaseVerticalPagerAdapter getSectionAdapter(Sections section) {
        switch (section) {
            case NEWS:
                return new NewsVerticalPagerAdapter(getChildFragmentManager(), (NewsSectionModel) sectionData);
            case MOVIES:
                return new MoviesVerticalPagerAdapter(getChildFragmentManager(), (MoviesSectionModel) sectionData);
            case WEATHER:
                return new WeatherVerticalPagerAdapter(getChildFragmentManager(), (WeatherSectionModel) sectionData);
            default:
                return null;
        }
    }
}
