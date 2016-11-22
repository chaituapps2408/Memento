package com.chaiy.memento.view.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chaiy.memento.model.sections.BaseSectionModel;

/**
 * Created by Chaiy on 11/21/2016.
 */

public abstract class BaseVerticalPagerAdapter<BS extends BaseSectionModel> extends FragmentStatePagerAdapter{


    final BS sectionModel;

    public BaseVerticalPagerAdapter(FragmentManager fm, BS sectionModel) {
        super(fm);
        this.sectionModel = sectionModel;
    }

    public BS getData(){
        return sectionModel;
    }
}
