package com.chaiy.memento.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;

import com.chaiy.memento.model.MementoModel;
import com.chaiy.memento.view.framents.HorizontalPagerFragment;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends FragmentStatePagerAdapter {

    private final static String[] SECTIONS = {"Movies", "News", "Weather"};
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final MementoModel mementoModel;

    public HorizontalPagerAdapter(final Context context, FragmentManager fragmentManager, MementoModel mementoModel) {
        super(fragmentManager);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mementoModel = mementoModel;
    }

    @Override
    public int getCount() {
        if (mementoModel == null) {
            return 0;
        }
        return mementoModel.getSectionOrder().length;
    }

    @Override
    public Fragment getItem(int position) {
        HorizontalPagerFragment horizontalPagerFragment = HorizontalPagerFragment.newInstance(mementoModel.getSections()
                .get(mementoModel.getSectionOrder()[position]));

        return horizontalPagerFragment;
    }

  /*  @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.horizontal_fragment, container, false);

        final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
        verticalInfiniteCycleViewPager.setAdapter(
                new MoviesVerticalPagerAdapter(mContext, SECTIONS[position])
        );
        //verticalInfiniteCycleViewPager.setCurrentItem(position);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }*/
}
