package com.chaiy.memento.view.framents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaiy.memento.R;
import com.chaiy.memento.model.sections.news.NewsItemModel;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class NewsItemFragment extends BaseFragment {


    private static final String NEWS_ITEM_DATA = "NEWS_ITEM_DATA";

    NewsItemModel newsItemModel;

    TextView newsHeadLineTV, newsContent, readMore;
    ImageView newsImage;

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
        newsContent = (TextView) view.findViewById(R.id.newsContent);
        newsImage = (ImageView) view.findViewById(R.id.newsImage);
        readMore = (TextView) view.findViewById(R.id.readMore);

        return view;
    }


    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        newsHeadLineTV.setText(newsItemModel.getHeadLine());
        newsContent.setText(newsItemModel.getSnippet());
        Glide.with(this).load(newsItemModel.getImagePath()).placeholder(R.drawable.ny_times_img).into(newsImage);
        setListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setListeners() {
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewsDetails(newsItemModel.getUrl());
            }
        });
    }

    private void openNewsDetails(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
