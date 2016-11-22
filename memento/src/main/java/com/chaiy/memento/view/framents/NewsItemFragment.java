package com.chaiy.memento.view.framents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.news.NewsItemModel;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class NewsItemFragment extends Fragment {


    private static final String NEWS_ITEM_DATA = "NEWS_ITEM_DATA";

    NewsItemModel newsItemModel;

    TextView newsHeadLineTV;

    public static NewsItemFragment newInstance(NewsItemModel newsItemModel) {

        NewsItemFragment verticalPagerFragment = new NewsItemFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(NEWS_ITEM_DATA, newsItemModel);
        verticalPagerFragment.setArguments(bundle);
        verticalPagerFragment.setRetainInstance(true);

        return verticalPagerFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsItemModel = getArguments().getParcelable(NEWS_ITEM_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vertical_news_fragment, container, false);
        newsHeadLineTV = (TextView) view.findViewById(R.id.newsHeadLine);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        newsHeadLineTV.setText(newsItemModel.getHeadLine());
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
