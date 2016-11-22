package com.chaiy.memento.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chaiy.memento.model.sections.news.NewsItemModel;
import com.chaiy.memento.model.sections.news.NewsSectionModel;
import com.chaiy.memento.view.framents.NewsItemFragment;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class NewsVerticalPagerAdapter extends BaseVerticalPagerAdapter<NewsSectionModel> {


    public NewsVerticalPagerAdapter(FragmentManager fm, NewsSectionModel sectionModel) {
        super(fm, sectionModel);
    }

    @Override
    public int getCount() {
        if (getData() == null || getData().getNewsItemModelList() == null)
            return 0;
        return getData().getNewsItemModelList().size();

    }

    @Override
    public Fragment getItem(int position) {
        NewsItemFragment newsItemFragment = NewsItemFragment.newInstance(getNewsItemData(position));
        return newsItemFragment;
    }

    private NewsItemModel getNewsItemData(int position) {
        NewsSectionModel newsSectionModel = getData();

        if (newsSectionModel == null || newsSectionModel.getNewsItemModelList() == null)
            return null;
        return newsSectionModel.getNewsItemModelList().get(position);
    }
}
